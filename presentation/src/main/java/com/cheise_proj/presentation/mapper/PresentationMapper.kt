package com.cheise_proj.presentation.mapper

interface PresentationMapper<P, E> {
    fun presentationToEntity(p: P): E
    fun entityToPresentation(e: E): P
}