package com.cheise_proj.domain.entity.message

interface IComplaint :IMessage{
    val refNo:String
    val studentName:String
    val level:String
    val contact:String
    val receiver:String

}