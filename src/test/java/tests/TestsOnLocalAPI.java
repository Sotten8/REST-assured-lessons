package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


public class TestsOnLocalAPI {

    @BeforeSuite
    // Налаштування базового URI
    public void setBaseURI() {
        baseURI = "http://localhost:3000";
    }

    @Test
    public void get() {

        given()
                .get("/users")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void post() throws Exception {
        // Створення об'єкта User
        User user = new User("Mykhailo", "Kalienchenko", 2);

        // Серіалізація об'єкта в JSON за допомогою Jackso

        // Відправка POST-запиту
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().all();
    }
    @Test
    public void put() throws Exception {
        // Створення об'єкта User
        User user_1 = new User("Masha", "Khubedzheva", 1);

        // Серіалізація об'єкта в JSON за допомогою Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(user_1);

        // Зміна об'єкта з допомогою PUT-запиту
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonBody)
                .when()
                .put("/users/4")
                .then()
                .statusCode(200)
                .log().all();
    }
    @Test
    public void patch() throws Exception {
        String jsonBody = "{ \"firstName\": \"Elizabeth\" }";

        // Зміна об'єкта з допомогою PATCH-запиту
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonBody)
                .when()
                .patch("/users/4")
                .then()
                .statusCode(200)
                .log().all();
    }
    @Test
    public void delete() throws Exception {

        // Видалення об'єкта з допомогою DELETE-запиту

        when()
                .delete("/users/4")
                .then()
                .statusCode(200)
                .log().all();
    }
}