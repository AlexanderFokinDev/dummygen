package uy.amn.dummygen

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/generator_v1")
class RestController {

    @RequestMapping(value = ["/helloWorld"], method = [(RequestMethod.GET)])
    fun getHelloWorldMessage(): ResponseEntity<String> =
        ResponseEntity.ok("Hello World")

    @RequestMapping(value = ["/helloWorld/{name}"], method = [(RequestMethod.GET)])
    fun getHelloWorldMessageWithName(@PathVariable("name") name: String): ResponseEntity<Any> =
        if (name != "Chukcha") {
            ResponseEntity.ok(HelloResponse(message = "Hello $name", name = name))
        } else {
            ResponseEntity.badRequest().body("You are Chukcha")
        }

}

data class HelloResponse(
    val message: String,
    val name: String
)