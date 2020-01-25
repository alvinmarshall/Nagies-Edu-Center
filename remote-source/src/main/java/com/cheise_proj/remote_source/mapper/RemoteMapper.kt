package com.cheise_proj.remote_source.mapper

interface RemoteMapper<T, D> {
    fun dtoToData(t: T): D
    fun dataToDto(d: D): T
}