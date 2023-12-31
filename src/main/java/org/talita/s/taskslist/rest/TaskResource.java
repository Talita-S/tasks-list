package org.talita.s.taskslist.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.talita.s.taskslist.domain.model.Task;
import org.talita.s.taskslist.domain.repository.TaskRepository;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;
import org.talita.s.taskslist.rest.dto.ResponseError;

import java.util.Set;

import static org.talita.s.taskslist.rest.dto.ResponseError.UNPROCESSABLE_ENTITY_STATUS;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskRepository repository;
    private final Validator validator;

    @Inject
    public TaskResource(TaskRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    @Operation(summary = "Add a new task")
    public Response addTask (AddTaskRequest taskRequest ) {

        Set<ConstraintViolation<AddTaskRequest>> violations = validator.validate(taskRequest);

        if(!violations.isEmpty()) {
            return  ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(UNPROCESSABLE_ENTITY_STATUS);
        }

        Task task = new Task();
        task.setDescription(taskRequest.getDescription());
        task.setDone(taskRequest.isDone());

        repository.persist(task);

        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @GET
    @Operation(summary = "List all tasks")
    public Response listAllTasks() {
        PanacheQuery<Task> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete a task by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Invalid id")
    public Response deleteTask(@PathParam("id") Long id){

        Task task = repository.findById(id);

        if(task != null) {
            repository.delete(task);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update a task by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Invalid id")
    public Response updateTask(@PathParam("id") Long id, AddTaskRequest taskData) {
        Task task = repository.findById(id);

        if(task != null) {
            task.setDescription(taskData.getDescription());
            task.setDone(taskData.isDone());
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
