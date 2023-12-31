package org.talita.s.taskslist.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;
import org.talita.s.taskslist.rest.dto.ResponseError;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TaskResourceTest {

    @Test
    @DisplayName("Should create a task successfully")
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

}