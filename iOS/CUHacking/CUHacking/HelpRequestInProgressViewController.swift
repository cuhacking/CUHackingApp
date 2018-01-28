//
//  HelpRequestInProgressViewController.swift
//  CUHacking
//
//  Created by Jack McCracken on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit
import Firebase

class HelpRequestInProgressViewController: UIViewController {
    @IBOutlet weak var profilePicImageView: UIImageView!
    @IBOutlet weak var mentorOnWayText: UILabel!
    
    @IBOutlet weak var mentorOnWayButton: UIButton!
    var helpRequest : HelpRequest!
    
    override func viewDidLoad() {
        loadHelpRequest()
    }
    
    func loadHelpRequest() {
        if let profilePicLink = helpRequest.profilePictureURL {
            DispatchQueue.global(qos: .userInitiated).async {
                do {
                    let data = try Data(contentsOf: URL(string: profilePicLink)!)
                    let image = UIImage(data: data)
                    
                    DispatchQueue.main.async {
                        self.profilePicImageView.image = image
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
