package com.todoapp.todo_backend.mapper;

import com.todoapp.todo_backend.dto.TaskDTO;
import com.todoapp.todo_backend.model.Task;
import com.todoapp.todo_backend.model.Account;
import com.todoapp.todo_backend.model.TaskCategory;

public class TaskMapper {
    public static TaskDTO toDto(Task t) {
        if (t == null) return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setDescription(t.getDescription());
        dto.setCompleted(t.isCompleted());
        dto.setDueDate(t.getDueDate());
        if (t.getAccount() != null) dto.setAccountId(t.getAccount().getId());
        if (t.getTaskCategory() != null) dto.setTaskCategoryId(t.getTaskCategory().getId());
        return dto;
    }

    public static Task toEntity(TaskDTO dto) {
        if (dto == null) return null;
        Task t = new Task();
        t.setId(dto.getId());
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setCompleted(dto.isCompleted());
        t.setDueDate(dto.getDueDate());
        if (dto.getAccountId() != null) {
            Account a = new Account();
            a.setId(dto.getAccountId());
            t.setAccount(a);
        }
        if (dto.getTaskCategoryId() != null) {
            TaskCategory c = new TaskCategory();
            c.setId(dto.getTaskCategoryId());
            t.setTaskCategory(c);
        }
        return t;
    }
}

