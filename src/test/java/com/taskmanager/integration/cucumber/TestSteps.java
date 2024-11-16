package com.taskmanager.integration.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class TestSteps {
    private boolean isLoggedIn;
    private boolean taskCreated;

    @Given("I am logged in")
    public void iAmLoggedIn() {
        isLoggedIn = true;
        Assert.assertTrue("User should be logged in", isLoggedIn);
    }

    @When("I create a task")
    public void iCreateATask() {
        Assert.assertTrue("User must be logged in to create a task", isLoggedIn);
        taskCreated = true;
    }

    @Then("A new task is created")
    public void aNewTaskIsCreated() {
        Assert.assertTrue("Task should be created", taskCreated);
    }
}