package tests;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class XMLSchemaValidation {

    @BeforeSuite
    // Налаштування базового URI
    public void setBaseURI() {
        baseURI = "http://www.dneonline.com";
    }

    @Test
    public void schemaValidation() throws IOException {
        File file = new File("./SoapRequest/Add.xml");

        if(file.exists())
            System.out.println(" >> File Exists");

        String requestBody = Files.readString(Paths.get(file.getPath()));

        given()
                .contentType("text/xml")
                .accept(ContentType.XML)
                .body(requestBody)
                .when()
                .post("/calculator.asmx")
                .then()
                .statusCode(200)
                .log().all()
                .and()
                .body("//*:AddResult.text()", equalTo("5"))
                .and()
                .assertThat()
                .body(matchesXsdInClasspath("Calculator.xsd"));

    }
}
