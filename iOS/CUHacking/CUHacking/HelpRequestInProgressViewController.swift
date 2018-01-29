//
//  HelpRequestInProgressViewController.swift
//  CUHacking
//
//  Created by Jack McCracken on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit
import Firebase

extension Notification.Name {
    static let helpRequestUpdated = Notification.Name("HelpRequestUpdated")
}

class HelpRequestInProgressViewController: UIViewController {
    @IBOutlet weak var profilePicImageView: UIImageView!
    @IBOutlet weak var mentorOnWayText: UILabel!
    
    @IBOutlet weak var mentorOnWayButton: UIButton!
    var helpRequest : HelpRequest!
    
    override func viewDidLoad() {
        NotificationCenter.default.addObserver(self, selector: #selector(self.helpRequestUpdated(_:)), name: .helpRequestUpdated, object: nil)
        loadHelpRequest()
    }
    
    @objc func helpRequestUpdated(_ notification: NSNotification) {
        do {
        if let userInfo = notification.userInfo {
            guard let helpRequestJson = userInfo["help_request"] as? String else {
                print("Help request of wrong type")
                return
            }
            
            self.helpRequest = try JSONDecoder().decode(HelpRequest.self, from: helpRequestJson.data(using: .ascii)!)
            loadHelpRequest()
        }
        } catch { print("Bad help request") }
    }
    
    @IBAction func mentorHereButtonPressed(_ sender: Any) {
        helpRequest.status = "Complete"

        helpRequest.update { (helpRequest) in
            self.helpRequest = helpRequest
            
            DispatchQueue.main.async {
                self.navigationController?.popViewController(animated: true)
            }
        }
    }

    func loadHelpRequest() {
        if let profilePicLink = helpRequest.profilePictureURL {
            DispatchQueue.global(qos: .userInitiated).async {
                do {
                    let data = try Data(contentsOf: URL(string: profilePicLink)!)
                    let image = UIImage(data: data)
                    
                    DispatchQueue.main.async {
                        self.profilePicImageView.image = image
                        self.mentorOnWayButton.isHidden = false
                    }
                } catch {
                    NSLog("Failed to get profile pic")
                }
            }
        }
        
        if helpRequest.mentors.isEmpty {
            mentorOnWayText.text = "Hang tight, a mentor will be with you soon!"
        }
        else {
            mentorOnWayText.text = "Hang tight, \(helpRequest.mentors[0]) will be with you soon!"
        }
    }
}
