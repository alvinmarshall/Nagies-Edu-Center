package com.cheise_proj.presentation.mapper

internal interface PresentationMapper<P, E> {
    fun presentationToEntity(p: P): E
    fun entityToPresentation(e: E): P

}

internal interface PresentationListMapper<P, E> : PresentationMapper<P, E> {
    fun presentationToEntityList(pList: List<P>): List<E>
    fun entityToPresentationList(eList: List<E>): List<P>
}