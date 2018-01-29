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
    var name: String?
    
    static var userId : Int?
    
    static var waitingHandlers = [(User) -> Void]()
    
    init(id: Int, name: String?) {
        self.id = id
        self.name = name
    }
    
    static func getUser(completionHandler: @escaping (User) -> Void) {
        DispatchQueue.global(qos: .userInteractive).async {
            do {
                completionHandler(User(id: try getUserId(), name: ""))
            } catch {
                waitingHandlers.append(completionHandler)
                if waitingHandlers.count == 1 {
                    guard let url = URL(string: "https://cuhacking.herokuapp.com/users/") else {
                        fatalError("Problems with URL")
                    }
                    
                    var urlRequest = URLRequest(url: url)
                    let session = URLSession.shared
                    urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
                    urlRequest.httpMethod = "POST"
                    
                    let task = session.dataTask(with: urlRequest, completionHandler: { (data, response, error) in
                        do {
                            if let error = error {
                                NSLog("Error while downloading user information: \(error.localizedDescription)")
                                return
                            }
                            
                            guard let data = data else {
                                NSLog("No data returned")
                                return
                            }
                            
                            let user = try JSONDecoder().decode(User.self, from: data)
                            
                            // Write the user ID to a file
                            let fileManager = FileManager.default
                            var url = try fileManager.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
                            url.appendPathComponent("userId.txt")
                            try String(describing: user.id).write(to: url, atomically: false, encoding: .ascii)
                            
                            for handler in waitingHandlers {
                                handler(user)
                            }
                        
                            // Clear out all
                            waitingHandlers = []
                        } catch {
                            NSLog("Error while decoding user.")
                        }
                    })
                    
                    task.resume()
                }
                else {
                    waitingHandlers.append(completionHandler)
                }
            }
        }
    }

    private static func getUserId() throws -> Int {
        if let userId = userId {
            return userId
        }

        let fileManager = FileManager.default
        var url = try fileManager.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
        url.appendPathComponent("userId.txt")
        userId = try Int(String(contentsOf: url))

        return userId!
    }
    
    func applyToken(token : String) {
        do {
            guard let url = URL(string: "https://cuhacking.herokuapp.com/users/\(self.id)") else {
                fatalError("Problems with URL")
            }
            var urlRequest = URLRequest(url: url)
            let session = URLSession.shared
            urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
            urlRequest.httpMethod = "PUT"
            let userJson : [String: Any] = [
                "id": self.id,
                "token": token
            ]
            
            urlRequest.httpBody = try JSONSerialization.data(withJSONObject: userJson, options: .init(rawValue: 0))
            
            let task = session.dataTask(with: urlRequest, completionHandler: { (data, response, error) in
                if let error = error {
                    NSLog("Error while downloading user information: \(error.localizedDescription)")
                    return
                }
                
                guard let data = data else {
                    NSLog("No data returned")
                    return
                }
    
                NSLog("Added token: \(token), data: \(String(data: data, encoding: .ascii) ?? "Bad encoding")")
            })

            task.resume()
        } catch {
            NSLog("Could not create JSON object to send to server")
        }
    }
}
