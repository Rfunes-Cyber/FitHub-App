package edu.utsa.cs3443.fithubapp.util;

import edu.utsa.cs3443.fithubapp.model.Exercise;
import edu.utsa.cs3443.fithubapp.model.Workout;

import java.util.ArrayList;

public class WorkoutGenerator {

    private static final String NO_PREFERENCE =
            "No preference";

    // Prevents WorkoutGenerator objects from being created
    private WorkoutGenerator() {
    }

    // Original method preserved for WorkoutTest and other code
    public static Workout generateWorkout(
            ArrayList<String> selectedMuscleGroups,
            String equipmentType) {

        return generateWorkout(
                selectedMuscleGroups,
                equipmentType,
                NO_PREFERENCE
        );
    }

    // Generates a workout with an optional preferred exercise
    public static Workout generateWorkout(
            ArrayList<String> selectedMuscleGroups,
            String equipmentType,
            String preferredExerciseName) {

        validateSelections(
                selectedMuscleGroups,
                equipmentType
        );

        String workoutName =
                String.join(
                        ", ",
                        selectedMuscleGroups
                )
                        + " Workout";

        // Estimates twenty minutes per muscle group
        int durationMin =
                selectedMuscleGroups.size() * 20;

        Workout workout =
                new Workout(
                        workoutName,
                        durationMin
                );

        for (String muscleGroup
                : selectedMuscleGroups) {

            workout.addMuscleGroup(
                    muscleGroup
            );
        }

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

        Exercise preferredExercise =
                getPreferredExercise(
                        matchingExercises,
                        preferredExerciseName
                );

        // Add the preferred exercise first
        if (preferredExercise != null) {
            workout.addExercise(
                    preferredExercise
            );
        }

        // Add all remaining exercises
        for (Exercise exercise : matchingExercises) {

            if (exercise != preferredExercise) {
                workout.addExercise(exercise);
            }
        }

        return workout;
    }

    // Returns the preferred exercise when one was selected
    private static Exercise getPreferredExercise(
            ArrayList<Exercise> matchingExercises,
            String preferredExerciseName) {

        if (preferredExerciseName == null
                || preferredExerciseName.isBlank()
                || preferredExerciseName.equalsIgnoreCase(
                NO_PREFERENCE
        )) {

            return null;
        }

        Exercise preferredExercise =
                ExerciseFileLoader.findExerciseByName(
                        matchingExercises,
                        preferredExerciseName
                );

        if (preferredExercise == null) {
            throw new IllegalArgumentException(
                    "The preferred exercise does not match "
                            + "the selected workout options."
            );
        }

        return preferredExercise;
    }

    // Validates the workout selections
    private static void validateSelections(
            ArrayList<String> selectedMuscleGroups,
            String equipmentType) {

        if (selectedMuscleGroups == null
                || selectedMuscleGroups.isEmpty()) {

            throw new IllegalArgumentException(
                    "Select at least one muscle group."
            );
        }

        if (equipmentType == null
                || equipmentType.isBlank()) {

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

        for (String muscleGroup
                : selectedMuscleGroups) {

            if (muscleGroup == null
                    || muscleGroup.isBlank()) {

                throw new IllegalArgumentException(
                        "Muscle groups cannot be empty."
                );
            }
        }
    }
}