package edu.utsa.cs3443.fithubapp.model;

public class Exercise {

    // Information loaded from the CSV file
    private String workoutName;
    private String name;
    private String muscleGroup;
    private String equipmentType;
    private int sets;
    private int reps;

    public Exercise(
            String workoutName,
            String name,
            String muscleGroup,
            String equipmentType,
            int sets,
            int reps) {

        if (workoutName == null || workoutName.isBlank()) {
            throw new IllegalArgumentException(
                    "Workout name cannot be empty."
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Exercise name cannot be empty."
            );
        }

        if (muscleGroup == null || muscleGroup.isBlank()) {
            throw new IllegalArgumentException(
                    "Muscle group cannot be empty."
            );
        }

        if (equipmentType == null || equipmentType.isBlank()) {
            throw new IllegalArgumentException(
                    "Equipment type cannot be empty."
            );
        }

        if (sets <= 0) {
            throw new IllegalArgumentException(
                    "Sets must be greater than zero."
            );
        }

        if (reps <= 0) {
            throw new IllegalArgumentException(
                    "Reps must be greater than zero."
            );
        }

        // Stores the exercise information
        this.workoutName = workoutName;
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.equipmentType = equipmentType;
        this.sets = sets;
        this.reps = reps;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getName() {
        return name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    // Returns the exercise information as readable text
    @Override
    public String toString() {
        return name + " - " + sets + " sets of " + reps + " reps";
    }
}