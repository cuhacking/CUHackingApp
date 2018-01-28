//
//  User.swift
//  CUHacking
//
//  Created by Zach Hauser on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

class User : Codable {
    var id: Int
    var name: String
    
    static var userId : Int?
    
    init(id: Int, name: String) {
        self.id = id
        self.name = name
    }
    
    static func getUserId() throws -> Int {
        if let userId = userId {
            return userId
        }

        let fileManager = FileManager.default
        var url = try fileManager.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
        url.appendPathComponent("userId.txt")
        userId = try Int(String(contentsOf: url))

        return userId!
    }
}
