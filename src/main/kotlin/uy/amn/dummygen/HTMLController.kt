package uy.amn.dummygen

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class HTMLController {

    @GetMapping("/")
    fun generator(model: Model): String {
        model["title"] = "Generator"
        return "generator"
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