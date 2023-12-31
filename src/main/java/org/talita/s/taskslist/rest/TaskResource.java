package org.talita.s.taskslist.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.talita.s.taskslist.domain.model.Task;
import org.talita.s.taskslist.domain.repository.TaskRepository;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskRepository repository;

    @Inject
    public TaskResource(TaskRepository repository) {
        this.repository = repository;
    }

    @POST
    @Transactional
    @Operation(summary = "Add a new task")
    public Response addTask (AddTaskRequest taskRequest ) {

        Task task = new Task();
        task.setDescription(taskRequest.getDescription());
        task.setDone(taskRequest.isDone());

        repository.persist(task);

        return Response.ok(task).build();
    }

    @GET
    @Operation(summary = "List all tasks")
    public Response listAllTasks() {
        PanacheQuery<Task> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
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
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Invalid id")
    public Response updateTask(@PathParam("id") Long id, AddTaskRequest taskData) {
        Task task = repository.findById(id);

        if(task != null) {
            task.setDescription(taskData.getDescription());
            task.setDone(taskData.isDone());
            return Response.ok("Tarefa atualizada com sucesso!").build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
