package pl.edu.ug.squat_notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.ug.squat_notes.domain.ChartPoint;
import pl.edu.ug.squat_notes.domain.Training;
import pl.edu.ug.squat_notes.repository.SingleSetRepository;
import pl.edu.ug.squat_notes.repository.SuperSetRepository;
import pl.edu.ug.squat_notes.repository.TrainingRepository;
import pl.edu.ug.squat_notes.service.ProgressService;
import pl.edu.ug.squat_notes.service.SingleSetService;
import pl.edu.ug.squat_notes.service.SuperSetService;
import pl.edu.ug.squat_notes.service.TrainingService;

import java.util.List;

/**
 * Rest controller for user in training aspects
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserTrainingController {

    private TrainingRepository trainingRepository;
    private TrainingService trainingService;
    private ProgressService progressService;
    private SuperSetRepository superSetRepository;
    private SuperSetService superSetService;
    private SingleSetRepository singleSetRepository;
    private SingleSetService singleSetService;

    @Autowired
    public UserTrainingController(TrainingRepository trainingRepository, TrainingService trainingService, SuperSetRepository superSetRepository, SuperSetService superSetService, SingleSetRepository singleSetRepository, SingleSetService singleSetService, ProgressService progressService) {
        this.trainingRepository = trainingRepository;
        this.trainingService = trainingService;
        this.superSetRepository = superSetRepository;
        this.superSetService = superSetService;
        this.singleSetRepository = singleSetRepository;
        this.singleSetService = singleSetService;
        this.progressService = progressService;
    }

    @GetMapping("/api/user/{id}/training")
    public ResponseEntity<List<Training>> getTrainings(@PathVariable Long id) {
        return trainingService.findAllByUserId(id);
    }

    @PostMapping("/api/user/{id}/training")
    ResponseEntity<Training> addTraining(@PathVariable Long id, @RequestBody Training training) {
       /* if (training.getUser().getId() == null || !training.getUser().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }*/
        return trainingService.addTraining(training);
    }

    @GetMapping("/api/user/{idUser}/progress")
    public ResponseEntity<List<ChartPoint>> getTrainings(@PathVariable Long idUser, @RequestParam String exerciseName) {
        return progressService.getChartByExerciseNameAndUserId(exerciseName, idUser);
    }

    @PutMapping("/api/user/{id}/training")
    ResponseEntity<Training> putTraining(@PathVariable Long id, @RequestBody Training training) {
        if (training.getUser().getId() == null || !training.getUser().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return trainingService.deleteTraining(training);
    }
}
