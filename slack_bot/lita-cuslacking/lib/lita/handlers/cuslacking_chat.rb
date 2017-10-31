require 'httparty'

module Lita
  module Handlers
      class CUSlackingChat < Handler
        class BackendParty
          include HTTParty
          base_uri "http://127.0.0.1:3000"
        end

        BOT_CONTROL_CHANNEL = "three-thousand-more"
        ANNOUNCEMENTS_CHANNEL = "fake-announcements"

        route(/announce\s+(.+)/, command: true, help: {
          "announce TEXT" => "Make an announcement on Slack and the app with the given text"
        }) do |response|
          target = Lita::Source.new(room: Lita::Room.find_by_name(ANNOUNCEMENTS_CHANNEL))
          robot.send_message(target, response.matches[0])

          callback_data = {
            announcement: response.matches[0][0]
          }

          BackendParty.post('/notifications/make_announcement', body: callback_data.to_json, headers: {"Content-Type": "application/json"})
        end

        Lita.register_handler(self)
    end
  end
end