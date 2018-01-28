//
//  Company.swift
//  CUHacking
//
//  Created by Zach Hauser on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

class Company : Codable {
    var id: Int
    var name: String
    var tier: String
    var websiteURL: String
    var logo: String

    enum CodingKeys : String, CodingKey {
        case id
        case name
        case tier
        case websiteURL = "website_url"
        case logo
    }

    init(id: Int, name: String, tier: String, websiteURL: String, logo: String) {
        self.id = id
        self.name = name
        self.tier = tier
        self.websiteURL = websiteURL
        self.logo = logo
    }
    
}
