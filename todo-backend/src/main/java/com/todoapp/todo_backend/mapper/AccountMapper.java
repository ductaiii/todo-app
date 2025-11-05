package com.todoapp.todo_backend.mapper;

import com.todoapp.todo_backend.dto.AccountDTO;
import com.todoapp.todo_backend.dto.TaskDTO;
import com.todoapp.todo_backend.model.Account;
import com.todoapp.todo_backend.model.Task;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {
  public static AccountDTO toDto(Account a) {
    if (a == null) return null;
    AccountDTO dto = new AccountDTO();
    dto.setId(a.getId());
    dto.setUsername(a.getUsername());
    dto.setEmail(a.getEmail());
    dto.setCreatedAt(a.getCreatedAt());
    dto.setUpdatedAt(a.getUpdatedAt());
    List<Task> tasks = a.getTasks() == null ? Collections.emptyList() : List.copyOf(a.getTasks());
    dto.setTasks(tasks.stream().map(AccountMapper::taskToDto).collect(Collectors.toList()));
    return dto;
  }

  private static TaskDTO taskToDto(Task t) {
    if (t == null) return null;
    TaskDTO td = new TaskDTO();
    td.setId(t.getId());
    td.setTitle(t.getTitle());
    td.setDescription(t.getDescription());
    td.setDueDate(t.getDueDate());
    td.setCompleted(t.isCompleted());
    if (t.getAccount() != null) td.setAccountId(t.getAccount().getId());
    if (t.getTaskCategory() != null) td.setTaskCategoryId(t.getTaskCategory().getId());
    return td;
  }
}

