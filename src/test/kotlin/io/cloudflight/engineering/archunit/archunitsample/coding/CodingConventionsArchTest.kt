package io.cloudflight.engineering.archunit.archunitsample.coding

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.GeneralCodingRules.*

@AnalyzeClasses(
    packages = ["io.cloudflight.engineering.archunit"],
    importOptions = [ImportOption.DoNotIncludeTests::class]
)
class CodingConventionsArchTest {

    @ArchTest
    val classesShouldNotAccessStandardStreamsFromLibrary = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS

    @ArchTest
    val classesShouldNotThrowGenericExceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS

    @ArchTest
    val classesShouldNotUseJavaUtilLogging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING

    @ArchTest
    val classesShouldNotUseJodatime = NO_CLASSES_SHOULD_USE_JODATIME

    @ArchTest
    val classesShouldNotUseFieldInjection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION
}
