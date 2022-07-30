package com.cydeo.tests;

import com.cydeo.utilities.BookItUtils;
import com.cydeo.utilities.DBUtils;
import com.cydeo.utilities.ExcelUtil;
import com.cydeo.utilities.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookItAPIvsDBwithParameterized {

    public static List<Map<String ,String >> getExcelData(){
        ExcelUtil bookitFile = new ExcelUtil("src/test/resources/BookItQa3.xlsx","QA3");
        return bookitFile.getDataList();
    }

    @ParameterizedTest
    @MethodSource("getExcelData")
    public void bookItTest(Map<String,String> user){
        // connect DB
        DBUtils.createConnection();
        // get info from DB
        String query = "select firstname, lastname, role, t.name,t.batch_number, c.location\n" +
                "from users u join team t on u.team_id = t.id\n" +
                "             join campus c on u.campus_id=c.id\n" +
                "where u.email='"+user.get("email")+"'";
       Map<String,Object> dbInfoMap = DBUtils.getRowMap(query);
       // close connection
       DBUtils.destroy();
        System.out.println("dbInfoMap = " + dbInfoMap);
       // sign into bookit API
       String finalToken = Token.getToken(user.get("email"),user.get("password"));

       Map<String,Object> apiInfoMap = new HashMap<>();
       apiInfoMap.put("firstname", BookItUtils.getInfo("/students/me","firstName"));
       apiInfoMap.put("lastname", BookItUtils.getInfo("/students/me","lastName"));
       apiInfoMap.put("role", BookItUtils.getInfo("/students/me","role"));
       apiInfoMap.put("name", BookItUtils.getInfo("/teams/my","name"));
       apiInfoMap.put("batch_number", BookItUtils.getInfo("/batches/my","number"));
       apiInfoMap.put("location", BookItUtils.getInfo("/campuses/my","location"));
       Token.endToken();
        System.out.println("apiInfoMap = " + apiInfoMap);
    }

}
