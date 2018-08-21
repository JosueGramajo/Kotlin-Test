package com.example.josuegramajo.kotlintest.Objects

/**
 * Created by josuegramajo on 8/21/18.
 */
class Rol{
    var rol:String? = null
    var uid:String? = null

    constructor(){}

    constructor(rol:String, uid:String){
        this.rol = rol
        this.uid = uid
    }
}
