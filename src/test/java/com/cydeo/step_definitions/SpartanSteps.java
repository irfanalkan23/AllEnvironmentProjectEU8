package com.cydeo.step_definitions;

import com.cydeo.utilities.DBUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class SpartanSteps {

    List<Map<String,Object>> queryResultList;

    @When("user executes query to get all spartan")
    public void user_executes_query_to_get_all_spartan() {
        String query = "select spartan_id, name, gender, phone, created_at, updated_at from spartans";
       queryResultList = DBUtils.getQueryResultMap(query);
    }
    @Then("user should see {int} results")
    public void user_should_see_results(Integer expectedValue) {
        int numberOfSpartans = queryResultList.size();
        System.out.println("numberOfSpartans = " + numberOfSpartans);
        System.out.println("expectedValue = " + expectedValue);
    }
}
