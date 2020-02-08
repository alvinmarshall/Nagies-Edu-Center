package com.cheise_proj.domain.entity.message

interface IMessage {
    val uid:Int
    val sender:String
    val content:String
    val date:String
}