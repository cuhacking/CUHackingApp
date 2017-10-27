class Room < ApplicationRecord
	has_and_belongs_to_many :event
	belongs_to :building
end
