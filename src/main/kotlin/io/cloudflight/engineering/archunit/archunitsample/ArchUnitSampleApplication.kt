package io.cloudflight.engineering.archunit.archunitsample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ArchUnitSampleApplication

fun main(args: Array<String>) {
    runApplication<ArchUnitSampleApplication>(*args)
}