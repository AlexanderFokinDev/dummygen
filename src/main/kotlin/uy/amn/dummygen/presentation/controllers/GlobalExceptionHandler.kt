package uy.amn.dummygen.presentation.controllers

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        e: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse =
            ErrorResponse("Invalid Request",
                "What is happen: ${e.message}", request.requestURI)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    data class ErrorResponse(
        val errorMessage: String,
        val errorDetails: String,
        val requestUri: String
    )
}