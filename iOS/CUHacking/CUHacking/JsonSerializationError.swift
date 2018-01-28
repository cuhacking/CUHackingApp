//
//  JsonSerializationError.swift
//  CUHacking
//
//  Created by Jack McCracken on 2018-01-27.
//  Copyright © 2018 CUHacking. All rights reserved.
//

import Foundation

enum JsonSerializationError : Error {
    case missing(String)
    case invalid(String, Any)
}
