package com.srj9

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class Srj9Application: SpringBootServletInitializer() {

	override fun configure(builder: SpringApplicationBuilder?): SpringApplicationBuilder {
		return super.configure(builder)
	}
}

fun main(args: Array<String>) {
	runApplication<Srj9Application>(*args)
}
