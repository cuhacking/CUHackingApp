class CompaniesController < ApplicationController
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
  end

  def new
  end

  def destroy
  end
end
