package org.marvin.core.models;

public class Todo {

    private long id;
    private String title;
    private boolean completed;

    public Todo(){}

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean complete) {
        this.completed = complete;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", descr='" + title + '\'' +
                ", isDone=" + completed +
                '}';
    }
}
