class EventsController < ApplicationController
  def index
    @event = Event.all

    respond_to do |format|
      format.json { render json: @event.to_json }
      format.html
    end
  end

  def show
    @event = Event.all

    respond_to do |format|
      format.json { render json: @event.to_json }
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
