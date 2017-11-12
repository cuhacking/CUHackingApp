class Event < ApplicationRecord
	belongs_to :room
	belongs_to :company, optional: true
end
