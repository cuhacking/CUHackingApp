//
//  HelpRequest.swift
//  CUHacking
//
//  Created by Elisa Kazan on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

class HelpRequest {
    var id: Int
    var location: String
    var problem: String
    var mentors: [String]
    var status: String
    
    var profilePictureURL: String
    
    init(id: Int, location: String, problem: String, mentors: [String], status: String, profilePictureURL: String) {
        self.id = id
        self.location = location
        self.problem = problem
        self.mentors = mentors
        self.status = status
        self.profilePictureURL = profilePictureURL
    }
    
    
    
}
