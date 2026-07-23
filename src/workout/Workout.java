package workout;

import java.util.ArrayList;

public class Workout {

    // Stores the complete generated workout
    private String name;
    private int durationMin;
    private ArrayList<String> muscleGroups;
    private ArrayList<Exercise> exercises;

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