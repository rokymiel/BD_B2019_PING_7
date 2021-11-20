package ru.hse.librarybd.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.persistence.EntityNotFoundException


@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException) =
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException) =
        ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
}