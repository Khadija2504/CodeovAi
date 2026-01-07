package main.java.com.workpal.model;

import java.util.Map;
import java.util.Date;

public class Space {
    private int id;
    private String name;
    private String type;
    private int capacity;
    private Map<Date, Boolean> availability;

    public Space(int id, String name, String type, int capacity, Map<Date, Boolean> availability) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.availability = availability;
    }

    public boolean checkAvailability(Date date) {
        return availability.getOrDefault(date, false);
    }

    public void reserveSpace(Date date) {
        if (checkAvailability(date)) {
            availability.put(date, false);
        }
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Map<Date, Boolean> getAvailability() {
        return availability;
    }
    
    public void setAvailability(Map<Date, Boolean> availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Space{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", availability=" + availability +
                '}';
    }
}
