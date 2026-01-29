package com.of.ll.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.of.ll", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule domain_should_not_depend_on_adapters =
            noClasses().that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAPackage("..adapter..");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_application =
            noClasses().that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAPackage("..application..");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_config =
            noClasses().that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAPackage("..config..");

    @ArchTest
    static final ArchRule domain_should_not_depend_on_spring =
            noClasses().that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAPackage("org.springframework..");

    @ArchTest
    static final ArchRule application_should_not_depend_on_adapters =
            noClasses().that().resideInAPackage("..application..")
                    .should().dependOnClassesThat().resideInAPackage("..adapter..");

    @ArchTest
    static final ArchRule application_should_not_depend_on_config =
            noClasses().that().resideInAPackage("..application..")
                    .should().dependOnClassesThat().resideInAPackage("..config..");

    @ArchTest
    static final ArchRule ports_should_not_depend_on_adapters =
            noClasses().that().resideInAPackage("..port..")
                    .should().dependOnClassesThat().resideInAPackage("..adapter..");

    @ArchTest
    static final ArchRule ports_should_not_depend_on_application =
            noClasses().that().resideInAPackage("..port..")
                    .should().dependOnClassesThat().resideInAPackage("..application..");

    @ArchTest
    static final ArchRule ports_should_not_depend_on_config =
            noClasses().that().resideInAPackage("..port..")
                    .should().dependOnClassesThat().resideInAPackage("..config..");

    private HexagonalArchitectureTest() {
    }
}
