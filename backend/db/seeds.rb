# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

forest = User.new(name: "Forest")

help = HelpRequest.new(user: forest, problem: "Need help with ruby")

company = Company.new(name: "Spotify", tier: "Gold", website_url: "https://open.spotify.com/browse", logo: "http://www.scdn.co/i/_global/open-graph-default.png")

building = Building.new(name: "Herzberg", initials: "HP")

room = Room.new(room_type: "Chill", name: "Comp Sci Lounge", building: building)

forest.save!
help.save!
company.save!
room.save!
building.save!

event = Event.new(name: "3004 Meeting", event_type: "Meeting", description: "3004 Meeting", room: room, company: company, date: Date.parse('2017-04-20'), start_time: Time.parse("00:04:20"), end_time: Time.parse("00:06:20"))
event2 = Event.new(name: "3004 Meeting, but Jack just arrived", event_type: "Meeting", description: "3004 Meeting but now Jack is here", room: room, company: company, date: Date.parse('2017-04-20'), start_time: Time.parse("00:04:30"), end_time: Time.parse("00:06:30"))

event.save!
event2.save!

#notification = 
