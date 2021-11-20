package ru.hse.librarybd.contoller.abstraction

import org.springframework.web.bind.annotation.*
import ru.hse.librarybd.dto.abstraction.EntityDto
import ru.hse.librarybd.service.abstraction.EntityService

abstract class EntityController<E : Any, D : EntityDto<I>, I : Any>(
    private val entityService: EntityService<E, D, I>
) {
    @GetMapping
    fun getAll() = entityService.findAll()

    @GetMapping("{id}")
    fun get(@PathVariable id: I) = entityService.find(id)

    @PostMapping
    fun create(@RequestBody dto: D) = entityService.save(dto)

    @PutMapping
    fun update(@RequestBody dto: D) = entityService.put(dto)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: I) = entityService.delete(id)
}
