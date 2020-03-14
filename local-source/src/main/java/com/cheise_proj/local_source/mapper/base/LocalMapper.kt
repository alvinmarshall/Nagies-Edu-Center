package com.cheise_proj.local_source.mapper.base

interface LocalMapper<L, D> {
    fun localToData(l: L): D
    fun dataToLocal(d: D): L
    fun localToDataList(lList: List<L>): List<D>
    fun dataToLocalList(dList: List<D>): List<L>
}