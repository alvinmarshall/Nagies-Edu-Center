package com.cheise_proj.presentation.mapper

interface PresentationMapper<P, E> {
    fun presentationToEntity(p: P): E
    fun entityToPresentation(e: E): P
    fun presentationToEntityList(pList: List<P>): List<E>
    fun entityToPresentationList(eList: List<E>): List<P>
}