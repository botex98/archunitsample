package io.cloudflight.engineering.archunit.archunitsample.spring

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.springframework.web.bind.annotation.RequestMapping

@AnalyzeClasses(
    packages = ["io.cloudflight.engineering.archunit"],
    importOptions = [ImportOption.DoNotIncludeTests::class]
)
class SpringWebArchTest {

    @ArchTest
    val no_interface_should_do_request_mapping: ArchRule = noClasses()
        .that().areInterfaces()
        .should().beAnnotatedWith(RequestMapping::class.java)
        .`as`("Implementations only should define RequestMappings.")
}
