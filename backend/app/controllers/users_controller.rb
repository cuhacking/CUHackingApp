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

  def create
    @user = User.new
    @user.token = params[:token]
    @user.save!

    respond_to do |format|
      format.html
      format.json { render json: @user.to_json }
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
