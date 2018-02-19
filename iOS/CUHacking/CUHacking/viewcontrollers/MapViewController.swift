//
//  MapViewController.swift
//  CUHacking
//
//  Created by Esti Tweg on 2018-02-18.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import UIKit


class MapViewController: UIPageViewController, UIPageViewControllerDelegate{
    
     fileprivate lazy var pages: [UIViewController] = {
        return [MapViewController.newMapViewController(idenifier: "MapView1"),
                MapViewController.newMapViewController(idenifier: "MapView2"),
                MapViewController.newMapViewController(idenifier: "MapView3")]
    }()
    
    var pageControl = UIPageControl()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.dataSource = self
        self.delegate = self
        
        if let firstViewController = pages.first {
            setViewControllers([firstViewController],
                               direction: .forward,
                               animated: true,
                               completion: nil)
        }
        configurePageControl()
    }
    
    func configurePageControl() {
        pageControl = UIPageControl(frame: CGRect(x: 0,y: UIScreen.main.bounds.maxY - 50,width: UIScreen.main.bounds.width,height: 50))
        self.pageControl.numberOfPages = pages.count
        self.pageControl.currentPage = 0
        self.pageControl.tintColor = UIColor.lightGray
        self.pageControl.pageIndicatorTintColor = UIColor.lightGray
        self.pageControl.currentPageIndicatorTintColor = UIColor.gray
        
        let controlHeight: CGFloat = 60
        let bottomSpace: CGFloat = 70
        let yPosition = view.frame.size.height - (controlHeight + bottomSpace)
        let fullScreenWidth = view.frame.size.width
        let frame = CGRect(x: 0, y: yPosition, width: fullScreenWidth, height: controlHeight)
        
        pageControl.frame = frame
        self.view.addSubview(pageControl)
    }
    
    private class func newMapViewController(idenifier: String) -> UIViewController {
        return UIStoryboard(name: "Main", bundle: nil) .
            instantiateViewController(withIdentifier: idenifier)
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, didFinishAnimating finished: Bool, previousViewControllers: [UIViewController], transitionCompleted completed: Bool) {
        let pageContentViewController = pageViewController.viewControllers![0]
        self.pageControl.currentPage = pages.index(of: pageContentViewController)!
    }
}


extension MapViewController: UIPageViewControllerDataSource{
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerBefore viewController: UIViewController) -> UIViewController? {
        guard let viewControllerIndex = pages.index(of: viewController) else { return nil }
        let previousIndex = viewControllerIndex - 1
        guard previousIndex >= 0          else { return pages.last }
        guard pages.count > previousIndex else { return nil        }
        return pages[previousIndex]
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerAfter viewController: UIViewController) -> UIViewController?{
        guard let viewControllerIndex = pages.index(of: viewController) else { return nil }
        let nextIndex = viewControllerIndex + 1
        guard nextIndex < pages.count else { return pages.first }
        guard pages.count > nextIndex else { return nil         }
        return pages[nextIndex]
    }
    
    func presentationCountForPageViewController(pageViewController: UIPageViewController) -> Int {
        return pages.count
    }
    
    func presentationIndexForPageViewController(pageViewController: UIPageViewController) -> Int {
        guard let firstViewController = viewControllers?.first,
            let firstViewControllerIndex = pages.index(of: firstViewController) else {
                return 0
        }
        return firstViewControllerIndex
    }
}
