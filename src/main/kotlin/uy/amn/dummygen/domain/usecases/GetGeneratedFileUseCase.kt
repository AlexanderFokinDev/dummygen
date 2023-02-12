package uy.amn.dummygen.domain.usecases

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import pt.amn.moveon.domain.models.UseCaseResult
import uy.amn.dummygen.domain.models.SettingsFromFile
import uy.amn.dummygen.domain.repositories.DummyDataRepository
import java.io.File
import java.util.*

class GetGeneratedFileUseCase(private val repository: DummyDataRepository) {

    fun execute(settingsJson: String): UseCaseResult<ResponseEntity<InputStreamResource>> {

        deleteTempFiles()

        val settings = SettingsFromFile.fromJson(settingsJson)

        val uid = UUID.randomUUID().toString().replace("-", "")
        val file = File("dummy_${uid}.${settings.fileExtension}")

        // Logic
        val inputStreamResource = getInputStreamResource(file, settings)
        //

        val headers = HttpHeaders()
        headers.contentType = getContentType(settings.fileExtension)
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

            else -> {
                MediaType.APPLICATION_OCTET_STREAM
            }
        }

    private fun getInputStreamResource(
        file: File,
        settings: SettingsFromFile
    ): InputStreamResource {

        return when (settings.format) {

            "xml" -> {
                repository.getGeneratedFileXML(file, settings.rows, settings.columns)
            }

            "json" -> {
                repository.getGeneratedFileJSON(file, settings.rows, settings.columns)
            }

            "csv" -> {
                repository.getGeneratedFileCSV(file, settings.rows, settings.columns)
            }

            "query_clickhouse" -> {
                repository.getGeneratedFileClickhouse(file, settings.rows, settings.columns)
            }

            "query_sql" -> {
                repository.getGeneratedFileSQL(file, settings.rows, settings.columns)
            }

            else -> {
                repository.getGeneratedFileCSV(file, settings.rows, settings.columns)
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
                        || file.name.endsWith(".json")
                        || file.name.endsWith(".txt")
                        )
            ) {
                file.delete()
            }
        }

    }
}