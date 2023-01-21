package uy.amn.dummygen.presentation.controllers

import com.opencsv.bean.StatefulBeanToCsvBuilder
import com.opencsv.exceptions.CsvDataTypeMismatchException
import com.opencsv.exceptions.CsvRequiredFieldEmptyException
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import uy.amn.dummygen.domain.DummyTable
import java.io.FileWriter
import java.io.IOException

@RestController
class DummyTableController {

    @GetMapping("/form")
    fun form(): ModelAndView {
        return ModelAndView("form")
    }

    @PostMapping("/dummy-table")
    fun saveDummyTable(@RequestParam rows: Int, @RequestParam columns: Int, @RequestParam email: String): String {
        val dummyTable = DummyTable(rows, columns, email)
        try {
            val writer = FileWriter("dummy_table.csv")
            val beanToCsv = StatefulBeanToCsvBuilder<DummyTable>(writer).build()
            beanToCsv.write(dummyTable)
            writer.close()
        } catch (e: CsvDataTypeMismatchException) {
            e.printStackTrace()
        } catch (e: CsvRequiredFieldEmptyException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "Dummy Table saved successfully"
    }
}