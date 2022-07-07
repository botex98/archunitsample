package io.cloudflight.engineering.archunit.archunitsample.api

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.domain.JavaMethod
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.lang.*
import com.tngtech.archunit.lang.conditions.ArchConditions
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import io.cloudflight.engineering.archunit.archunitsample.architecture.Packages
import io.swagger.annotations.Api
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import java.util.regex.Pattern
import java.util.stream.Collectors

@AnalyzeClasses(packages = ["io.cloudflight"], importOptions = [ImportOption.DoNotIncludeTests::class])
class SwaggerApiArchTest {

    @Test
    fun testApiPostAndPutMethodsDefineConsumesMediaType() {
        val importedClasses: JavaClasses = ClassFileImporter()
            .withImportOption { it!!.matches(Pattern.compile(".*Api.class")) }
            .importPackages("io.cloudflight")

        val apiClasses: ClassesTransformer<JavaClass> =
            object : AbstractClassesTransformer<JavaClass>("classes being annotated with @Api") {
                override fun doTransform(classes: JavaClasses): Iterable<JavaClass> {
                    return classes.stream()
                        .filter { it.isInterface }
                        .collect(Collectors.toSet())
                }
            }

        ArchRuleDefinition
            .all(apiClasses)
            .should(ArchConditions.beAnnotatedWith(Api::class.java))
            .because("we want the swagger generator to generate frontend DTOs and services for us")
            .check(importedClasses)

        ArchRuleDefinition.methods()
            .that()
            .areAnnotatedWith(PostMapping::class.java)
            .or()
            .areAnnotatedWith(PutMapping::class.java)
            .should(defineConsumesArgument())
            .because("request header Content-Type must be set so that generated frontend services work as expected")
            .check(importedClasses)
    }

    private fun defineConsumesArgument(): ArchCondition<in JavaMethod> {
        return object : ArchCondition<JavaMethod>("define the 'consumes' argument") {
            override fun check(method: JavaMethod, events: ConditionEvents) {
                val requestPartOrBody = listOf(RequestPart::class.java, RequestBody::class.java)
                val containsRequestPartOrBody = method.reflect().parameterAnnotations
                    .flatMap { it.map { kclass -> kclass.annotationClass.java } }
                    .any { requestPartOrBody.contains(it) }
                try {
                    val postMappingAnnotation = method.getAnnotationOfType(PostMapping::class.java)
                    if (containsRequestPartOrBody && postMappingAnnotation.consumes.isEmpty()) {
                        val message: String = getMessage(method, PostMapping::class.java.simpleName)
                        events.add(SimpleConditionEvent(method, false, message))
                    }
                } catch (ignored: Exception) {
                    val putMappingAnnotation = method.getAnnotationOfType(PutMapping::class.java)
                    if (containsRequestPartOrBody && putMappingAnnotation.consumes.isEmpty()) {
                        val message: String = getMessage(method, PutMapping::class.java.simpleName)
                        events.add(SimpleConditionEvent(method, false, message))
                    }
                }
            }

            private fun getMessage(method: JavaMethod, annotationName: String): String {
                return "${method.fullName} is annotated with $annotationName and must define the 'consumes' argument."
            }
        }
    }
}
