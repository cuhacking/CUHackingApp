class BuildingsController < ApplicationController
  def index
    @buildings = Building.all
  end

  def show
  end

  def create
    @building = Building.new(
      name: building_params[:name],
      initials: building_params[:initials]
    )
    @building.save!
    redirect_to :new_building
  end

  def building_params
    params[:building]
  end

  def new
    @building = Building.new
  end

  def destroy
  end
end
