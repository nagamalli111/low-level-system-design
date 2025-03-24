package com.matty.task_management;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class User {
    private final String id;
    private final String name;
    private final String email;
}
