package uy.amn.dummygen.domain.usecases

import pt.amn.moveon.domain.models.UseCaseResult
import uy.amn.dummygen.domain.models.GeneratedRow
import uy.amn.dummygen.domain.repositories.DummyDataRepository

class GetGeneratedListUseCase(private val repository: DummyDataRepository) {

    fun execute(rows: Int, columns: Int): UseCaseResult<List<GeneratedRow>> {

        return UseCaseResult(
            isError = false,
            data = repository.getGeneratedList(rows, columns),
            description = "List is generated"
        )

    }

}