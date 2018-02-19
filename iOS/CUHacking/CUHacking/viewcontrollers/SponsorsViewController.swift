//
//  SponsorsViewController.swift
//  CUHacking
//
//  Created by Esti Tweg on 2018-02-18.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit

class SponsorCell: UITableViewCell {
    @IBOutlet weak var sponsorImage: UIImageView!
}

class SponsorsViewController: UITableViewController {
    
    var sponsorImages = [UIImage(named: "cse.jpg")!, UIImage(named: "foko.jpg")!, UIImage(named: "inova.jpg")!, UIImage(named: "kx.jpg")!, UIImage(named: "magmic.jpg")!, UIImage(named: "martello.jpg")!, UIImage(named: "pythian.jpg")!, UIImage(named: "stdlib.jpg")!, UIImage(named: "you.jpg")!]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sponsorImages.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let sponsorCell = tableView.dequeueReusableCell(withIdentifier: "sponsor_cell") as! SponsorCell
        
        sponsorCell.sponsorImage.image = sponsorImages[indexPath.row]
        
        return sponsorCell
    }
    
}
