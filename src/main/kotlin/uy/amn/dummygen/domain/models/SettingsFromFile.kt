package uy.amn.dummygen.domain.models

import com.google.gson.Gson

data class SettingsFromFile(
    val columns: List<ColumnSettings>,
    val rows: Int,
    val format: String,
    var fileExtension: String
) {

    companion object {
        fun fromJson(json: String): SettingsFromFile {
            val settingsFromFile = Gson().fromJson(json, SettingsFromFile::class.java)

            settingsFromFile.fileExtension = when (settingsFromFile.format) {
                "query_sql" -> "txt"
                "query_clickhouse" -> "txt"
                else -> settingsFromFile.format
            }

            return settingsFromFile
        }
    }

}
