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

    @RequestMapping(value = ["/countries/{country}"], method = [(RequestMethod.GET)])
    fun getListCitiesInCountry(@PathVariable("country") country: String): ResponseEntity<List<City>> =
        ResponseEntity.ok(
            when (country.lowercase().trim()) {
                "uruguay" -> listOf(
                    City(id = 1, name = "Montevideo", population = 18000000),
                    City(id = 2, name = "Colonia", population = 100000),
                    City(id = 3, name = "Punta del Este", population = 150000)
                )
                "russia" -> listOf(
                    City(id = 1, name = "Moscow", population = 12000000),
                    City(id = 2, name = "Saint-Petersburg", population = 5000000),
                    City(id = 3, name = "Tula", population = 600000)
                )

                else -> listOf()
            }
        )

}

data class HelloResponse(
    val message: String,
    val name: String
)

data class City(val id: Int, val name: String, val population: Int)