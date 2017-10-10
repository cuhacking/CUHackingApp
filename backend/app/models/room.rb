class Room < ApplicationRecord
	belongs_to :event
	belongs_to :building
end
