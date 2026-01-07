package main.java.com.workpal.model;

public class Notification {
    private int id;
    private String description;
    private int user_id;

    public Notification(int id, String description, int user_id) {
        this.id = id;
        this.description = description;
        this.user_id = user_id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getUserId() {
        return user_id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
    
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user_id=" + user_id +
                '}';
    }

}
