package uy.amn.dummygen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummygenApplication

fun main(args: Array<String>) {
	runApplication<DummygenApplication>(*args)
}
