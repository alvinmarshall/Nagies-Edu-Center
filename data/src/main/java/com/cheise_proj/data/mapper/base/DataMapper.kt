package com.cheise_proj.data.mapper.base

interface DataMapper<D, E> {
    fun dataToEntity(d: D): E
    fun entityToData(e: E): D
}