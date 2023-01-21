package uy.amn.dummygen.data.repositories

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBeanFilter
import com.opencsv.bean.StatefulBeanToCsvBuilder
import com.opencsv.exceptions.CsvDataTypeMismatchException
import com.opencsv.exceptions.CsvRequiredFieldEmptyException
import org.springframework.core.io.InputStreamResource
import uy.amn.dummygen.domain.models.GeneratedRow
import uy.amn.dummygen.domain.repositories.DummyDataRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException

class DummyDataRepositoryImpl : DummyDataRepository {

    override fun getGeneratedList(rows: Int, columns: Int): List<GeneratedRow> {

        val dataList = mutableListOf<GeneratedRow>()
        for (i in 1..rows) {
            val fields = mutableListOf<String>()
            for (j in 1..columns) {
                fields.add("Field $j")
            }
            dataList.add(GeneratedRow(fields))
        }
        return dataList
    }

    override fun getGeneratedFileCSV(file: File, rows: Int, columns: Int): InputStreamResource {

        val dummyTable = getGeneratedList(rows, columns)

        file.delete()

        try {
            val writer = FileWriter(file)
            val csvWriter = StatefulBeanToCsvBuilder<GeneratedRow>(writer).build()
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

        return InputStreamResource(inputStream)
    }
}