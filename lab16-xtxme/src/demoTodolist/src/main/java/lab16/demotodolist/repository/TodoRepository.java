package demoTodolist.src.main.java.lab16.demotodolist.repository;

import lab16.demotodolist.model.TodoItem;

import java.util.List;
import lab16.demotodolist.model.TodoItem;

public interface TodoRepository {
    List<TodoItem> findAll();
    List<TodoItem> findByCompleted(boolean completed);
    TodoItem add(String title);
    void deleteById(int id);
    TodoItem addAll(TodoItem item);
    void updateById(int id);
    List<TodoItem> searchByTitle(String title);
}
