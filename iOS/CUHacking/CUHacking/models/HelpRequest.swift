//
//  HelpRequest.swift
//  CUHacking
//
//  Created by Elisa Kazan on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

class HelpRequest : Codable {
    var id: Int
    var location: String
    var problem: String
    var mentors: [String]
    var status: String
    
    var profilePictureURL: String?
    
    init(id: Int, location: String, problem: String, mentors: [String], status: String, profilePictureURL: String?) {
        self.id = id
        self.location = location
        self.problem = problem
        self.mentors = mentors
        self.status = status
        self.profilePictureURL = profilePictureURL
    }
    
    static func create(name: String, location: String, description: String, completionHandler: @escaping (HelpRequest) -> Void) {
        User.getUser { (user) in
            guard let url = URL(string: "https://cuhacking.herokuapp.com/help_requests") else {
                fatalError("Problems with URL")
            }
            
            var urlRequest = URLRequest(url: url)
            
            urlRequest.httpMethod = "POST"
            
            let helpRequestJson : [String:Any] = [
                "help_request":
                    [
                        "location": location,
                        "problem": description
                        ] as [String:Any],
                "user_name": name,
                "user_id": user.id
            ]

            let session = URLSession.shared
            urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
            
            do {
                try urlRequest.httpBody = JSONSerialization.data(withJSONObject: helpRequestJson, options: .init(rawValue: 0))
                
                let task = session.dataTask(with: urlRequest) {
                    (data, response, error) in
                    if let error = error {
                        NSLog(error.localizedDescription)
                        return
                    }
                    
                    do {
                        guard let data = data else {
                            NSLog("No data provided")
                            return
                        }
                        
                        let helpRequest = try JSONDecoder().decode(HelpRequest.self, from: data)
                        
                        completionHandler(helpRequest)
                    } catch {
                        NSLog("Failed decode of request")
                    }
                }
                
                task.resume()
            } catch {
                NSLog("Failed request")
            }
        }
    }
    
    func update(completionHandler: @escaping (HelpRequest) -> Void) {
        User.getUser { (user) in
            guard let url = URL(string: "https://cuhacking.herokuapp.com/help_requests/\(self.id)") else {
                fatalError("Problems with URL")
            }
            
            var urlRequest = URLRequest(url: url)
            
            urlRequest.httpMethod = "PUT"
            
            let helpRequestJson : [String:Any] = [
                "help_request":
                    [
                        "location": self.location,
                        "problem": self.problem,
                        "status": self.status
                    ] as [String:Any]
            ]
            
            let session = URLSession.shared
            urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
            urlRequest.setValue("application/json", forHTTPHeaderField: "Accept")
            
            do {
                try urlRequest.httpBody = JSONSerialization.data(withJSONObject: helpRequestJson, options: .init(rawValue: 0))
                
                let task = session.dataTask(with: urlRequest) {
                    (data, response, error) in
                    if let error = error {
                        NSLog(error.localizedDescription)
                        return
                    }
                    
                    do {
                        guard let data = data else {
                            NSLog("No data provided")
                            return
                        }
                        
                        let helpRequest = try JSONDecoder().decode(HelpRequest.self, from: data)
                        
                        completionHandler(helpRequest)
                    } catch {
                        NSLog("Failed decode of request")
                    }
                }
                
                task.resume()
            } catch {
                NSLog("Failed request")
            }
        }
    }

    enum CodingKeys : String, CodingKey {
        case id
        case location
        case problem
        case mentors
        case status
        case profilePictureURL = "profile_pic_link"
    }
}
