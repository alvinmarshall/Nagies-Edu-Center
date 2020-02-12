package com.cheise_proj.domain.entity.people

interface IPeople {
    val id: Int
    val refNo: String
    val name: String
    val photo: String?
    val username: String
    val gender:String
    val contact:String?
}