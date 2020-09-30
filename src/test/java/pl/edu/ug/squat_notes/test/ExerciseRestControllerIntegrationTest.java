package pl.edu.ug.squat_notes.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.ug.squat_notes.domain.*;
import pl.edu.ug.squat_notes.repository.ExerciseRepository;
import pl.edu.ug.squat_notes.repository.MuscleRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ExerciseRestControllerIntegrationTest extends IntegrationTest {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    MuscleRepository muscleRepository;

    @Test
    public void testCreateExerciseValid() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Account account = createDefaultAccount().get();
        Muscle muscle = muscleRepository.findByName("Chest").get();

        Exercise exercise = new Exercise();
        exercise.setAuthor(account);
        exercise.setName("newExercise");
        exercise.addTargetMuscle(muscle);

        String inputJson = this.mapToJson(exercise);

        this.mvc.perform(post("/api/exercise").contentType(MediaType.APPLICATION_JSON).content(inputJson));

        List<Exercise> found = exerciseRepository.findAll();
        assertThat(found).extracting(Exercise::getName).contains("newExercise");
    }


}
