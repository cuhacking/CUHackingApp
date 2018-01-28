//
//  RequestHelpViewController.swift
//  CUHacking
//
//  Created by Jack McCracken on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit

class RequestHelpViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var locationField: UITextField!
    @IBOutlet weak var descriptionField: UITextField!

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let helpRequest = sender as! HelpRequest
        
        let helpRequestInProgressVC = segue.destination as! HelpRequestInProgressViewController
        
        helpRequestInProgressVC.helpRequest = helpRequest
    }
    
    @IBAction func getHelpButtonPressed(_ getHelpButton: UIButton) {
        HelpRequest.create(name: nameField.text!, location: locationField.text!, description: descriptionField.text!) { (helpRequest) in
            // Do the segue
            DispatchQueue.main.async {
                self.performSegue(withIdentifier: "HRCreated", sender: helpRequest)
            }
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}


