class EventsController < ApplicationController
  def index
    @events = Event.all

    respond_to do |format|
      format.json { render json: @events.to_json }
      format.html
    end
  end

  def show
    @events = Event.all

    respond_to do |format|
      format.json { render json: @events.to_json }
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
