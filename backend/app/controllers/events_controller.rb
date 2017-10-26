class EventsController < ApplicationController
  def index
  end

  def show
    /todo findall/
    @event = Event.find(params[:id])

    respond_to do |format|
      format.json { render json: @event.to_json }
      format.html
  end

  def create
    @event = Event.new.save!

    respond_to do |format|
      format.html
      format.json { render json: @event.to_json }
    end
  end

  def new
  end

  def destroy
  end
end
