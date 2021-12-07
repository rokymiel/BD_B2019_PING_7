package ru.hse.librarybd.repository

import org.springframework.data.repository.CrudRepository
import ru.hse.librarybd.dto.Reader

interface ReaderRepository : CrudRepository<Reader, Long>
