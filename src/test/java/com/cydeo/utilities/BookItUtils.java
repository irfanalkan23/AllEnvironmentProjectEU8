package com.cydeo.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;



public class BookItUtils {

    public static Object getInfo(String endPath, String responsePath){

        String path = ConfigurationReader.getProperty("apiUrl")+"/api" + endPath;
        return RestAssured.given().accept(ContentType.JSON)
                .and()
                .header("Authorization", Token.getToken())
                .when()
                .get(path).path(responsePath);
    }
}
