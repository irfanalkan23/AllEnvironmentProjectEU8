package com.cydeo.step_definitions;

import com.cydeo.utilities.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

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
        String query = "select firstname, lastname, role, t.name,t.batch_number, c.location\n" +
                "from users u join team t on u.team_id = t.id\n" +
                "             join campus c on u.campus_id=c.id\n" +
                "where u.email='"+email+"'";
        Map<String, Object> rowMap = DBUtils.getRowMap(query);
        System.out.println("rowMap = " + rowMap);

    }
    @Then("UI and DB information should match")
    public void ui_and_db_information_should_match() {

    }

}
