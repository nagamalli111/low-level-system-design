package com.matty.task_management;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Task {
    private final String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private int priority;
    private TaskStatus status;
    private final User assignedUser;
}
