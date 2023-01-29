package uy.amn.dummygen.domain.repositories

import org.springframework.core.io.InputStreamResource
import uy.amn.dummygen.domain.models.GeneratedRow
import java.io.File

interface DummyDataRepository {

    fun getGeneratedList(rows: Int, columns: Int) : List<GeneratedRow>

    fun getGeneratedFileCSV(file: File, rows: Int, columns: Int) : InputStreamResource
    fun getGeneratedFileJSON(file: File, rows: Int, columns: Int) : InputStreamResource

}