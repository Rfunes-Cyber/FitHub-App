package workout;

import java.util.ArrayList;

public class WorkoutTest {

    public static void main(String[] args) {

        // Stores the muscle groups selected by the user
        ArrayList<String> selectedMuscleGroups = new ArrayList<>();

        selectedMuscleGroups.add("Back");
        selectedMuscleGroups.add("Arms");
        selectedMuscleGroups.add("Chest");

        // Generates a gym workout using the selected muscle groups
        Workout workout = WorkoutGenerator.generateWorkout(
                selectedMuscleGroups,
                "Home"
        );

        // Displays the general workout information
        System.out.println("Workout: " + workout.getName());
        System.out.println(
                "Duration: " + workout.getDurationMin() + " minutes"
        );

        System.out.println("Muscle Groups: " + workout.getMuscleGroups());

        System.out.println("\nExercises:");

        // Displays every exercise loaded from the CSV
        for (Exercise exercise : workout.getExercises()) {
            System.out.println(exercise);
        }
    }
}