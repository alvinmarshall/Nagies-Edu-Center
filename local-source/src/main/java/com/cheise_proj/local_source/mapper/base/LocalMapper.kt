package com.cheise_proj.local_source.mapper.base

internal interface LocalMapper<L, D> {
    fun localToData(l: L): D
    fun dataToLocal(d: D): L
}

internal interface LocalListMapper<L, D> : LocalMapper<L, D> {
    fun localToDataList(lList: List<L>): List<D>
    fun dataToLocalList(dList: List<D>): List<L>
}