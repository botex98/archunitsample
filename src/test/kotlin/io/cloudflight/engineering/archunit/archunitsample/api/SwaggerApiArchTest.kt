package io.cloudflight.engineering.archunit.archunitsample.api

import com.tngtech.archunit.core.domain.JavaMethod
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart

@AnalyzeClasses(
    packages = ["io.cloudflight.engineering.archunit"], importOptions = [ImportOption.DoNotIncludeTests::class]
)
class SwaggerApiArchTest {

    @ArchTest
    val api_classes_should_be_annotated_with_swagger: ArchRule =
        classes().that().resideInAPackage("..api")
            .should().beAnnotatedWith("io.swagger.annotations.Api")
            .`as`("we want the swagger generator to generate frontend DTOs and services for us")

    @ArchTest
    val post_or_put_methods_should_define_consumes_argument: ArchRule =
        methods().that().areAnnotatedWith("org.springframework.web.bind.annotation.PostMapping").or()
            .areAnnotatedWith("org.springframework.web.bind.annotation.PutMapping")
            .should(defineConsumesArgument())
            .`as`("request header Content-Type must be set so that generated frontend services work as expected")

    private fun defineConsumesArgument(): ArchCondition<in JavaMethod> {
        return object : ArchCondition<JavaMethod>("define the 'consumes' argument") {
            override fun check(method: JavaMethod, events: ConditionEvents) {
                val postMapping = method.tryGetAnnotationOfType(PostMapping::class.java)
                val putMapping = method.tryGetAnnotationOfType(PutMapping::class.java)

                val hasParameterAnnotationPartOrBody = method.parameterAnnotations[0]
                    .map { it.type.name }
                    .any {
                        it == RequestBody::class.java.canonicalName || it == RequestPart::class.java.canonicalName
                    }

                if (hasParameterAnnotationPartOrBody && postMapping.isPresent && postMapping.get().consumes.isEmpty()) {
                    val message: String = getMessage(method, PostMapping::class.java.simpleName)
                    events.add(SimpleConditionEvent(method, false, message))
                }

                if (hasParameterAnnotationPartOrBody && putMapping.isPresent && putMapping.get().consumes.isEmpty()) {
                    val message: String = getMessage(method, PostMapping::class.java.simpleName)
                    events.add(SimpleConditionEvent(method, false, message))
                }
            }

            private fun getMessage(method: JavaMethod, annotationName: String): String {
                return "${method.fullName} is annotated with $annotationName and must define the 'consumes' argument."
            }
        }
    }
}
