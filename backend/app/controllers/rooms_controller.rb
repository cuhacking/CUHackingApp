class RoomsController < ApplicationController
  def index
  end

  def show
  end

  def create
    @room = Room.new(
      name: room_params[:name],
      room_type: room_params[:room_type],
      building_id: room_params[:building_id]
    )
    @room.save!

    redirect_to :new_room
  end

  def name
    byebug
    super
  end

  def room_params
    params[:room]
  end

  def new
    @room = Room.new
  end

  def destroy
  end
end
