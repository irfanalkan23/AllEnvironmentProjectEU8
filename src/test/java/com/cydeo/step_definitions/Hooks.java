package com.cydeo.step_definitions;

import com.cydeo.utilities.DBUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before("@db")
    public void dbStart(){
        DBUtils.createConnection();
    }

    @After("@db")
    public void dbEnd(){
        DBUtils.destroy();
    }
}
