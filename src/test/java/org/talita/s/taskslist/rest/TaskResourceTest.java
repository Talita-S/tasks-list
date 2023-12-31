package org.talita.s.taskslist.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;
import org.talita.s.taskslist.rest.dto.ResponseError;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskResourceTest {

    @Test
    @DisplayName("Should create a task successfully")
    @Order(1)
    void addTaskTest() {
        var task = new AddTaskRequest();
        task.setDescription("Tarefa teste");
        task.setDone(true);

        var response =
                given()
                    .contentType(ContentType.JSON)
                    .body(task)
                .when()
                    .post("/tasks")
                .then()
                    .extract().response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Should return error when json isn't valid")
    @Order(2)
    void addTaskValidationErrorTest() {
        var task = new AddTaskRequest();
        task.setDescription(null);
        task.setDone(false);

        var response =
                given()
                    .contentType(ContentType.JSON)
                    .body(task)
                .when()
                    .post("/tasks")
                .then()
                    .extract().response();
        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.statusCode());
        assertEquals("Erro de validação", response.jsonPath().getString("message"));

        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertNotNull(errors.get(0).get("message"));
    }

    @Test
    @DisplayName("Should retrieve all tasks successfully")
    @Order(3)
    void listAllTasksTest() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/tasks")
        .then()
            .statusCode(200)
            .body("size()", Matchers.is(1));
    }

    @Test
    @DisplayName("Should delete a task successfully")
    void deleteTaskTest() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete("/tasks/1")
        .then()
            .statusCode(204);
    }

    @Test
    @DisplayName("Should return 404 when delete a task with nonexistent id")
    void idNotFoundWhenDeleteTaskTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/tasks/2")
                .then()
                .statusCode(404);
    }

}