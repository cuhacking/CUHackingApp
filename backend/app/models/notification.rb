require "#{Rails.root}/lib/secrets"

class Notification < ApplicationRecord
  def send_notification!(data)
    fcm = FCM.new(Secrets[:server_key])

    options = {
      notification: {
        body: self.description,
        title: self.title
      },
      data: data
    }

    if self.audience
      #TODO: Use the client key in audience to send the notif
    else
      fcm.send_to_topic(options)
    end
  end
end
