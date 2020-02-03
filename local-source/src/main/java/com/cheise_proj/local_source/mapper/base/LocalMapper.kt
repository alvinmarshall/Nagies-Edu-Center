package com.cheise_proj.local_source.mapper.base

interface LocalMapper<L, D> {
    fun localToData(l: L): D
    fun dataToLocal(d: D): L
}