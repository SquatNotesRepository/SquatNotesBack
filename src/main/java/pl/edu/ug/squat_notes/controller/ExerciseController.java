package pl.edu.ug.squat_notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.ug.squat_notes.domain.Exercise;
import pl.edu.ug.squat_notes.repository.ExerciseRepository;
import pl.edu.ug.squat_notes.service.ExerciseService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class ExerciseController {

    private ExerciseService exerciseService;
    private ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, ExerciseRepository exerciseRepository) {
        this.exerciseService = exerciseService;
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("/api/exercise")
    public List<Exercise> getExercises() {
        return exerciseRepository.findAll();
    }


    @GetMapping("/api/exercise/{id}")
    ResponseEntity<Exercise> getExercise(@PathVariable Long id) {
        return exerciseService.findById(id);
    }

    @GetMapping("/api/exercises/{muscleName}")
    public ResponseEntity<List<Exercise>> getExercisesByMuscleId(@PathVariable String muscleName) {
        return exerciseService.findAllExercisesByMuscleName(muscleName);
    }

    @GetMapping("/api/exercises/user/{id}")
    public ResponseEntity<List<Exercise>> getExercisesByUser(@PathVariable Long id) {
        return exerciseService.findAllExercisesByUserId(id);
    }

    @GetMapping("/api/exercises/default")
    public ResponseEntity<List<Exercise>> getDefaultExercises() {
        return exerciseService.findAllExercisesDefault();
    }

    @PostMapping("/api/exercise")
    ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise) {
        return exerciseService.addExercise(exercise);
    }

    @PutMapping("/api/exercise/{id}")
    ResponseEntity<Exercise> updateExercise(@RequestBody Exercise exercise, @PathVariable Long id) {
        return exerciseService.updateExercise(exercise);
    }

    @DeleteMapping("/api/exercise/{id}")
    ResponseEntity deleteExercise(@PathVariable Long id) {
        return exerciseService.deleteExercise(id);
    }

}
