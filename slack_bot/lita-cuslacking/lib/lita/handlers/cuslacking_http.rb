require "httparty"
require_relative "../../api.rb"

module Lita
  module Handlers
    MENTORS_CHANNEL = "three-thousand-more"

    class CUSlackingHTTP < Handler
      class BackendParty
          include HTTParty
          base_uri "http://127.0.0.1:3000"
      end

      def initialize(*args)
        super(*args)
        @api = SlackAPI.new(robot.config.adapters.slack)
      end

      on(:slack_reaction_added) do |payload|
        to_location = Lita::Source.new(room: Lita::Room.find_by_name(MENTORS_CHANNEL))

        if payload[:name].split('::')[0] == "+1"
          request_id = Lita.redis.get(payload[:item]["ts"] + payload[:item]["channel"])
          robot.send_message(to_location, "Thanks @#{payload[:user].mention_name} (#{request_id})!")

          user_profile = @api.user_profile(payload[:user].id)
          profile_pic_link = user_profile["user"]["profile"]["image_512"]
          real_name = user_profile["user"]["real_name"]

          # Send to server
          callback_data = {
            help_request_id: request_id,
            mentor_name: real_name,
            profile_pic: profile_pic_link
          }

          BackendParty.post('/help_requests/bot_callback', body: callback_data.to_json, headers: {"Content-Type": "application/json"})
        end
      end

      def help_request_message(data)
        help_request = data["help_request"]

        "#{data["user_name"] || "Someone"} needs help with " \
        "\"#{help_request["problem"] || "something"}\" at \"#{help_request["location"] || "unknown location"}\"." \
        " React :+1: to this message to take it! (request ID #{help_request["id"]})"
      end

      http.post "/help_request/start" do |request, response|
        data = JSON.parse(request.body.read)
        help_request = data["help_request"]

        to_location = Lita::Source.new(room: Lita::Room.find_by_name(MENTORS_CHANNEL))

        api_response = robot.send_message(to_location, help_request_message(data))

        # Allow lookups either way
        Lita.redis.set(api_response["ts"] + api_response["channel"], help_request["id"])
        Lita.redis.set(help_request["id"], api_response["ts"] + ":" + api_response["channel"])
      end

      http.post "/help_request/complete" do |request, response|
        data = JSON.parse(request.body.read)
        help_request = data["help_request"]

        timestamp, channel_id = Lita.redis.get(help_request["id"]).split(':')

        @api.update("~#{help_request_message(data)}~", ts: timestamp, channel: channel_id)
      end

      Lita.register_handler(self)
    end
  end
end
