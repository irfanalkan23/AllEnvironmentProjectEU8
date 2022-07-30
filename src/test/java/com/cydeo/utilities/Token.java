package com.cydeo.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Token {
    private static String finalToken;

    private Token(){}


    public static String getToken(){

        if (finalToken==null){
            Response response = RestAssured.given().
                    accept(ContentType.JSON)
                    .queryParam("email", ConfigurationReader.getProperty("username"))
                    .queryParam("password", ConfigurationReader.getProperty("password"))
                    .when()
                    .get(ConfigurationReader.getProperty("apiUrl") +"/sign");


            String token = response.path("accessToken");

            finalToken ="Bearer "+token;

            return  finalToken;

        }
        return finalToken;
    }

    public static String getToken(String userEmail, String password){

        if (finalToken==null){
            Response response = RestAssured.given().
                    accept(ContentType.JSON)
                    .queryParam("email", userEmail)
                    .queryParam("password", password)
                    .when()
                    .get(ConfigurationReader.getProperty("apiUrl") +"/sign");


            String token = response.path("accessToken");

            finalToken ="Bearer "+token;

            return  finalToken;

        }
        return finalToken;
    }

    public static void endToken(){
        finalToken = null;
    }
}
