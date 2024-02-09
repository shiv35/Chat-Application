package com.example.chatapplication

class User {

    var Name:String?=null
    var email:String?=null
    var uid : String?=null
    constructor(){}
    constructor(Name:String?,email:String? , uid : String?=null)
    {
        this.Name = Name
        this.email = email
        this.uid = uid
    }
}
