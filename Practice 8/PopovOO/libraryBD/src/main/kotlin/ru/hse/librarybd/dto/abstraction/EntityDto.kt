package ru.hse.librarybd.dto.abstraction

interface EntityDto<I> {
    fun getId(): I
}
