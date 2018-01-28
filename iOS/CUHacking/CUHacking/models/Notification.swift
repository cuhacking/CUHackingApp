//
//  Notification.swift
//  CUHacking
//
//  Created by Zach Hauser on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

class Notification : Codable {
    var id: Int
    var title: String
    var description: String

    var createdAt: Date
    
    init(id: Int, title: String, description: String, createdAt: Date) {
        self.id = id
        self.title = title
        self.description = description
        self.createdAt = createdAt
    }
    
    enum CodingKeys : String, CodingKey {
        case id
        case title
        case description
        case createdAt = "created_at"
    }
}
