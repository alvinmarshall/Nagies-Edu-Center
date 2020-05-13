package com.cheise_proj.remote_source.mapper

internal interface RemoteMapper<T, D> {
    fun dtoToData(t: T): D
    fun dataToDto(d: D): T
}

internal interface RemoteListMapper<T, D> : RemoteMapper<T, D> {
    fun dtoToDataList(tList: List<T>): List<D>
    fun dataToDtoList(dList: List<D>): List<T>
}