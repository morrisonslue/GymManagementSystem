package org.keyin.workoutclasses;

public class WorkoutClass {
    private int id;
    private String type;
    private String description;
    private int trainerId;

    public WorkoutClass(int id, String type, String description, int trainerId) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.trainerId = trainerId;
    }

    public WorkoutClass(String type, String description, int trainerId) {
        this.type = type;
        this.description = description;
        this.trainerId = trainerId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getTrainerId() {
        return trainerId;
    }

    @Override
    public String toString() {
        return "WorkoutClass{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", trainerId=" + trainerId +
                '}';
    }
}


