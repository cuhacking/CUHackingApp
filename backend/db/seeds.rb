# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

forest = User.create!(name: "Forest")

HelpRequest.create!(user: forest, problem: "Need help with ruby")

martello = Company.create!(name: "Martello", tier: "Gold", website_url: "http://martellotech.com/", logo: "http://martellotech.com/wp-content/uploads/2014/08/logo-login.png")

river = Building.create!(name: "Herzberg Laboratories", initials: "HB")
herzberg = Building.create!(name: "River Building", initials: "RB")

river_2200 = Room.create!(room_type: "Theater", name:"River 2200", building: river)
herzberg_3434 = Room.create!(room_type: "Hacking Space", name:"Herzberg 3434", building: herzberg)

Event.create!(
	name: "Registration Opens",
	start_time: "March 11 2018 18:00:00",
	end_time: "March 11 2018 19:00:00",
	event_type: "Registration",
	description: "During this time hackers will line up in Minto to receive their CUHacking swag bag then have a chance to meet other hackers. Included in the swag bag is a t-shirt, stickers, hackthon handbook and many more fun goodies.",
	room: river_2200)

Event.create!(
	name: "Opening Ceremonies",
	start_time: "March 11 2018 19:00:00",
	end_time: "March 11 2018 20:00:00",
	event_type: "Opening Ceremony",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "Hacking Begins",
	start_time: "March 11 2018 20:00:00",
	end_time: "March 11 2018 20:00:00",
	event_type: "Hacking Starts",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "Lunch",
	start_time: "March 12 2018 12:00:00",
	end_time: "March 12 2018 13:00:00",
	event_type: "Lunch",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "iOS Workshop",
	start_time: "March 12 2018 13:00:00",
	end_time: "March 12 2018 14:00:00",
	event_type: "Workshop",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "Databases Workshop",
	start_time: "March 12 2018 15:00:00",
	end_time: "March 12 2018 16:00:00",
	event_type: "Workshop",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "Dinner",
	start_time: "March 12 2018 18:00:00",
	end_time: "March 12 2018 19:00:00",
	event_type: "Ceremony",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "Midnight Yoga",
	start_time: "March 12 2018 24:00:00",
	end_time: "March 13 2018 01:00:00",
	event_type: "Hacking",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "Breakfast",
	start_time: "March 13 2018 07:00:00",
	end_time: "March 13 2018 08:00:00",
	event_type: "Food",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "React Workshop",
	start_time: "March 13 2018 09:50:00",
	end_time: "March 13 2018 10:00:00",
	event_type: "Workshop",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)

Event.create!(
	name: "Rails Workshop",
	start_time: "March 13 2018 10:00:00",
	end_time: "March 13 2018 11:20:00",
	event_type: "Workshop",
	description: "work on some stuff",
	room: herzberg_3434,
	company: martello)
