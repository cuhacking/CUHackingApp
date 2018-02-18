//
//  RequestHelpViewController.swift
//  CUHacking
//
//  Created by Jack McCracken on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit

class RequestHelpViewController: UIViewController, UITextFieldDelegate {
    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var locationField: UITextField!
    @IBOutlet weak var descriptionField: UITextField!
    
    var fields : [UITextField]!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        fields = [nameField, locationField, descriptionField]
        
        nameField.delegate = self
        locationField.delegate = self
        descriptionField.delegate = self
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        // Try to find next responder
        if let nextField = textField.superview?.viewWithTag(textField.tag + 1) as? UITextField {
            nextField.becomeFirstResponder()
        } else {
            // Not found, so remove keyboard.
            textField.resignFirstResponder()
        }
        // Do not add a line break
        return false
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let helpRequest = sender as! HelpRequest
        
        let helpRequestInProgressVC = segue.destination as! HelpRequestInProgressViewController
        
        nameField.text = ""
        locationField.text = ""
        descriptionField.text = ""
        
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


