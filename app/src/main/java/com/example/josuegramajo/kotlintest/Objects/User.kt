package com.example.josuegramajo.kotlintest.Objects

/**
 * Created by josuegramajo on 8/23/18.
 */
class User : BaseObject{
    var name:String? = null
    var email:String? = null
    var role:String? = null
    var uid:String? = null

    constructor(){}

    constructor(name:String, email:String, role:String, uid:String){
        this.name = name
        this.email = email
        this.role = role
        this.uid = uid
    }
}