package edu.utsa.cs3443.fithubapp.model;

public class WorkoutSession {

    // Stores the workout currently being completed
    private Workout workout;

    // Tracks which exercise the user is currently on
    private int currentExerciseIndex;

    // Tracks which set the user is currently completing
    private int currentSet;

    // Tracks the total number of completed sets
    private int completedSets;

    // Tracks whether the workout has been completed
    private boolean workoutFinished;

    // Creates a new active workout session
    public WorkoutSession(Workout workout) {

        // Makes sure a workout was provided
        if (workout == null) {
            throw new IllegalArgumentException(
                    "Workout cannot be null."
            );
        }

        // Makes sure the workout contains at least one exercise
        if (workout.getExercises().isEmpty()) {
            throw new IllegalArgumentException(
                    "Workout must contain at least one exercise."
            );
        }

        // Stores the workout and begins at the first exercise and first set
        this.workout = workout;
        this.currentExerciseIndex = 0;
        this.currentSet = 1;
        this.completedSets = 0;
        this.workoutFinished = false;
    }

    // Returns the active workout
    public Workout getWorkout() {
        return workout;
    }

    // Returns the exercise the user is currently completing
    public Exercise getCurrentExercise() {

        // Returns null when the entire workout is finished
        if (workoutFinished) {
            return null;
        }

        return workout.getExercises().get(currentExerciseIndex);
    }

    // Returns the position of the current exercise
    public int getCurrentExerciseIndex() {
        return currentExerciseIndex;
    }

    // Returns the current set number
    public int getCurrentSet() {
        return currentSet;
    }

    // Returns the number of completed sets
    public int getCompletedSets() {
        return completedSets;
    }

    // Returns whether the workout has been completed
    public boolean isWorkoutFinished() {
        return workoutFinished;
    }

    // Marks the current set as completed
    public void completeSet() {

        // Prevents changes after the workout has finished
        if (workoutFinished) {
            throw new IllegalStateException(
                    "The workout has already been completed."
            );
        }

        // Records the completed set
        completedSets++;

        // Gets the exercise currently being completed
        Exercise currentExercise = getCurrentExercise();

        // Moves to the next set when more sets remain
        if (currentSet < currentExercise.getSets()) {
            currentSet++;
        } else {

            // Moves to the next exercise after the final set
            moveToNextExercise();
        }
    }

    // Skips the remaining sets of the current exercise
    public void skipExercise() {

        // Prevents changes after the workout has finished
        if (workoutFinished) {
            throw new IllegalStateException(
                    "The workout has already been completed."
            );
        }

        moveToNextExercise();
    }

    // Moves the session to the next exercise
    private void moveToNextExercise() {

        // Checks whether another exercise remains
        if (currentExerciseIndex
                < workout.getExercises().size() - 1) {

            currentExerciseIndex++;
            currentSet = 1;

        } else {

            // Ends the workout after the final exercise
            workoutFinished = true;
        }
    }

    // Calculates the total number of sets in the workout
    public int getTotalSets() {

        int totalSets = 0;

        for (Exercise exercise : workout.getExercises()) {
            totalSets += exercise.getSets();
        }

        return totalSets;
    }

    // Calculates workout progress as a percentage
    public double getProgressPercent() {

        int totalSets = getTotalSets();

        if (totalSets == 0) {
            return 0.0;
        }

        return ((double) completedSets / totalSets) * 100.0;
    }

    // Returns information about the current workout session
    @Override
    public String toString() {

        if (workoutFinished) {
            return workout.getName() + " - Workout completed";
        }

        Exercise currentExercise = getCurrentExercise();

        return workout.getName()
                + " - "
                + currentExercise.getName()
                + " - Set "
                + currentSet
                + " of "
                + currentExercise.getSets();
    }
}