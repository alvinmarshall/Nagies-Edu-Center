package com.cheise_proj.remote_source.mapper

interface RemoteMapper<T, D> {
    fun dtoToData(t: T): D
    fun dataToDto(d: D): T
    fun dtoToDataList(tList: List<T>): List<D>
    fun dataToDtoList(dList: List<D>): List<T>
}