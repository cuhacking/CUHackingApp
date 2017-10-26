# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

if Rails.env.development?
	forest = User.new(name: "Forest").save!

	help = HelpRequest.new(user: forest, problem: "Plz hlp boxx").save!

	spotify = Company.new(name: "Spotify", tier: "Gold", website_url: "https://open.spotify.com/browse", logo: "http://www.scdn.co/i/_global/open-graph-default.png").save!

	room = Room.new(type: "Chill", name: "Comp Sci Lounge")

	building = Building.new(name: "Herzberg", initials: "HP")

	room.building = building

	building.rooms = room

	event = Event.new(name: "3004 Meeting", type: "Meeting", description: "3004 Meeting", room: room, company: company)

	#notification = 
end
