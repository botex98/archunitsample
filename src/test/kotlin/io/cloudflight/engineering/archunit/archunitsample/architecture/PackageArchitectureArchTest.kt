package io.cloudflight.engineering.archunit.archunitsample.architecture

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes

@AnalyzeClasses(packages = ["io.cloudflight.engineering.archunit"], importOptions = [ImportOption.DoNotIncludeTests::class])
class PackageArchitectureArchTest {

    @ArchTest
    val entities_must_reside_in_entity_package = classes()
        .that().areAnnotatedWith("javax.persistence.Entity")
        .or().areAnnotatedWith("javax.persistence.MappedSuperclass")
        .should().resideInAPackage(Packages.ENTITY)
        .`as`("Entities should reside in a package ${Packages.ENTITY}")

    @ArchTest
    val repositories_must_reside_in_repository_package = classes()
        .that().areAssignableTo("org.springframework.data.jpa.repository.JpaRepository")
        .should().resideInAPackage(Packages.REPOSITORY)
        .andShould().haveSimpleNameEndingWith(Layers.REPOSITORY)
        .`as`("Repositories should reside in a package ${Packages.REPOSITORY}")

    @ArchTest
    val services_must_reside_in_service_package = classes()
        .that().areAnnotatedWith("org.springframework.stereotype.Service")
        .should().resideInAPackage(Packages.SERVICE)
        .andShould().haveSimpleNameEndingWith("${Layers.SERVICE}Impl")
        .`as`("Services should reside in a package ${Packages.SERVICE}")

    @ArchTest
    val controllers_must_reside_in_controller_package = classes()
        .that().areAnnotatedWith("org.springframework.web.bind.annotation.RestController")
        .should().resideInAnyPackage(Packages.CONTROLLER)
        .andShould().haveSimpleNameEndingWith(Layers.CONTROLLER)
        .`as`("Controllers should reside in a package ${Packages.CONTROLLER}")

    @ArchTest
    val apis_must_reside_in_api_package = classes()
        .that().areAnnotatedWith("io.swagger.annotations.Api")
        .or().areAnnotatedWith("org.springframework.web.bind.annotation.RequestMapping")
        .should().resideInAnyPackage(Packages.API)
        .andShould().haveSimpleNameEndingWith(Layers.API)
        .`as`("Controllers should reside in a package ${Packages.API}")

}
