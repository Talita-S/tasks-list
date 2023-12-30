package org.talita.s.taskslist.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
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

    @Inject
    public TaskResource(TaskRepository repository) {
        this.repository = repository;
    }

    @POST
    @Transactional
    public Response addTask (AddTaskRequest taskRequest ) {

        Task task = new Task();
        task.setDescription(taskRequest.getDescription());
        task.setDone(taskRequest.isDone());

        repository.persist(task);

        return Response.ok(task).build();
    }

    @GET
    public Response listAllTasks() {
        PanacheQuery<Task> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTask(@PathParam("id") Long id){

        Task task = repository.findById(id);

        if(task != null) {
            repository.delete(task);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
