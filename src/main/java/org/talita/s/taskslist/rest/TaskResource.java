package org.talita.s.taskslist.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.talita.s.taskslist.rest.dto.AddTaskRequest;

@Path("/tasks")
public class TaskResource {

    @POST
    public Response addTask (AddTaskRequest taskRequest ) {
        return Response.ok().build();
    }

}
