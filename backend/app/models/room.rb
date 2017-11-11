class Room < ApplicationRecord
	has_many :event
	belongs_to :building
end
