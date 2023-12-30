package org.talita.s.taskslist.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.talita.s.taskslist.domain.model.Task;
import org.talita.s.taskslist.domain.repository.TaskRepository;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskRepository repository;

    public TaskResource(TaskRepository repository) {
        this.repository = repository;
    }

    @POST
    @Transactional
    public Response addTask (AddTaskRequest taskRequest ) {
        Task task = new Task();
        task.setDescription(taskRequest.getDescription());
        task.setDone(taskRequest.isDone());

        task.persist();

        return Response.ok(task).build();
    }

    @GET
    public Response listAllTasks() {
        PanacheQuery<Task> query = Task.findAll();
        return Response.ok(query.list()).build();
    }

}
