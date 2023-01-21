package uy.amn.dummygen.domain.models

import com.opencsv.bean.CsvBindAndSplitByName

data class GeneratedRow(
    @CsvBindAndSplitByName(column = "generated_list", elementType = String::class, writeDelimiter = ",")
    val fields: List<String>)