package edu.utsa.cs3443.fithubapp.util;

import edu.utsa.cs3443.fithubapp.model.Exercise;
import edu.utsa.cs3443.fithubapp.model.Workout;

import java.util.ArrayList;

public class WorkoutGenerator {

    // Prevents WorkoutGenerator objects from being created
    private WorkoutGenerator() {
    }

    // Generates a workout using selected muscles and equipment
    public static Workout generateWorkout(
            ArrayList<String> selectedMuscleGroups,
            String equipmentType) {

        if (selectedMuscleGroups == null
                || selectedMuscleGroups.isEmpty()) {

            throw new IllegalArgumentException(
                    "Select at least one muscle group."
            );
        }

        if (equipmentType == null || equipmentType.isBlank()) {
            throw new IllegalArgumentException(
                    "Equipment type cannot be empty."
            );
        }

        if (!equipmentType.equalsIgnoreCase("Home")
                && !equipmentType.equalsIgnoreCase("Gym")) {

            throw new IllegalArgumentException(
                    "Equipment type must be Home or Gym."
            );
        }

        // Makes sure none of the selected muscle groups are empty
        for (String muscleGroup : selectedMuscleGroups) {
            if (muscleGroup == null || muscleGroup.isBlank()) {
                throw new IllegalArgumentException(
                        "Muscle groups cannot be empty."
                );
            }
        }

        // Creates a name such as "Back, Arms, Legs Workout"
        String workoutName =
                String.join(", ", selectedMuscleGroups)
                        + " Workout";

        // Estimates twenty minutes per selected muscle group
        int durationMin =
                selectedMuscleGroups.size() * 20;

        Workout workout =
                new Workout(workoutName, durationMin);

        // Stores all muscle groups selected by the user
        for (String muscleGroup : selectedMuscleGroups) {
            workout.addMuscleGroup(muscleGroup);
        }

        // Loads matching exercises from the CSV
        ArrayList<Exercise> matchingExercises =
                ExerciseFileLoader.loadExercises(
                        selectedMuscleGroups,
                        equipmentType
                );

        if (matchingExercises.isEmpty()) {
            throw new IllegalArgumentException(
                    "No matching exercises were found."
            );
        }

        // Adds all matching CSV exercises to the workout
        for (Exercise exercise : matchingExercises) {
            workout.addExercise(exercise);
        }

        return workout;
    }
}