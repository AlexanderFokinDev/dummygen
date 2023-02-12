package uy.amn.dummygen.presentation.controllers

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import uy.amn.dummygen.data.repositories.DummyDataRepositoryImpl
import uy.amn.dummygen.domain.usecases.*

@Controller
class HTMLController {

    @GetMapping("/")
    fun index(): ModelAndView {
        return ModelAndView("index")
    }

    @GetMapping("/data-generator")
    fun dataGenerator(): ModelAndView {
        return ModelAndView("data-generator")
    }

    @PostMapping("/submit-dummy-table")
    fun submit(
        @RequestParam settingsJson: String
    ): ResponseEntity<InputStreamResource> {

        // TODO: Change with DI
        val repository = DummyDataRepositoryImpl()

        return GetGeneratedFileUseCase(repository).execute(settingsJson).data
    }

}