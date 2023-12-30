package org.talita.s.taskslist.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.talita.s.taskslist.domain.model.Task;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {
}
