class EventsController < ApplicationController
  def index
  end
 
  def show
    /todo findall/
    @event = Event.find(:all)
 
    respond_to do |format|
      format.html
      format.json { render json: @event.to_json }
    end
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