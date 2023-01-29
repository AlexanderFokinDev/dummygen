package uy.amn.dummygen.domain.usecases

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import pt.amn.moveon.domain.models.UseCaseResult
import uy.amn.dummygen.domain.repositories.DummyDataRepository
import java.io.File
import java.util.*

class GetGeneratedFileUseCase(private val repository: DummyDataRepository) {

    fun execute(rows: Int, columns: Int, format: String): UseCaseResult<ResponseEntity<InputStreamResource>> {

        deleteTempFiles()

        val uid = UUID.randomUUID().toString().replace("-", "")
        val file = File("dummy_${uid}.${format}")
        val inputStreamResource = getInputStreamResource(format, file, rows, columns)

        val headers = HttpHeaders()
        headers.contentType = getContentType(format)
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(file.name).build()

        val useCaseResult = UseCaseResult(
            isError = false,
            data = ResponseEntity.ok().headers(headers).body(inputStreamResource),
            description = "List is generated"
        )

        return useCaseResult
    }

    private fun getContentType(format: String) =
        when (format) {
            "xml" -> {
                MediaType.APPLICATION_XML
            }

            "json" -> {
                MediaType.APPLICATION_JSON
            }

            "csv" -> {
                MediaType.APPLICATION_OCTET_STREAM
            }

            else -> {
                MediaType.APPLICATION_OCTET_STREAM
            }
        }

    private fun getInputStreamResource(format: String, file: File, rows: Int, columns: Int): InputStreamResource {

        return when (format) {
            "xml" -> {
                repository.getGeneratedFileXML(file, rows, columns)
            }

            "json" -> {
                repository.getGeneratedFileJSON(file, rows, columns)
            }

            "csv" -> {
                repository.getGeneratedFileCSV(file, rows, columns)
            }

            else -> {
                repository.getGeneratedFileCSV(file, rows, columns)
            }
        }
    }

    private fun deleteTempFiles() {

        val currentDir = File(".")
        val files = currentDir.listFiles()
        for (file in files!!) {
            if (file.name.startsWith("dummy_")
                && (file.name.endsWith(".csv")
                        || file.name.endsWith(".xml")
                        || file.name.endsWith(".json"
                ))
            ) {
                file.delete()
            }
        }

    }
}