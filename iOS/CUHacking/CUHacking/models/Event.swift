//
//  Event.swift
//  CUHacking
//
//  Created by Zach Hauser on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

class Event {
    
    var id: Int
    var name: String
    var date: Date
    
    var startTime: Date
    var endTime: Date
    
    var type: String
    var description: String
    
    var room: Room
    var company: Company
    
    init(id: Int, name: String, date: Date, startTime: Date, endTime: Date, type: String, description: String, room: Room, company: Company) {
        
        self.id = id
        self.name = name
        self.date = date
        self.startTime = startTime
        self.endTime = endTime
        self.type = type
        self.description = description
        self.room = room
        self.company = company
    }
}
