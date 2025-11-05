package com.todoapp.todo_backend.service;

import com.todoapp.todo_backend.model.Task;
import com.todoapp.todo_backend.repository.TaskRepository;
import com.todoapp.todo_backend.dto.TaskDTO;
import com.todoapp.todo_backend.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    // lấy tất cả các task (entity)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasksDto() {
        return taskRepository.findAll().stream().map(TaskMapper::toDto).collect(Collectors.toList());
    }
    // lấy task theo id
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public TaskDTO findDtoById(Long id) {
        return taskRepository.findById(id).map(TaskMapper::toDto).orElse(null);
    }

    // tạo hoặc cập nhật task từ entity
    public Task save(Task task) {
        return taskRepository.save(task);

    }

    // tạo hoặc cập nhật từ DTO
    public TaskDTO saveFromDto(TaskDTO dto) {
        Task t = TaskMapper.toEntity(dto);
        Task saved = taskRepository.save(t);
        return TaskMapper.toDto(saved);
    }

    // xóa task theo id
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    // đánh dấu hoàn thành
    @Transactional
    public TaskDTO markComplete(Long id, boolean completed) {
        Optional<Task> opt = taskRepository.findById(id);
        if (opt.isEmpty()) return null;
        Task t = opt.get();
        t.setCompleted(completed);
        Task saved = taskRepository.save(t);
        return TaskMapper.toDto(saved);
    }

    // set due date
    @Transactional
    public TaskDTO setDueDate(Long id, LocalDateTime dateTime) {
        Optional<Task> opt = taskRepository.findById(id);
        if (opt.isEmpty()) return null;
        Task t = opt.get();
        t.setDueDate(dateTime);
        Task saved = taskRepository.save(t);
        return TaskMapper.toDto(saved);
    }


}
