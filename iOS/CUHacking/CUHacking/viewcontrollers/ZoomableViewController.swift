//
//  ZoomableViewController.swift
//  CUHacking
//
//  Created by Esti Tweg on 2018-02-19.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit

//https://stackoverflow.com/questions/30014241/uiimageview-pinch-zoom-swift
class ZoomableViewController: UIViewController, UIScrollViewDelegate {
    
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var imgPhoto: UIImageView!
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        scrollView.minimumZoomScale = 1.0
        scrollView.maximumZoomScale = 6.0
    }
    
    func viewForZooming(in scrollView: UIScrollView) -> UIView? {
        
        return imgPhoto
    }

}
