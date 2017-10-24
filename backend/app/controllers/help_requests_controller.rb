require "#{Rails.root}/lib/secrets"

class HelpRequestsController < ApplicationController
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

    @help_request = HelpRequest.new(user: user, location: help_request_params[:location], problem: help_request_params[:problem])
    @help_request.save!

    SendHelpRequestToBotJob.perform_later(@help_request)

    respond_to do |format|
      format.json { render json: @help_request.to_json }
      format.html
    end
  end

  def new
  end

  def help_request_params
    params[:help_request]
  end

  def bot_callback
    help_request = HelpRequest.find(params[:help_request_id])

    mentors = help_request.mentors
    mentors << params[:mentor_name]
    help_request.mentors = mentors

    help_request.save!

    # Send a notif to the client
    fcm = FCM.new(Secrets[:server_key])

    tokens = [help_request.user.token] # an array of one or more client registration tokens
    options = {
      notification: {
        title: "Help is on the way!",
        body: "Mentor #{params[:mentor_name]} is coming to help you out :) Sit tight!"
      },
      data: {
        drawer_page: 3
      }
    }
    response = fcm.send(tokens, options)
  end

  def destroy
  end
end
