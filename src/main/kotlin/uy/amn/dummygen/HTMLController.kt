package uy.amn.dummygen

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HTMLController {

    @GetMapping("/")
    fun generator(model: Model) : String {
        model["title"] = "Generator"
        return "generator"
    }
}