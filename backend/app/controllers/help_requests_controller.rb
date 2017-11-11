require "#{Rails.root}/lib/secrets"

class HelpRequestsController < ApplicationController
  PENDING_MENTOR = "Pending mentor"
  MENTOR_FOUND = "Mentor found"

  def index
  end

  def show
    @help_request = HelpRequest.find(params[:id])

    respond_to do |format|
      format.json { render json: @help_request.to_json }
      format.html
    end
  end

  def create
    user = User.find(params[:user_id])
    user.name = params[:user_name]
    user.save!

    @help_request = HelpRequest.new(user: user, location: help_request_params[:location], problem: help_request_params[:problem], status: PENDING_MENTOR)
    @help_request.save!

    SendHelpRequestToBotJob.perform_later(@help_request)

    respond_to do |format|
      format.json { render json: @help_request.to_json }
      format.html
    end
  end

  def new
  end

  def update
    @help_request = HelpRequest.find(params[:id])

    if help_request_params[:status] == "Complete" && @help_request.status != "Complete"
      CompleteHelpRequestWithBotJob.perform_later(@help_request)
    end

    @help_request.update!(location: help_request_params[:location], problem: help_request_params[:problem], status: help_request_params[:status])

    respond_to do |format|
      format.html
      format.json { render json: @help_request.to_json }
    end
  end

  def help_request_params
    params[:help_request]
  end

  def bot_callback
    help_request = HelpRequest.find(params[:help_request_id])

    mentors = help_request.mentors

    # Check for duplicates
    mentors << params[:mentor_name] unless mentors.include? params[:mentor_name]

    help_request.mentors = mentors
    help_request.status = MENTOR_FOUND
    help_request.profile_pic_link = params[:profile_pic]
    help_request.save!

    notif = Notification.new(
            title: "Help is on the way!",
            description: "Mentor #{params[:mentor_name]} is coming to help you out :) Sit tight!",
            user: help_request.user)
    notif.save!
    notif.send_notification!({
      drawer_page: 3,
      help_request: help_request.serializable_hash
    })
  end

  def destroy
  end
end
