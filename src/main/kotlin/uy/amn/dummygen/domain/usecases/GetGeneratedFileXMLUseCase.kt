package uy.amn.dummygen.domain.usecases

import org.springframework.core.io.InputStreamResource
import pt.amn.moveon.domain.models.UseCaseResult
import uy.amn.dummygen.domain.models.GeneratedRow
import uy.amn.dummygen.domain.repositories.DummyDataRepository
import java.io.File

class GetGeneratedFileXMLUseCase(private val repository: DummyDataRepository) {

    fun execute(file: File, rows: Int, columns: Int): UseCaseResult<InputStreamResource> {

        return UseCaseResult(
            isError = false,
            data = repository.getGeneratedFileXML(file, rows, columns),
            description = "List is generated"
        )

    }

}