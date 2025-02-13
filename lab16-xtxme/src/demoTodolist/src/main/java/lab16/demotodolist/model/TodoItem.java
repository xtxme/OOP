package demoTodolist.src.main.java.lab16.demotodolist.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItem {
    private static int nextId = 0;
    private static int getNextId() {
        return nextId++;
    }

    private int id;
    private String title;
    private Boolean completed = false;

    public TodoItem(String title) {
        this.id = getNextId();
        this.title = title;
    }
}
