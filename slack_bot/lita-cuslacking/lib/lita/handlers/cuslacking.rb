module Lita
  module Handlers
    class CUSlacking < Handler
      # insert handler code here
      route(/call everyone nerds/, :call_everyone_nerds)

      def call_everyone_nerds(response)
          users = robot.roster(response.room).map { |user_id| "@#{User.find_by_id(user_id).mention_name}" }

          response.reply "Sup nerds #{users.join(' ')}!"
      end

      Lita.register_handler(self)
    end
  end
end
