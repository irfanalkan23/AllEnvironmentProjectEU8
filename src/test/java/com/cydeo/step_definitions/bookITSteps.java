package com.cydeo.step_definitions;

import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.DBUtils;
import com.cydeo.utilities.Token;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class bookITSteps {

    Map<String,Object> apiInfoMap = new HashMap<>();
    Map<String,Object> dbInfoMap = new HashMap<>();


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
        dbInfoMap = DBUtils.getRowMap(query);
        System.out.println("dbInfoMap = " + dbInfoMap);

    }
    @Then("UI and DB information should match")
    public void ui_and_db_information_should_match() {

    }

    @And("User logs into BookIT API using {string} and {string}")
    public void userLogsIntoBookITAPIUsingAnd(String email, String password) {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .and()
                .header("Authorization", Token.getToken(email,password))
                .when()
                .get(ConfigurationReader.getProperty("apiUrl")+"/api/students/me");
       // response.prettyPrint();
        JsonPath bodyData = response.jsonPath();
        apiInfoMap.put("firstname",bodyData.getString("firstName"));
        apiInfoMap.put("lastname",bodyData.getString("lastName"));
        apiInfoMap.put("role",bodyData.getString("role"));
        System.out.println("apiInfoMap = " + apiInfoMap);
    }

    @And("User gets related API information")
    public void userGetsRelatedAPIInformation() {
        String path = "";
//            for (int i=0; i<3 ; i++){
//                if(i==0){
//                    path = ConfigurationReader.getProperty("apiUrl")+"/api/teams/my";
//                    Response response = RestAssured.given().accept(ContentType.JSON)
//                            .and()
//                            .header("Authorization", Token.getToken())
//                            .when()
//                            .get(path);
//                    apiInfoMap.put("name",response.path("name"));
//                }
//                if(i==1){
//                    path = ConfigurationReader.getProperty("apiUrl")+"/api/batches/my";
//                    Response response = RestAssured.given().accept(ContentType.JSON)
//                            .and()
//                            .header("Authorization", Token.getToken())
//                            .when()
//                            .get(path);
//                    apiInfoMap.put("batch_number",response.path("number"));
//                }
//                if(i==2){
//                    path = ConfigurationReader.getProperty("apiUrl")+"/api/campuses/my";
//                    Response response = RestAssured.given().accept(ContentType.JSON)
//                            .and()
//                            .header("Authorization", Token.getToken())
//                            .when()
//                            .get(path);
//                    apiInfoMap.put("location",response.path("location"));
//                }
//            }

            path = ConfigurationReader.getProperty("apiUrl")+"/api/teams/my";
            Response response1 = RestAssured.given().accept(ContentType.JSON)
                    .and()
                    .header("Authorization", Token.getToken())
                    .when()
                    .get(path);
            apiInfoMap.put("name",response1.path("name"));

            path = ConfigurationReader.getProperty("apiUrl")+"/api/batches/my";
            Response response2 = RestAssured.given().accept(ContentType.JSON)
                    .and()
                    .header("Authorization", Token.getToken())
                    .when()
                    .get(path);
            apiInfoMap.put("batch_number",response2.path("number"));

            path = ConfigurationReader.getProperty("apiUrl")+"/api/campuses/my";
            Response response3 = RestAssured.given().accept(ContentType.JSON)
                    .and()
                    .header("Authorization", Token.getToken())
                    .when()
                    .get(path);
            apiInfoMap.put("location",response3.path("location"));

            Token.endToken();
        System.out.println("apiInfoMap = " + apiInfoMap);
    }

    @Then("API and DB information should match")
    public void apiAndDBInformationShouldMatch() {
        Assert.assertEquals(dbInfoMap,apiInfoMap);
    }
}
