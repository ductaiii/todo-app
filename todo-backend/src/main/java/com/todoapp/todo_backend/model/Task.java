package com.todoapp.todo_backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "is_completed")
    private boolean isCompleted;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @ManyToOne
    @JoinColumn(name = "task_category_id")
    @JsonBackReference
    private TaskCategory taskCategory;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    @JsonIgnore
    private List<TaskTagMap> tagMaps;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    @JsonIgnore
    private List<TaskComment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public List<TaskTagMap> getTagMaps() {
        return tagMaps;
    }

    public void setTagMaps(List<TaskTagMap> tagMaps) {
        this.tagMaps = tagMaps;
    }

    public List<TaskComment> getComments() {
        return comments;
    }

    public void setComments(List<TaskComment> comments) {
        this.comments = comments;
    }

    public Task() {
    }

    public Task(Long id, String title, String description, boolean isCompleted, LocalDateTime dueDate, Account account,
            TaskCategory taskCategory, List<TaskTagMap> tagMaps, List<TaskComment> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.account = account;
        this.taskCategory = taskCategory;
        this.tagMaps = tagMaps;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                ", dueDate=" + dueDate +
                ", account=" + account +
                ", taskCategory=" + taskCategory +
                ", tagMaps=" + tagMaps +
                ", comments=" + comments +
                '}';
    }
}
