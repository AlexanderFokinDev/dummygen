package uy.amn.dummygen.presentation.controllers

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import uy.amn.dummygen.data.repositories.DummyDataRepositoryImpl
import uy.amn.dummygen.domain.repositories.DummyDataRepository
import uy.amn.dummygen.domain.usecases.*
import java.io.File
import java.util.*

@Controller
class HTMLController {

    @GetMapping("/")
    fun index(): ModelAndView {
        return ModelAndView("index")
    }

    @GetMapping("/data-generator")
    fun dataGenerator(): ModelAndView {
        return ModelAndView("data-generator")
    }

    @PostMapping("/submit-dummy-table")
    fun submit(
        @RequestParam rows: Int,
        @RequestParam columns: Int,
        @RequestParam formatSelect: String,
        @RequestParam email: String
    ): ResponseEntity<InputStreamResource> {

        // TODO: Change with DI
        val repository = DummyDataRepositoryImpl()

        return GetGeneratedFileUseCase(repository).execute(rows, columns, formatSelect).data
    }

}