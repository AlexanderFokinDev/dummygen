package uy.amn.dummygen.presentation.controllers

import com.opencsv.bean.StatefulBeanToCsvBuilder
import com.opencsv.exceptions.CsvDataTypeMismatchException
import com.opencsv.exceptions.CsvRequiredFieldEmptyException
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import uy.amn.dummygen.domain.DummyTable
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException

@RestController
class DummyTableController {

    @GetMapping("/form")
    fun form(): ModelAndView {
        return ModelAndView("form")
    }

    @PostMapping("/submit-dummy-table")
    fun submit(
        @RequestParam rows: Int,
        @RequestParam columns: Int,
        @RequestParam email: String
    ): ResponseEntity<InputStreamResource> {

        val dummyTable = DummyTable(rows, columns, email)

        val file = File("dummy_table_${rows}_rows.csv")
        file.delete()

        try {
            val writer = FileWriter(file)
            val csvWriter = StatefulBeanToCsvBuilder<DummyTable>(writer).build()
            csvWriter.write(dummyTable)
            writer.close()
        } catch (e: CsvDataTypeMismatchException) {
            e.printStackTrace()
        } catch (e: CsvRequiredFieldEmptyException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val inputStream = FileInputStream(file)
        val inputStreamResource = InputStreamResource(inputStream)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(file.name).build()

        return ResponseEntity.ok().headers(headers).body(inputStreamResource)
    }
}