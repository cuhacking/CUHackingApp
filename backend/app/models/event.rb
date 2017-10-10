class Event < ApplicationRecord
	has_one :room
	has_one :company
end
