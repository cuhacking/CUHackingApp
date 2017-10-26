class SlackAPI < Lita::Adapters::Slack::API
    SlackAPIError = Class.new(StandardError)
    NotInChannelError = Class.new(SlackAPIError)
    API_URL = 'https://slack.com/api/'

    def send_file(room_or_user, content, **options)
    call_api(
        'files.upload',
        as_user: true,
        channels: room_or_user.id,
        content: content,
        **options
    )
    end

    def update(text, ts:, channel:)
    call_api(
        'chat.update',
        ts: ts,
        channel: channel,
        text: text,
        as_user: true,
    )
    end

    def channels_list
    call_api(
        'channels.list',
        exclude_archived: true,
        exclude_members: true
    )
    end

    def add_reaction(channel, ts, reaction)
    call_api(
        'reactions.add',
        timestamp: ts,
        channel: channel.id,
        name: reaction,
    )
    end

    def channels_replies(channel, thread_ts)
    call_api(
        'channels.replies',
        channel: channel,
        thread_ts: thread_ts,
        )
    end

    def channels_info(channel_id)
    call_api(
        'channels.info',
        channel: channel_id,
        include_locale: false,
        )
    end

    private

    attr_reader :config

    def call_api(method, post_data = {})
    response = connection.post(
        "#{API_URL}#{method}",
        { token: config.token }.merge(post_data)
    )
    data = parse_response(response, method)
    case data['error']
    when nil
        return data
    when 'not_in_channel'
        channel = Lita::Room.find_by_id(post_data[:channel]).display_name || post_data[:channel]
        raise NotInChannelError, "Slack API call to #{method} failed: not in channel #{channel}."
    else
        raise SlackAPIError, "Slack API call to #{method} returned an error: #{data['error']}."
    end
    end

    def connection
    @connection ||= begin
        options = if config.proxy.nil?
        { proxy: config.proxy }
        else
        {}
        end
        Faraday.new(options)
    end
    end

    def parse_response(response, method)
    raise "Slack API call to #{method} failed with status code #{response.status}: " \
        "'#{response.body}'. Headers: #{response.headers}" unless response.success?

    JSON.load(response.body)
    end
end
  