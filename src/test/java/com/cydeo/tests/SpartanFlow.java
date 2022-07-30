package com.cydeo.tests;

import com.cydeo.utilities.ExcelUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanFlow {

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "http://44.202.119.26:8000/";
    }

    public static List<Map<String,String>> getMySpartans(){
        ExcelUtil spartanFile = new ExcelUtil("src/test/resources/mySpartans3.xlsx","data");
        return spartanFile.getDataList();
    }

    @ParameterizedTest
    @MethodSource("getMySpartans")
    public void postSpartan(Map<String,String> spartans){
        //create a map to keep request json body information
       // BigDecimal phone = BigDecimal.valueOf(Long.parseLong(spartans.get("phone")));


        Map<String,Object> requestJsonMap = new LinkedHashMap<>();
        requestJsonMap.put("name",spartans.get("name"));
        requestJsonMap.put("gender",spartans.get("gender"));
        requestJsonMap.put("phone", Long.parseLong(spartans.get("phone").replace(" ","")));
        System.out.println("requestJsonMap = " + requestJsonMap);

        Response response = given().accept(ContentType.JSON).and() //what we are asking from api which is JSON response
                .contentType(ContentType.JSON) //what we are sending to api, which is JSON also
                .body(requestJsonMap).log().all()
                .when()
                .post("/api/spartans");

        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";
        assertThat(response.path("success"),is(expectedResponseMessage));
        assertThat(response.path("data.name"),is(spartans.get("name")));
        assertThat(response.path("data.gender"),is(spartans.get("gender")));
        assertThat(response.path("data.phone"),is(Long.parseLong(spartans.get("phone").replace(" ",""))));

        int idFromPost = response.path("data.id");


        // get request
        given().accept(ContentType.JSON)
                .and().pathParam("id",idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).body("name",is (spartans.get("name")));

        // delete request
        given().accept(ContentType.JSON)
                .and().pathParam("id",idFromPost)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204).log().all();

    }
}
