package org.joshi.easy.cucumber;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import scs.comp5903.cucumber.EasyCucumber;
import scs.comp5903.cucumber.execution.JFeature;
import scs.comp5903.cucumber.execution.tag.BaseFilteringTag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestRunner {

    @Test
    public void singlePlayerTest() throws IOException,
            InvocationTargetException, IllegalAccessException {
        Path myFeatureFile = Paths.get(
                "../easy-cucumber/src" +
                        "/test/resources/SinglePlayer.feature");

        var tags = List.of(
                "R37"
        );

        for (var tag : tags) {
            System.out.println("TAG " + tag);
            var stepDefs = new CommonStepDefs();
            stepDefs.setup(tag);

            try {
                var jFeature = EasyCucumber.build(myFeatureFile, stepDefs);
                jFeature.executeByTag(BaseFilteringTag.tag(tag));
            } catch (Exception ex) {
                ex.printStackTrace();
                fail("Could not execute test with tag " + tag);
            } finally {
                stepDefs.teardown();
            }
        }

    }
}
