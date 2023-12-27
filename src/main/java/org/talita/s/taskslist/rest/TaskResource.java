package org.talita.s.taskslist.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    @POST
    public Response addTask (AddTaskRequest taskRequest ) {
        return Response.ok(taskRequest).build();
    }

    @GET
    public Response listAllTasks() {
        return Response.ok().build();
    }

}
