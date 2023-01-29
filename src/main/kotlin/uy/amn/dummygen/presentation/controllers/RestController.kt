package uy.amn.dummygen

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
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

    /**
     * This function maps a GET request to the "/dictionaries/{dictionary}" URL path. It takes a single parameter,
     * "dictionary", obtained from the path variable of the same name.
     * It can be a name of dictionary, like `countries`, 'users', etc.
     * The function returns a list of objects with the OK status code as a ResponseEntity object.
     */
    @RequestMapping(value = ["/dictionaries/{dictionary}"], method = [(RequestMethod.GET)])
    fun getListFromDB(@PathVariable("dictionary") dictionary: String): ResponseEntity<List<City>> =
        ResponseEntity.ok(
            listOf(
                City(1, "New York", 8175133),
                City(2, "Los Angeles", 3792621),
                City(3, "Chicago", 2695598),
                City(4, "Houston", 2100975),
                City(5, "Phoenix", 1513367),
                City(6, "Philadelphia", 1526006),
                City(7, "San Antonio", 1469845),
                City(8, "San Diego", 1355896),
                City(9, "Dallas", 1257676),
                City(10, "San Jose", 998537),
                City(11, "Austin", 947292),
                City(12, "Jacksonville", 868313),
                City(13, "San Francisco", 864816),
                City(14, "Columbus", 835925),
                City(15, "Fort Worth", 833319),
                City(16, "Charlotte", 827097),
                City(17, "Indianapolis", 820445),
                City(18, "Seattle", 744955),
                City(19, "Denver", 704636),
                City(20, "Washington", 681170),
                City(21, "Boston", 669584),
                City(22, "Nashville", 664577),
                City(23, "El Paso", 649121),
                City(24, "Detroit", 648826),
                City(25, "Memphis", 646189),
                City(26, "Portland", 639863),
                City(27, "Oklahoma City", 638367),
                City(28, "Las Vegas", 648224),
                City(29, "Louisville", 615266),
                City(30, "Baltimore", 611648),
                City(31, "Milwaukee", 599164),
                City(32, "Albuquerque", 559121),
                City(33, "Tucson", 526116),
                City(34, "Fresno", 524662),
                City(35, "Sacramento", 485199),
                City(36, "Long Beach", 469428),
                City(37, "Kansas City", 467007),
                City(38, "Mesa", 464704),
                City(39, "Atlanta", 456002),
                City(40, "Omaha", 446599),
                City(41, "Raleigh", 431746),
                City(42, "Miami", 417650),
                City(43, "Minneapolis", 407207),
                City(44, "Tulsa", 393041),
                City(45, "New Orleans", 390103),
                City(46, "Wichita", 386552),
                City(47, "Cleveland", 385809),
                City(48, "Tampa", 377165),
                City(49, "Santa Ana", 365647),
                City(50, "St. Louis", 318410),
                City(51, "Pittsburgh", 305841),
                City(52, "Cincinnati", 299184),
                City(53, "Anaheim", 289848),
                City(54, "Montevideo", 1800000),
                City(55, "Toledo", 282313),
                City(56, "Tampa", 281404),
                City(57, "Aurora", 277579),
                City(58, "Santa Rosa", 277501),
                City(59, "Riverside", 274097),
                City(60, "Corpus Christi", 273045),
                City(61, "Lexington", 272621),
                City(62, "Anchorage", 271827),
                City(63, "Stockton", 270742),
                City(64, "Tulsa", 269943),
                City(65, "Newark", 268417),
                City(66, "Toledo", 267941),
                City(67, "Greensboro", 267922),
                City(68, "Plano", 267593),
                City(69, "Henderson", 267011),
                City(70, "Lincoln", 265404),
                City(71, "Buffalo", 256404),
                City(72, "Fort Wayne", 255598),
                City(73, "Jersey City", 255199),
                City(74, "St. Paul", 254345),
                City(75, "Chula Vista", 254075),
                City(76, "Norfolk", 254012),
                City(77, "Orlando", 253073),
                City(78, "Chandler", 252874),
                City(79, "Laredo", 252049),
                City(80, "Madison", 251821),
                City(81, "Winston-Salem", 251620),
                City(82, "Lubbock", 251540),
                City(83, "Baton Rouge", 251370),
                City(84, "Durham", 250532),
                City(85, "Garland", 250440),
                City(86, "Glendale", 250410),
                City(87, "Reno", 250280),
                City(88, "Hialeah", 250140),
                City(89, "Chesapeake", 250100),
                City(90, "Scottsdale", 250070),
                City(91, "North Las Vegas", 250040),
                City(92, "Arlington", 250010),
                City(93, "Gilbert", 250000),
                City(94, "Raleigh", 249990),
                City(95, "Irving", 249980),
                City(96, "Fremont", 249970),
                City(97, "Irvine", 249960),
                City(98, "Birmingham", 249950),
                City(99, "Rochester", 249940),
                City(100, "San Bernardino", 249930)
            )
        )

}

data class HelloResponse(
    val message: String,
    val name: String
)

data class City(val id: Int, val name: String, val population: Int)