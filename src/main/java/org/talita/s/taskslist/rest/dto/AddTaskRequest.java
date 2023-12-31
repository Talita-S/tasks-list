package org.talita.s.taskslist.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddTaskRequest {

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    private boolean done;
}
