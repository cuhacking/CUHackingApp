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
      puts params
  end

  def destroy
  end
end
