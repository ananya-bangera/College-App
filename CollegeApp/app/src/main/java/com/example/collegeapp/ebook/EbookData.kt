package com.example.collegeapp.ebook

class EbookData {
    lateinit var name:String
    lateinit var pdfUrl:String

    constructor(){}
    constructor(name: String,pdfUrl:String) {
        this.name = name
        this.pdfUrl=pdfUrl
    }


}