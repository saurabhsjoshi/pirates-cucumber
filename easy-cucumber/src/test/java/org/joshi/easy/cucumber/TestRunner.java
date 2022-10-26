package org.joshi.easy.cucumber;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import scs.comp5903.cucumber.EasyCucumber;
import scs.comp5903.cucumber.execution.tag.BaseFilteringTag;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Class that contains the tests that will execute the acceptance tests using easy-cucumber.
 * Note: These tests should be run from cucumber module directory as that directory contains the required application
 * JAR files.
 */
public class TestRunner {

    /**
     * Location of the cucumber files relative to the cucumber module directory.
     */
    private static final String FEATURE_SRC_DIR = "../easy-cucumber/src/test/resources/";

    /**
     * Generates arguments for parametrized list that invokes all cucumber scenarios using tags.
     *
     * @return stream of arguments consisting of feature filepath and a tag
     */
    static Stream<Arguments> getAllTags() {
        return Stream.of("SinglePlayer.feature",
                        "Sorceress.feature"
                )
                .flatMap(file -> EasyCucumber.build(Paths.get(FEATURE_SRC_DIR + file), CommonStepDefs.class)
                        .getScenarios()
                        .stream()
                        .flatMap(s -> s.getTags().stream())
                        .map(tag -> Arguments.of(Named.of(file, FEATURE_SRC_DIR + file), tag)));
    }

    @ParameterizedTest
    @MethodSource("getAllTags")
    public void runTest(String fileName, String tag) {
        CommonStepDefs stepDefs = new CommonStepDefs();
        try {
            // Setup tests
            stepDefs.setup(tag);
            EasyCucumber.build(Paths.get(fileName), stepDefs).executeByTag(BaseFilteringTag.tag(tag));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Could not execute test with tag '" + tag + "' in file '" + fileName + "'");
        } finally {
            // Teardown
            stepDefs.teardown();
        }
    }
}
