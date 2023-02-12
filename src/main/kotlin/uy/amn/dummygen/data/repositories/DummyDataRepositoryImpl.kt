package uy.amn.dummygen.data.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.opencsv.bean.StatefulBeanToCsvBuilder
import org.springframework.core.io.InputStreamResource
import uy.amn.dummygen.domain.models.ColumnSettings
import uy.amn.dummygen.domain.models.GeneratedRow
import uy.amn.dummygen.domain.repositories.DummyDataRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DummyDataRepositoryImpl : DummyDataRepository {

    override fun getGeneratedList(rows: Int, columns: List<ColumnSettings>): List<GeneratedRow> {

        val dataList = mutableListOf<GeneratedRow>()

        dataList.add(getHeadRow(columns))

        for (i in 1..rows) {

            val fields = mutableListOf<String>()

            for (column in columns) {
                fields.add(
                    when (column.generator.lowercase().trim()) {
                        "increment" -> generateIncrementalField(i, column.params)
                        "random" -> generateRandomValue(column.params, column.dataType)
                        "email" -> generateRandomEmail()
                        "date" -> generateRandomDate()
                        "default" -> returnDefaultValue(column.params)
                        "range" -> generateRandomNumberFromRange(column.params)
                        else -> ""
                    }
                )
            }

            dataList.add(GeneratedRow(fields))
        }
        return dataList
    }

    private fun getHeadRow(columns: List<ColumnSettings>) : GeneratedRow {

        val fields = mutableListOf<String>()
        for (column in columns) {
            fields.add(column.name)
        }

        return GeneratedRow(fields)
    }

    private fun generateIncrementalField(counter: Int, params: Map<String, Any>) : String {

        val start = (params.getOrDefault("start", 1) as Double).toInt()
        val step = (params.getOrDefault("step", 1) as Double).toInt()

        return (start - 1 + counter * step).toString()
    }

    private fun generateRandomNumberFromRange(params: Map<String, Any>) : String {

        val min = (params.getOrDefault("min", 1) as Double).toInt()
        val max = (params.getOrDefault("max", 1) as Double).toInt()

        val randNumber = (min..max).shuffled().first()

        return randNumber.toString()
    }

    private fun generateRandomEmail(): String {

        val allowedChars = "0123456789abcdefghijklmnopqrstuvwxyz"
        val userNameLength = (6..10).random()
        val domainLength = (5..8).random()

        val userName = (1..userNameLength).map { allowedChars.random() }.joinToString("")
        val domain = (1..domainLength).map { allowedChars.random() }.joinToString("")

        return "$userName@$domain.com"
    }

    private fun returnDefaultValue(params: Map<String, Any>): String {

        return params.getOrDefault("value", "") as String
    }

    private fun generateRandomDate(): String {

        val endDate = LocalDate.now()
        val startDate = endDate.minusYears(2)
        val days = startDate.until(endDate).years * 365
        val randomDay = startDate.plusDays(Random().nextLong(days + 1L))
        return randomDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    }

    private fun generateRandomValue(params: Map<String, Any>, dataType: String) : String {

        if (dataType.lowercase().trim() == "string") {
            val length = (params.getOrDefault("length", 10) as Double).toInt()

            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")

        } else if (dataType.lowercase().trim() == "int") {
            return generateRandomNumberFromRange(params)
        } else {
            return ""
        }

    }

    override fun getGeneratedFileCSV(file: File, rows: Int, columns: List<ColumnSettings>): InputStreamResource {

        val dummyTable = getGeneratedList(rows, columns)

        try {
            val writer = FileWriter(file)
            val csvWriter = StatefulBeanToCsvBuilder<GeneratedRow>(writer).build()
            csvWriter.write(dummyTable)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val inputStream = FileInputStream(file)

        return InputStreamResource(inputStream)
    }

    override fun getGeneratedFileJSON(file: File, rows: Int, columns: List<ColumnSettings>): InputStreamResource {

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

    override fun getGeneratedFileXML(file: File, rows: Int, columns: List<ColumnSettings>): InputStreamResource {

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

    override fun getGeneratedFileSQL(file: File, rows: Int, columns: List<ColumnSettings>): InputStreamResource {

       /* val dummyTable = getGeneratedList(rows, columns)
        val tableName = "dummy_table"
        val columnNames = (1..columns).map { "column_$it" }
        val dataTypes = mapOf(
            "integer" to "INT",
            "float" to "FLOAT",
            "boolean" to "BOOLEAN",
            "string" to "VARCHAR(255)"
        )

        try {
            val writer = FileWriter(file)

            // Generate the CREATE TABLE statement
            writer.write("CREATE TABLE $tableName (\n")
            for (i in 0 until columns) {
                val columnName = columnNames[i]
                val dataType = dataTypes[dummyTable[0].dataTypes[i]]
                writer.write("  $columnName $dataType NOT NULL,\n")
            }
            writer.write("  PRIMARY KEY (${columnNames.joinToString(", ")})\n")
            writer.write(");\n\n")

            // Generate the INSERT INTO statements
            for (row in dummyTable) {
                writer.write("INSERT INTO $tableName (${columnNames.joinToString(", ")}) VALUES (")
                for (i in 0 until columns) {
                    val value = when (dataTypes[dummyTable[0].dataTypes[i]]) {
                        "INT" -> row.data[i].toInt()
                        "FLOAT" -> row.data[i].toFloat()
                        "BOOLEAN" -> if (row.data[i] == "true") "TRUE" else "FALSE"
                        "VARCHAR(255)" -> "'${row.data[i]}'"
                        else -> error("Unknown data type")
                    }
                    writer.write("$value")
                    if (i < columns - 1) writer.write(", ")
                }
                writer.write(");\n")
            }

            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }*/

        val inputStream = FileInputStream(file)

        return InputStreamResource(inputStream)
    }
}