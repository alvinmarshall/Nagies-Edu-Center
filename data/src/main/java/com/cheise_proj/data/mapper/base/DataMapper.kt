package com.cheise_proj.data.mapper.base

interface DataMapper<D, E> {
    fun dataToEntity(d: D): E
    fun entityToData(e: E): D
    fun dataToEntityList(dList: List<D>): List<E>
    fun entityToDataList(eList: List<E>): List<D>
}