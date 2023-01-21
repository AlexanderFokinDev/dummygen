package uy.amn.dummygen.presentation.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HTMLController {

    @GetMapping("/")
    fun index(): ModelAndView {
        return ModelAndView("index")
    }

    // Randomize button handler
    // Generate and return a random number between 1-100
    @PostMapping("/randomize")
    fun randomize(): String {
        val randNumber = (1..100).shuffled().first()

        return randNumber.toString()  // Return the generated number as a string to be displayed on the page
    }

    // Clear All button handler
    // Clear all input fields in the form
    @PostMapping("/clearall")
    fun clearAll(): String {
        return ""
    }
}