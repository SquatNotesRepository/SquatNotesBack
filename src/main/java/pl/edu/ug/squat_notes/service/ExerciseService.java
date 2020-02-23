package pl.edu.ug.squat_notes.service;

import org.springframework.http.ResponseEntity;
import pl.edu.ug.squat_notes.domain.Exercise;

import java.util.List;

public interface ExerciseService {

    List<Exercise> findAllExercises();

    ResponseEntity<List<Exercise>>  findAllExercisesByMuscleName(String muscleName);

    ResponseEntity<Exercise> addExercise(Exercise exercise);

    ResponseEntity<Exercise> updateExercise(Exercise exercise);

    ResponseEntity<Exercise> findById(Long id);

    ResponseEntity deleteExercise(Long id);
}