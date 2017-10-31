class NotificationsController < ApplicationController
  def index
  end

  def show
  end

  def create
  end

  def new
  end

  def destroy
  end

  def make_announcement
    notification = Notification.new(title: "CUHacking Announcement", description: params[:announcement])
    notification.save!

    notification.send_notification!({})
  end
end
