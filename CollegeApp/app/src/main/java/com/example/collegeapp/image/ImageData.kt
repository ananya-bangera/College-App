package com.example.collegeapp.image

class ImageData {

    lateinit var image:String
    lateinit var key:String
    lateinit var category:String


    constructor(){}
    constructor( image: String, key:String, category:String) {
        this.image = image
        this.key = key
        this.category = category
    }

}