package ru.hse.librarybd.service.abstraction

import org.springframework.data.repository.CrudRepository
import ru.hse.librarybd.dto.abstraction.EntityDto
import javax.persistence.EntityNotFoundException

abstract class EntityService<E : Any, D : EntityDto<I>, I : Any>(
    private val entityRepository: CrudRepository<E, I>,
    private val entityName: String
) {
    fun findAll(): Iterable<E> = entityRepository.findAll()

    fun find(id: I): E = entityRepository.findById(id).orElseThrow {
        throwNotFoundException(id)
    }

    fun save(dto: D) =
        entityRepository.save(dto.toEntity())

    fun put(dto: D): E {
        if (!entityRepository.existsById(dto.getId()))
            throwNotFoundException(dto.getId())
        return entityRepository.save(dto.toEntity())
    }

    fun delete(id: I) = entityRepository.deleteById(id)

    abstract fun D.toEntity(): E

    fun throwNotFoundException(id: Any, name: String = entityName): Nothing =
        throw EntityNotFoundException("$name with id=$id does not exist")
}
