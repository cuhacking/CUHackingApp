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
}

class NotificationsViewController: UITableViewController {
    private var notifications = [Notification]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 100
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let notificationCell = tableView.dequeueReusableCell(withIdentifier: "notification_cell") as! NotificationsCell

        notificationCell.notificationText.text = "Hello world"
        
        return notificationCell
    }
}

