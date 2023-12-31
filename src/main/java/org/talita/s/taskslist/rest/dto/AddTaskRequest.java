package org.talita.s.taskslist.rest.dto;

import jakarta.validation.constraints.NotBlank;

public class AddTaskRequest {

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    private boolean done;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
