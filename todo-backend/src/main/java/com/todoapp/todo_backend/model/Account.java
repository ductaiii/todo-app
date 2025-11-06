package com.todoapp.todo_backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  @JsonIgnore
  private String passwordHash;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  @JsonIgnore // tránh serialize collection khi trả về Account
  private Set<Task> tasks = new HashSet<>();

  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  @JsonIgnore // tránh serialize collection khi trả về Account
  private Set<TaskComment> comments = new HashSet<>();

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Set<Task> getTasks() {
    return tasks;
  }

  public void setTasks(Set<Task> tasks) {
    this.tasks = tasks;
  }

  public Set<TaskComment> getComments() {
    return comments;
  }

  public void setComments(Set<TaskComment> comments) {
    this.comments = comments;
  }

  public Account() {
  }

  public Account(Long id, String username, String email, String passwordHash, LocalDateTime createdAt,
      LocalDateTime updatedAt, Set<Task> tasks, Set<TaskComment> comments) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.tasks = tasks;
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "Account{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", passwordHash='" + passwordHash + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", tasks=" + tasks +
        ", comments=" + comments +
        '}';
  }
}
