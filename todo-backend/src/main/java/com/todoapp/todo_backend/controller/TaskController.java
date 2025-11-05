package com.todoapp.todo_backend.controller;

import com.todoapp.todo_backend.dto.TaskDTO;
import com.todoapp.todo_backend.model.Task;
import com.todoapp.todo_backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // Cho phép gọi API từ bất kỳ frontend nào
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Lấy tất cả tasks (DTO)
    @PostMapping("/all")
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasksDto();
    }

    @PostMapping("/get")
    public TaskDTO getTaskById(@RequestBody Map<String, Long> body) {
        if (body == null || !body.containsKey("id")) {
            throw new IllegalArgumentException("không có id trong body hoặc body là null");
        }
        Long id = body.get("id");
        return taskService.findDtoById(id);
    }

    // Tạo hoặc cập nhật task (sử dụng DTO)
    @PostMapping("/save")
    public TaskDTO saveTask(@RequestBody TaskDTO taskDto) {
        return taskService.saveFromDto(taskDto);
    }

    // Xoá task theo id
    @PostMapping("/delete")
    public void deleteTaskById(@RequestBody Map<String, Long> body) {
        Long id = body.get("id");
        if (id == null) {
            throw new IllegalArgumentException("ID không được cung cấp");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID phải lớn hơn 0");
        }
        taskService.deleteById(id);
    }

    // Đánh dấu hoàn thành / không hoàn thành
    @PostMapping("/complete")
    public TaskDTO completeTask(@RequestBody Map<String, Object> body) {
        if (body == null || !body.containsKey("id") || !body.containsKey("completed")) {
            throw new IllegalArgumentException("Yêu cầu phải có id và completed");
        }
        Long id = ((Number) body.get("id")).longValue();
        boolean completed = Boolean.parseBoolean(body.get("completed").toString());
        return taskService.markComplete(id, completed);
    }

    // Set deadline (expect ISO date-time string under "dueDate")
    @PostMapping("/set-deadline")
    public TaskDTO setDeadline(@RequestBody Map<String, String> body) {
        if (body == null || !body.containsKey("id") || !body.containsKey("dueDate")) {
            throw new IllegalArgumentException("Yêu cầu phải có id và dueDate");
        }
        Long id = Long.parseLong(body.get("id"));
        try {
            LocalDateTime dt = LocalDateTime.parse(body.get("dueDate"));
            return taskService.setDueDate(id, dt);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("dueDate phải ở định dạng ISO_LOCAL_DATE_TIME, ví dụ: 2025-11-05T15:00:00");
        }
    }
}
