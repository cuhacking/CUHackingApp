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
  end

  def new
  end

  def destroy
  end
end
