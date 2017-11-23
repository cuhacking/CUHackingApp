class Building < ApplicationRecord
	has_many :room

	def str
		"#{initials} - #{name}"
	end
end
