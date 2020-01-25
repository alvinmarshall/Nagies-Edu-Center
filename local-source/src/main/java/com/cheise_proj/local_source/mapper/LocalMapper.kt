package com.cheise_proj.local_source.mapper

interface LocalMapper<L, D> {
    fun localToData(l: L): D
    fun dataToLocal(d: D): L
}