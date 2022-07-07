package io.cloudflight.engineering.archunit.archunitsample.architecture

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.Architectures.layeredArchitecture


@AnalyzeClasses(packages = ["io.cloudflight"], importOptions = [DoNotIncludeTests::class])
class LayeredArchitectureArchTest {

    @ArchTest
    val layer_dependencies_are_respected: ArchRule = layeredArchitecture()
        .withOptionalLayers(true)
        .layer(Layers.API)
        .definedBy(Packages.API)
        .layer(Layers.CONTROLLER)
        .definedBy(Packages.CONTROLLERS)
        .layer(Layers.SERVICE)
        .definedBy(Packages.SERVICE)
        .layer(Layers.DOMAIN)
        .definedBy(Packages.DOMAIN)
        .layer(Layers.ENTITY)
        .definedBy(Packages.ENTITY)
        .layer(Layers.REPOSITORY)
        .definedBy(Packages.REPOSITORY)

        .whereLayer(Layers.API)
        .mayOnlyBeAccessedByLayers(Layers.SERVICE)
        .whereLayer(Layers.CONTROLLER)
        .mayNotBeAccessedByAnyLayer()
        .whereLayer(Layers.SERVICE)
        .mayOnlyBeAccessedByLayers(Layers.CONTROLLER)
        .whereLayer(Layers.ENTITY)
        .mayOnlyBeAccessedByLayers(Layers.SERVICE, Layers.REPOSITORY)
        .whereLayer(Layers.REPOSITORY)
        .mayOnlyBeAccessedByLayers(Layers.SERVICE)
        .`as`("Package structuring does not match the expected one.")
}

