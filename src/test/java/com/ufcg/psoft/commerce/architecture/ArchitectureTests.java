package com.ufcg.psoft.commerce.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(
        packages = "com.ufcg.psoft.commerce",
        importOptions = { ImportOption.DoNotIncludeTests.class }
)
public class ArchitectureTests {

    @ArchTest
    static final ArchRule controllersShouldNotAccessRepositories =
            noClasses()
                    .that().resideInAPackage("..controller..")
                    .should().accessClassesThat().resideInAPackage("..repository..");

    @ArchTest
    static final ArchRule serviceShouldNotAccessController =
            noClasses()
                    .that().resideInAPackage("..service..")
                    .should().accessClassesThat().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule repositoriesShouldNotAccessControllersOrServices =
            noClasses()
                    .that().resideInAPackage("..repository..")
                    .should().accessClassesThat()
                    .resideInAnyPackage("..controller..", "..service..");

    @ArchTest
    static final ArchRule serviceShouldOnlyBeAccessedByControllersBrOtherServices =
            classes().that().resideInAPackage("..service..")
                    .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

    @ArchTest
    static final ArchRule service_should_only_access_repository_or_services =
            classes().that().resideInAPackage("..service..")
                    .should().onlyAccessClassesThat()
                    .resideInAnyPackage("..service..", "..repository..")
                    .orShould().accessClassesThat().belongToAnyOf(
                            Object.class,
                            String.class,
                            Autowired.class,
                            ModelMapper.class
                    );

    @ArchTest
    static final ArchRule services_should_not_depend_on_controllers =
            noClasses().that().resideInAPackage("..service..")
                    .should().dependOnClassesThat().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule repository_should_not_depend_on_services =
            noClasses().that().resideInAPackage("..repository..")
                    .should().dependOnClassesThat().resideInAPackage("..service..");

    @ArchTest
    static final ArchRule services_should_only_be_depended_on_by_controllers_or_other_services =
            classes().that().resideInAPackage("..service..")
                    .should().onlyHaveDependentClassesThat().resideInAnyPackage("..controller..", "..service..");

    @ArchTest
    static final ArchRule services_should_only_depend_on_repository_or_other_services =
            classes().that().resideInAPackage("..service..")
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage("..service..", "..repository..")
                    .orShould().accessClassesThat().belongToAnyOf(
                            Object.class,
                            String.class,
                            Autowired.class,
                            ModelMapper.class
                    ).allowEmptyShould(true);

    @ArchTest
    final ArchRule controllersMustHaveAnnotation =
            classes()
                    .that().resideInAPackage("..controller..")
                    .should().beAnnotatedWith(RestController.class);

    @ArchTest
    final ArchRule servicesMustHaveAnnotation =
            classes()
                    .that().resideInAPackage("..service..")
                    .and().haveNameMatching(".*.ServiceImpl")
                    .should().beAnnotatedWith(Service.class);

    @ArchTest
    final ArchRule repositoriesMustHaveAnnotation =
            classes()
                    .that().resideInAPackage("..repository..")
                    .should().beAnnotatedWith(Repository.class);

    @ArchTest
    static final ArchRule dtos_should_be_in_dto_package =
            classes().that().haveSimpleNameEndingWith("DTO")
                    .or().haveSimpleNameEndingWith("Dto")
                    .should().resideInAPackage("..dto..");

    @ArchTest
    static final ArchRule model_classes_should_be_transactional =
            classes().that().resideInAPackage("..model..")
                    .and().areNotInterfaces()
                    .and().areNotAnnotations()
                    .and().areNotEnums()
                    .and().areNotMemberClasses()
                    .should().beAnnotatedWith(Transactional.class)
                    .allowEmptyShould(true);

    @ArchTest
    static final ArchRule model_classes_should_be_annotated_with_entity_or_embeddable =
            classes().that().resideInAPackage("..model..")
                    .and().areNotInterfaces()
                    .and().areNotAnnotations()
                    .and().areNotEnums()
                    .and().areNotMemberClasses()
                    .should().beAnnotatedWith(Entity.class)
                    .orShould().beAnnotatedWith(Embeddable.class)
                    .allowEmptyShould(true);
}
