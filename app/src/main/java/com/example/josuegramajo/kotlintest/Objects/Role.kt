package com.example.josuegramajo.kotlintest.Objects

/**
 * Created by josuegramajo on 8/22/18.
 */
class Role{
    var role:String? = null
    var uid:String? = null

    constructor(){}

    constructor(role:String, uid:String){
        this.role = role
        this.uid = uid
    }
}