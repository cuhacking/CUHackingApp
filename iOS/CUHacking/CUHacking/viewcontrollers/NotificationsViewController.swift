//
//  FirstViewController.swift
//  CUHacking
//
//  Created by Jack McCracken on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit

class NotificationsCell: UITableViewCell {
    @IBOutlet weak var notificationText: UILabel!
    @IBOutlet weak var notificationDescription: UILabel!
}

class NotificationsViewController: UITableViewController {
    private var notifications = [CUHNotification]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        User.getUser { (user) in
            guard let url = URL(string: "https://cuhacking.herokuapp.com/users/\(user.id)/notifications/") else {
                fatalError("Problems with URL")
            }
            
            var urlRequest = URLRequest(url: url)
            let session = URLSession.shared
            urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
            
            let task = session.dataTask(with: urlRequest) {
                (data, response, error) in
                if let error = error {
                    NSLog(error.localizedDescription)
                    return
                }
                
                // make sure we got data
                guard let data = data else {
                    print("Error: did not receive data")
                    return
                }
                do {
                    let decoder = JSONDecoder()
                    decoder.dateDecodingStrategy = .custom({ (decoder) -> Date in
                        let container = try decoder.singleValueContainer()
                        let dateStr = try container.decode(String.self)
                        
                        let formatter = DateFormatter()
                        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
                        
                        print(formatter.date(from: dateStr)!)
                        
                        return formatter.date(from: dateStr)!
                    })
                    try self.notifications = decoder.decode([CUHNotification].self, from: data)
                } catch { print("Error") }
                
                //swift 3
                DispatchQueue.main.async{
                    self.tableView.reloadData()
                }
            }
            
            task.resume()
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return notifications.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let notificationCell = tableView.dequeueReusableCell(withIdentifier: "notification_cell") as! NotificationsCell

        notificationCell.notificationText.text = notifications[indexPath.row].title
        notificationCell.notificationDescription.text = notifications[indexPath.row].description
        
        return notificationCell
    }
}

