package edu.utsa.cs3443.fithubapp.util;

import edu.utsa.cs3443.fithubapp.model.Exercise;
import edu.utsa.cs3443.fithubapp.model.Workout;
import edu.utsa.cs3443.fithubapp.model.WorkoutSession;

import java.util.ArrayList;

public class WorkoutTest {

    public static void main(String[] args) {

        ArrayList<String> selectedMuscleGroups = new ArrayList<>();

        selectedMuscleGroups.add("Back");
        selectedMuscleGroups.add("Arms");
        selectedMuscleGroups.add("Chest");

        // Generates a home workout
        Workout workout = WorkoutGenerator.generateWorkout(
                selectedMuscleGroups,
                "Home"
        );

        System.out.println("Workout: " + workout.getName());
        System.out.println(
                "Duration: "
                        + workout.getDurationMin()
                        + " minutes"
        );

        System.out.println(
                "Muscle Groups: "
                        + workout.getMuscleGroups()
        );

        System.out.println("\nExercises:");

        for (Exercise exercise : workout.getExercises()) {
            System.out.println(exercise);
        }

        // Starts and tests the workout session
        WorkoutSession session = new WorkoutSession(workout);

        System.out.println("\nStarting Workout Session:");
        System.out.println(session);

        session.completeSet();

        System.out.println("\nAfter completing one set:");
        System.out.println(session);

        System.out.println(
                "Progress: "
                        + session.getProgressPercent()
                        + "%"
        );
    }
}