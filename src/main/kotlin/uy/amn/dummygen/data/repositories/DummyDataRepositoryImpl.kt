package uy.amn.dummygen.data.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
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

            val randNumber = (1..100).shuffled().first()
            fields.add("id_$randNumber")

            for (j in 1..columns) {
                fields.add("Field $j")
            }
            dataList.add(GeneratedRow(fields))
        }
        return dataList
    }

    override fun getGeneratedFileCSV(file: File, rows: Int, columns: Int): InputStreamResource {

        val dummyTable = getGeneratedList(rows, columns)

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

    override fun getGeneratedFileJSON(file: File, rows: Int, columns: Int): InputStreamResource {

        val dummyTable = getGeneratedList(rows, columns)

        val mapper = ObjectMapper()
        val jsonString = mapper.writeValueAsString(dummyTable)

        try {
            val writer = FileWriter(file)
            writer.write(jsonString)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val inputStream = FileInputStream(file)

        return InputStreamResource(inputStream)
    }

    override fun getGeneratedFileXML(file: File, rows: Int, columns: Int): InputStreamResource {

        val dummyTable = getGeneratedList(rows, columns)

        val mapper = XmlMapper()
        val xmlString = mapper.writeValueAsString(dummyTable)

        try {
            val writer = FileWriter(file)
            writer.write(xmlString)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val inputStream = FileInputStream(file)

        return InputStreamResource(inputStream)
    }
}