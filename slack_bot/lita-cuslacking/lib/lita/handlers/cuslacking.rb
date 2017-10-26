module Lita
  module Handlers
    class CUSlacking < Handler
      # insert handler code here
      route(/call everyone nerds/, :call_everyone_nerds, command: true, help: {
        "call everyone nerds" => "Calls everyone nerds!"
        })

      route(/what time is it?/, :get_time, command: true, help: {
        "what time is it?" => "Tells you to check the time yourself"
        })
      
      BOT_CONTROL_CHANNEL = "three-thousand-more"
      ANNOUNCEMENTS_CHANNEL = "fake-announcements"

      route(/^announce\s+(.+)/, command: true, help: {
        "announce TEXT" => "Announce the provided text through the app and the announcements channel"
      }) do |response|
        if Lita::Room.find_by_id(response.room.id).name == BOT_CONTROL_CHANNEL
          target = Lita::Source.new(room: Lita::Room.find_by_name(ANNOUNCEMENTS_CHANNEL))
          robot.send_message(target, response.matches[0])

          notification = Notification.new(audience: nil, title: "CUHacking Announcement", description: response.matches[0])
          notification.save!

          notification.send_notification!
        end
      end

      def call_everyone_nerds(response)
          if response.private_message?
            response.reply "You're the only nerd here..."
          else
            users = robot.roster(response.room)
            users = users.map {|user_id| "@#{User.find_by_id(user_id).mention_name}" }
            users = users.reject { |user_name| user_name == "@cuslacking" }

            response.reply "Sup nerds #{users.join(' ')}!"
          end
      end

      def get_time(response)
        response.reply "Just look up or #{Time.now}"
      end

      Lita.register_handler(self)
    end
  end
end
