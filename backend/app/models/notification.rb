require "#{Rails.root}/lib/secrets"

class Notification < ApplicationRecord
  belongs_to :user, optional: true

  def send_notification!(data)
    # Send a notif to the client
    fcm = FCM.new(Secrets[:server_key])

    logger.info("Sending notification: #{{
      notification: {
        title: self.title,
        body: self.description
      },
      data: data.merge(notification: self.serializable_hash)
    }} to token #{self.user.token} (ID #{self.user.id})")

    options = {
      notification: {
        title: self.title,
        body: self.description
      },
      data: data.merge(notification: self.serializable_hash)
    }

    if self.user
      fcm.send([self.user.token], options)
    else
      response = fcm.send_with_notification_key("/topics/announcements", options)
    end
  end
end
