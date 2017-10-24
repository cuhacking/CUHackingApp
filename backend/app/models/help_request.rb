class HelpRequest < ApplicationRecord
	belongs_to :user

	def mentors
		if self[:mentors]
			self[:mentors].split(',')
		else
			[]
		end
	end

	def mentors=(value)
		self[:mentors] = if value
			value.join(',')
		else
			nil
		end
	end
end
