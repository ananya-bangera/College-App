package com.example.collegeapp.notice

class NoticeData {

    lateinit var title:String
    lateinit var image:String
    lateinit var date:String
    lateinit var time:String
    lateinit var key:String

    constructor(){}
    constructor( title: String ,image: String, date: String, time:String, key:String) {
        this.date = date
        this.image = image
        this.title = title
        this.time = time
        this.key = key
    }

}