package uy.amn.dummygen.domain.models

import com.google.gson.Gson

data class SettingsFromFile(val columns: List<ColumnSettings>, val rows: Int, val format: String) {

    companion object {
        fun fromJson(json: String): SettingsFromFile {
            return Gson().fromJson(json, SettingsFromFile::class.java)
        }
    }

}
