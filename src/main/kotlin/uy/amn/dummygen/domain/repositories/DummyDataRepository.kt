package uy.amn.dummygen.domain.repositories

import org.springframework.core.io.InputStreamResource
import uy.amn.dummygen.domain.models.ColumnSettings
import uy.amn.dummygen.domain.models.GeneratedRow
import java.io.File

interface DummyDataRepository {

    fun getGeneratedList(rows: Int, columns: List<ColumnSettings>) : List<GeneratedRow>

    fun getGeneratedFileCSV(file: File, rows: Int, columns: List<ColumnSettings>) : InputStreamResource

    fun getGeneratedFileJSON(file: File, rows: Int, columns: List<ColumnSettings>) : InputStreamResource

    fun getGeneratedFileXML(file: File, rows: Int, columns: List<ColumnSettings>) : InputStreamResource

    fun getGeneratedFileSQL(file: File, rows: Int, columns: List<ColumnSettings>) : InputStreamResource

    fun getGeneratedFileClickhouse(file: File, rows: Int, columns: List<ColumnSettings>) : InputStreamResource

}