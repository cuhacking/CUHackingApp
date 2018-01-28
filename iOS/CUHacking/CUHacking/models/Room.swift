//
//  Room.swift
//  CUHacking
//
//  Created by Zach Hauser on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

class Room {
    var id: Int
    var type: String
    var name: String
    
    init(id: Int, type: String, name: String) {
        self.id = id
        self.type = type
        self.name = name
    }

}
