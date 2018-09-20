package com.maxsoft.restassured.demo;

import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

/**
 * Project Name : Rest Assured Example
 * Developer    : Osanda Deshan
 * Version      : 1.0.0
 * Date         : 9/20/2018
 * Time         : 8:19 PM
 * Description  :
 **/


public class JsonRequestExample {

    private static Response response;
    private Map<String, String> jsonData = new HashMap<>();
    private static final String piUrl = "http://int-piapi-internal.stg-openclass.com/tokens?useJwt=true";

    @Step("Set json key value pairs <table>")
    public Map<String, String> createFormParamsMap(Table table) {
        List<TableRow> rows = table.getTableRows();
        List<String> columnNames = table.getColumnNames();
        for (TableRow row : rows) {
            jsonData.put(row.getCell(columnNames.get(0)), row.getCell(columnNames.get(1)));
        }
        return jsonData;
    }

    private void clearJsonDataHashMap(){
        jsonData.clear();
    }

    @Step("Invoke API")
    public void sendFormDataRequest() {
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .post(piUrl).then().extract().response();
        System.out.println("JSON Data Hash Map: \n" + jsonData);
        Gauge.writeMessage("JSON Data Hash Map: \n" + jsonData);
        System.out.println("Response is:\n" + response.prettyPrint());
        Gauge.writeMessage("Response is:\n" + response.prettyPrint());
        clearJsonDataHashMap();
    }


}
