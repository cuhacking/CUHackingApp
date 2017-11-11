class Event < ApplicationRecord
	belongs_to :room
	belongs_to :company
end
