module Lita
  module Handlers
    MENTORS_CHANNEL = "three-thousand-more"

    class CUSlackingHTTP < Handler
      http.post "/help_request" do |request, response|
        data = JSON.parse(request.body.read)

        to_location = Lita::Source.new(room: Lita::Room.find_by_name("three-thousand-more"))

        robot.send_message(to_location, "#{data["user_name"] || "Someone"} needs help with #{data["problem"] || "something"} at #{data["location"] || "unknown location"}. React :+1: to this message to take it!")
      end

      Lita.register_handler(self)
    end
  end
end
