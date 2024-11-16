package com.taskmanager.integration.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/cucumber",
    glue = "com.taskmanager.integration.cucumber"
)
public class RunCucumberTest {
}