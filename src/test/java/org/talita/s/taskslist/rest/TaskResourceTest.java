package org.talita.s.taskslist.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;

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

}