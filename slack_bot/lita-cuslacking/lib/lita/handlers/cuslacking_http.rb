require "httparty"

module Lita
  module Handlers
    MENTORS_CHANNEL = "three-thousand-more"

    class CUSlackingHTTP < Handler
        class BackendParty
            include HTTParty
            base_uri "http://127.0.0.1:3000"
        end

      on(:slack_reaction_added) do |payload|
        to_location = Lita::Source.new(room: Lita::Room.find_by_name(MENTORS_CHANNEL))

        if payload[:name].split('::')[0] == "+1"
          request_id = Lita.redis.get(payload[:item]["ts"] + payload[:item]["channel"])
          robot.send_message(to_location, "Thanks @#{payload[:user].mention_name} (#{request_id})!")

          # Send to server
          callback_data = {
            help_request_id: request_id,
            #TODO: Get the mentor's real name?
            mentor_name: payload[:user].mention_name
          }

          BackendParty.post('/help_requests/bot_callback', body: callback_data.to_json, headers: {"Content-Type": "application/json"})
        end
      end

      http.post "/help_request" do |request, response|
        data = JSON.parse(request.body.read)
        help_request = data["help_request"]

        to_location = Lita::Source.new(room: Lita::Room.find_by_name(MENTORS_CHANNEL))

        api_response = robot.send_message(to_location, "#{data["user_name"] || "Someone"} needs help with " \
          "\"#{help_request["problem"] || "something"}\" at \"#{help_request["location"] || "unknown location"}\"." \
          " React :+1: to this message to take it! (request ID #{help_request["id"]})")

        Lita.redis.set(api_response["ts"] + api_response["channel"], help_request["id"])

      end

      Lita.register_handler(self)
    end
  end
end
