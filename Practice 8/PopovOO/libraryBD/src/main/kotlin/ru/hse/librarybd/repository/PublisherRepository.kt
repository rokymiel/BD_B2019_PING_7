package ru.hse.librarybd.repository

import org.springframework.data.repository.CrudRepository
import ru.hse.librarybd.dto.*

interface PublisherRepository :CrudRepository<Publisher, String>