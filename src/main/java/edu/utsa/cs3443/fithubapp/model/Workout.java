package edu.utsa.cs3443.fithubapp.model;

import java.util.ArrayList;

//Represents a generated workout in the FitHub application.
//A workout stores its name, estimated duration, selected muscle groups, and the exercises included in the workout.
public class Workout {

    // Stores the complete generated workout
    private String name;
    private int durationMin;
    private ArrayList<String> muscleGroups;
    private ArrayList<Exercise> exercises;

    //Creates a new workout with a empty muscle group list and exercise list
    //has name of the workout
    //duretaionMin estimated workout duration in min
    //IllegalArgumentException when the name is empty or the duration is not positive
    public Workout(String name, int durationMin) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Workout name cannot be empty."
            );
        }

        if (durationMin <= 0) {
            throw new IllegalArgumentException(
                    "Workout duration must be greater than zero."
            );
        }

        // Stores the workout information and creates empty lists
        this.name = name;
        this.durationMin = durationMin;
        this.muscleGroups = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    public void addExercise(Exercise exercise) {

        if (exercise == null) {
            throw new IllegalArgumentException(
                    "Exercise cannot be null."
            );
        }

        exercises.add(exercise);
    }

    public void addMuscleGroup(String muscleGroup) {

        if (muscleGroup == null || muscleGroup.isBlank()) {
            throw new IllegalArgumentException(
                    "Muscle group cannot be empty."
            );
        }

        // Prevents the same muscle group from being added twice
        if (!muscleGroups.contains(muscleGroup)) {
            muscleGroups.add(muscleGroup);
        }
    }

    public String getName() {
        return name;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public ArrayList<String> getMuscleGroups() {
        return muscleGroups;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    // Returns general workout information
    @Override
    public String toString() {
        return name
                + " - "
                + durationMin
                + " minutes - "
                + exercises.size()
                + " exercises";
    }
}