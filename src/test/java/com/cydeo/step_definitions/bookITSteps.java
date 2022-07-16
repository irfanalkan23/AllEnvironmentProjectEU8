package com.cydeo.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class bookITSteps {

    @Given("User logs into UI app with {string} and {string}")
    public void user_logs_into_ui_app_with_and(String email, String password) {
        System.out.println("Logged into UI");
    }
    @When("User navigates to mySelf page and gets user info")
    public void user_navigates_to_my_self_page_and_gets_user_info() {
        System.out.println("Retrived UI info");
    }
    @When("User sends a query to bookIT DB with {string}")
    public void user_sends_a_query_to_book_it_db_with(String email) {

    }
    @Then("UI and DB information should match")
    public void ui_and_db_information_should_match() {

    }

}
