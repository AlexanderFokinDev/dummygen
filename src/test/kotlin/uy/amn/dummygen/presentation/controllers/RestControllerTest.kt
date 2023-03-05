package uy.amn.dummygen.presentation.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import uy.amn.dummygen.City
import uy.amn.dummygen.RestController

@SpringBootTest
class RestControllerTest {

    lateinit var mockMvc : MockMvc

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(RestController()).build()
    }

    @Test
    @WithMockUser
    fun `should return hello world message`() {
        val response = mockMvc.get("/api/v1/helloWorld") {
            accept = MediaType.TEXT_PLAIN
        }
        assertEquals("Hello World", response.andReturn().response.contentAsString)
    }

    @Test
    @WithMockUser
    fun `should return hello message with name`() {
        val name = "John"
        val response = mockMvc.get("/api/v1/helloWorld/$name") {
            accept = MediaType.APPLICATION_JSON
        }
        assertEquals(200, response.andReturn().response.status)
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.andReturn().response.contentType)
        assertEquals("{\"message\":\"Hello $name\",\"name\":\"$name\"}", response.andReturn().response.contentAsString)
    }

    @Test
    @WithMockUser
    fun `should return bad request if name is Chukcha`() {
        val name = "Chukcha"
        val response = mockMvc.get("/api/v1/helloWorld/$name") {
            accept = MediaType.TEXT_PLAIN
        }
        assertEquals(400, response.andReturn().response.status)
        assertEquals("text/plain;charset=ISO-8859-1", response.andReturn().response.contentType)
        assertEquals("You are Chukcha", response.andReturn().response.contentAsString)
    }

    @Test
    fun `should return list of cities in country`() {

        val objectMapper = ObjectMapper()

        // Define the expected response for each country
        val expectedResponseForUruguay = listOf(
            City(id = 1, name = "Montevideo", population = 18000000),
            City(id = 2, name = "Colonia", population = 100000),
            City(id = 3, name = "Punta del Este", population = 150000)
        )
        val expectedResponseForRussia = listOf(
            City(id = 1, name = "Moscow", population = 12000000),
            City(id = 2, name = "Saint-Petersburg", population = 5000000),
            City(id = 3, name = "Tula", population = 600000)
        )

        // Test the endpoint for Uruguay
        val uruguayResponse = mockMvc.get("/api/v1/countries/Uruguay") {
            accept = MediaType.APPLICATION_JSON
        }

        val uruguayResponseBody = uruguayResponse.andReturn().response.contentAsString
        val actualResponseForUruguay =
            objectMapper.readValue(uruguayResponseBody, object : TypeReference<List<City>>() {})
        assertEquals(expectedResponseForUruguay, actualResponseForUruguay)

       // Test the endpoint for Russia
        val russiaResponse = mockMvc.get("/api/v1/countries/Russia") {
            accept = MediaType.APPLICATION_JSON
        }

        val russiaResponseBody = russiaResponse.andReturn().response.contentAsString
        val actualResponseForRussia =
            objectMapper.readValue(russiaResponseBody, object : TypeReference<List<City>>() {})
        assertEquals(expectedResponseForRussia, actualResponseForRussia)

        // Test the endpoint for an unknown country
        val unknownCountryResponse = mockMvc.get("/api/v1/countries/Unknown") {
            accept = MediaType.APPLICATION_JSON
        }

        val unknownCountryResponseBody = unknownCountryResponse.andReturn().response.contentAsString
        val actualResponseForUnknownCountry =
            objectMapper.readValue(unknownCountryResponseBody, object : TypeReference<List<City>>() {})
        assertTrue(actualResponseForUnknownCountry.isEmpty())
    }

    @Test
    fun `test getListFromDB`() {

        val objectMapper = ObjectMapper()

        val expectedResponse = listOf(
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

        val dictionaryResponse = mockMvc.get("/api/v1/dictionaries/dictionary_name") {
            accept = MediaType.APPLICATION_JSON
        }

        val dictionaryResponseBody = dictionaryResponse.andReturn().response.contentAsString
        val actualResponse =
            objectMapper.readValue(dictionaryResponseBody, object : TypeReference<List<City>>() {})
        assertEquals(expectedResponse, actualResponse)
    }

}