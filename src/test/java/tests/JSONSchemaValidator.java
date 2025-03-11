package tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JSONSchemaValidator {

    @BeforeSuite
    // Налаштування базового URI
    public void setBaseURI() {
        baseURI = "http://reqres.in/api";
    }

    @Test
    public void get() {

        given()
                .get("/users?page=2")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema.json"))
                .statusCode(200);
    }
}