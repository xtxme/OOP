package demoTodolist.src.main.java.lab16.demotodolist.controller;

import lab16.demotodolist.dto.CreateTodoItem;
import lab16.demotodolist.model.TodoItem;
import demoTodolist.src.main.java.lab16.demotodolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class MyTodoController {
    private final TodoRepository todoRepository;

    @Autowired
    public MyTodoController(@Qualifier("MyTodoRepository") TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/")
    public List<TodoItem> getTodoList(@RequestParam(name = "status", required = false) Boolean completed){
        if (completed != null) {
            return todoRepository.findByCompleted(completed);
        }
        return todoRepository.findAll();
    }

    @PostMapping("/")
    public TodoItem addTodoItem(@RequestBody CreateTodoItem createTodoItem){ //สำหรับ JSON
        return todoRepository.add(createTodoItem.getTitle());
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable(name="id") int todoId){
        todoRepository.deleteById(todoId);
    }

    @PostMapping("/upload")
    public List<TodoItem> uploadTodoItems(@RequestBody List<CreateTodoItem> createTodoItems) {
        List<TodoItem> addedItems = new ArrayList<>();
        for (CreateTodoItem createTodoItem : createTodoItems) {
            TodoItem addedItem = todoRepository.add(createTodoItem.getTitle());
            addedItems.add(addedItem);
        }
        return addedItems;
    }

    @PutMapping("/{id}")
    public void updateItem(@PathVariable(name="id") int todoId){
        todoRepository.updateById(todoId);
    }

    @GetMapping("/search")
    public List<TodoItem> searchTodoList(@RequestParam(required = false) String title) {
        if(title != null){
            return todoRepository.searchByTitle(title);
        }
        return null;
    }
}
