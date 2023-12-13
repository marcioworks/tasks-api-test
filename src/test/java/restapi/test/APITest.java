package restapi.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

public class APITest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void shouldReturnAllTodos() {
        RestAssured.given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200);

    }

    @Test
    public void shouldSaveATodo() {
        RestAssured.given()
                .body("{ \"task\":\"teste via API\",\"dueDate\":\"2024-02-02\"} ").contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void shouldNotCreateATodo(){
        RestAssured.given()
                .body("{ \"task\":\"teste via API\",\"dueDate\":\"2022-02-02\"} ").contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}
