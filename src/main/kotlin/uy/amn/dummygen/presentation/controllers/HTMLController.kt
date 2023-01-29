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
import uy.amn.dummygen.domain.usecases.GetGeneratedFileCSVUseCase
import uy.amn.dummygen.domain.usecases.GetGeneratedFileJSONUseCase
import uy.amn.dummygen.domain.usecases.GetGeneratedFileXMLUseCase
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
        @RequestParam formatSelect: String,
        @RequestParam email: String
    ): ResponseEntity<InputStreamResource> {

        // TODO: Change with DI
        val repository = DummyDataRepositoryImpl()

        return when (formatSelect) {
            "xml" -> {
                getXMLFile(repository, rows, columns)
            }
            "json" -> {
                getJSONFile(repository, rows, columns)
            }
            "csv" -> {
                getCSVFile(repository, rows, columns)
            }
            else -> {
                getCSVFile(repository, rows, columns)
            }
        }

    }

    fun getCSVFile(repository: DummyDataRepository, rows: Int, columns: Int) : ResponseEntity<InputStreamResource> {

        val useCase = GetGeneratedFileCSVUseCase(repository)

        val file = File("dummy_table_${rows}_rows_${columns}_columns.csv")
        val inputStreamResource = useCase.execute(file, rows, columns).data

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(file.name).build()

        return ResponseEntity.ok().headers(headers).body(inputStreamResource)
    }

    fun getJSONFile(repository: DummyDataRepository, rows: Int, columns: Int) : ResponseEntity<InputStreamResource> {

        val useCase = GetGeneratedFileJSONUseCase(repository)

        val file = File("dummy_table_${rows}_rows_${columns}_columns.json")
        val inputStreamResource = useCase.execute(file, rows, columns).data

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(file.name).build()

        return ResponseEntity.ok().headers(headers).body(inputStreamResource)
    }

    fun getXMLFile(repository: DummyDataRepository, rows: Int, columns: Int) : ResponseEntity<InputStreamResource> {

        val useCase = GetGeneratedFileXMLUseCase(repository)

        val file = File("dummy_table_${rows}_rows_${columns}_columns.xml")
        val inputStreamResource = useCase.execute(file, rows, columns).data

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_XML
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(file.name).build()

        return ResponseEntity.ok().headers(headers).body(inputStreamResource)
    }
}