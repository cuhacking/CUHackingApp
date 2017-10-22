module Lita
  module Handlers
    MENTORS_CHANNEL = "three-thousand-more"

    class CUSlackingHTTP < Handler
      on(:slack_reaction_added) do |payload|
        to_location = Lita::Source.new(room: Lita::Room.find_by_name(MENTORS_CHANNEL))

        if payload[:name].split('::')[0] == "+1"
          robot.send_message(to_location, "Thanks @#{payload[:user].mention_name}!")
        end
      end

      http.post "/help_request" do |request, response|
        data = JSON.parse(request.body.read)
        help_request = data["help_request"]

        to_location = Lita::Source.new(room: Lita::Room.find_by_name(MENTORS_CHANNEL))

        robot.send_message(to_location, "#{data["user_name"] || "Someone"} needs help with " \
          "\"#{help_request["problem"] || "something"}\" at \"#{help_request["location"] || "unknown location"}\"." \
          " React :+1: to this message to take it! (request ID #{help_request["id"]})")
      end

      Lita.register_handler(self)
    end
  end
end
