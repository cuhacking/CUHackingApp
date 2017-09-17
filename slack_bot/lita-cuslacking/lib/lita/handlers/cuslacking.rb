module Lita
  module Handlers
    class CUSlacking < Handler
      # insert handler code here
      route(/call everyone nerds/, :call_everyone_nerds)

      def call_everyone_nerds(response)
          if response.private_message?
            response.reply "You're the only nerd here..."
          else
            users = robot.roster(response.room).map { |user_id| "@#{User.find_by_id(user_id).mention_name}" }
            users = users.reject { |user_name| user_name == "@cuslacking" }

            response.reply "Sup nerds #{users.join(' ')}!"
          end
      end

      Lita.register_handler(self)
    end
  end
end
