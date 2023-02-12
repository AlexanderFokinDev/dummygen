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

        val dummyTable = getGeneratedList(rows, columns)
        val tableName = "dummy_table"
        val columnNames = columns.joinToString(", ") { it.name }
        val dataTypes = mapOf(
            "int" to "INT",
            "float" to "FLOAT",
            "boolean" to "BOOLEAN",
            "string" to "VARCHAR(255)",
            "date" to "DATE"
        )

        try {
            val writer = FileWriter(file)

            // Generate the CREATE TABLE statement
            writer.write("CREATE TABLE $tableName (\n")
            var primaryKey = ""
            for (column in columns) {
                val dataType = dataTypes[column.dataType]
                writer.write("  ${column.name} $dataType NOT NULL,\n")
                if (column.generator == "increment") {
                    primaryKey = column.name
                }
            }

            if (primaryKey.isNotEmpty()) {
                writer.write("  PRIMARY KEY ($primaryKey)\n")
            }
            writer.write(");\n\n")

            // Generate the INSERT INTO statements
            for ((indexRow, row) in dummyTable.withIndex()) {

                // Head line with column names
                if (indexRow == 0) continue

                writer.write("INSERT INTO $tableName ($columnNames) VALUES (")
                for ((index, column) in columns.withIndex()) {
                    val value = when (dataTypes[column.dataType]) {
                        "INT" -> row.fields[index].toInt()
                        "FLOAT" -> row.fields[index].toFloat()
                        "BOOLEAN" -> if (row.fields[index] == "true") "TRUE" else "FALSE"
                        "VARCHAR(255)" -> "'${row.fields[index]}'"
                        "DATE" -> "toDate('${row.fields[index]}')"
                        else -> error("Unknown data type")
                    }
                    writer.write("$value")
                    if (index < columns.count() - 1) writer.write(", ")
                }
                writer.write(");\n")
            }

            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val inputStream = FileInputStream(file)

        return InputStreamResource(inputStream)
    }

    override fun getGeneratedFileClickhouse(file: File, rows: Int, columns: List<ColumnSettings>): InputStreamResource {

        val dummyTable = getGeneratedList(rows, columns)
        val tableName = "dummy_table"
        val columnNames = columns.joinToString(", ") { it.name }
        val dataTypes = mapOf(
            "int" to "Int32",
            "float" to "Float32",
            "boolean" to "UInt8",
            "string" to "String",
            "date" to "Date"
        )

        try {
            val writer = FileWriter(file)

            // Generate the CREATE TABLE statement
            writer.write("CREATE TABLE $tableName (\n")
            var primaryKey = ""
            for (column in columns) {
                val dataType = dataTypes[column.dataType]
                writer.write("  ${column.name} $dataType")
                if (column.generator == "increment") {
                    writer.write(" DEFAULT generateUUIDv1()")
                    primaryKey = column.name
                }
                writer.write(",\n")
            }

            if (primaryKey.isNotEmpty()) {
                writer.write("  PRIMARY KEY ($primaryKey)\n")
            }
            writer.write(") ENGINE = MergeTree ORDER BY $primaryKey;\n\n")

            // Generate the INSERT INTO statements
            for ((indexRow, row) in dummyTable.withIndex()) {

                // Head line with column names
                if (indexRow == 0) continue

                writer.write("INSERT INTO $tableName ($columnNames) VALUES (")
                for ((index, column) in columns.withIndex()) {
                    val value = when (dataTypes[column.dataType]) {
                        "Int32" -> row.fields[index].toInt()
                        "Float32" -> row.fields[index].toFloat()
                        "UInt8" -> if (row.fields[index] == "true") "1" else "0"
                        "String" -> "'${row.fields[index]}'"
                        "Date" -> "toDate('${row.fields[index]}')"
                        else -> error("Unknown data type")
                    }
                    writer.write("$value")
                    if (index < columns.count() - 1) writer.write(", ")
                }
                writer.write(");\n")
            }

            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val inputStream = FileInputStream(file)

        return InputStreamResource(inputStream)
    }
}