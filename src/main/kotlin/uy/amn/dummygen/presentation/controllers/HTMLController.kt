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
import uy.amn.dummygen.domain.usecases.GetGeneratedFileCSVUseCase
import java.io.File

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
        @RequestParam email: String
    ): ResponseEntity<InputStreamResource> {

        // TODO: Change with DI
        val repository = DummyDataRepositoryImpl()
        val useCase = GetGeneratedFileCSVUseCase(repository)

        val file = File("dummy_table_${rows}_rows_${columns}_columns.csv")
        val inputStreamResource = useCase.execute(file, rows, columns).data

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(file.name).build()
        file.delete()

        return ResponseEntity.ok().headers(headers).body(inputStreamResource)
    }
}