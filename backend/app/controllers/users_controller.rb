class UsersController < ApplicationController
  def index
  end

  def show
    @user = User.find(params[:id])

    respond_to do |format|
      format.html
      format.json { render json: @user.to_json }
    end
  end

  def notifications
    @notifications = Notification.where(user_id: [params[:id], nil]).limit(50)
    
    respond_to do |format|
      format.html
      format.json { render json: @notifications.to_json }
    end
  end

  def create
    @user = User.new
    @user.token = params[:token]
    @user.save!

    respond_to do |format|
      format.html
      format.json { render json: @user.to_json }
    end
  end

  def update
    @user = User.find(params[:id])
    @user.token = params[:token]
    @user.save!

    respond_to do |format|
      format.html
      format.json { render json: @user.to_json }
    end
  end

  def help_request
    @user = User.find(params[:id])

    respond_to do |format|
      format.html
      format.json {render json: @user.help_requests.where.not(status: "Completed").first.to_json}
    end
  end

  def new
  end

  def user_params
    params
  end

  def destroy
  end
end
