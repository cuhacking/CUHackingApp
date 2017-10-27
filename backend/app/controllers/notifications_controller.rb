class NotificationsController < ApplicationController
  def index
    @notifications = Notification.where(user_id: [params[:user_id], nil]).limit(50)
    
    respond_to do |format|
      format.html
      format.json { render json: @notifications.to_json }
    end
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
