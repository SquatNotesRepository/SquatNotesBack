package pl.edu.ug.squat_notes.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.ug.squat_notes.domain.*;
import pl.edu.ug.squat_notes.repository.ExerciseRepository;
import pl.edu.ug.squat_notes.repository.TrainingRepository;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TrainingRestControllerIntegrationTest extends IntegrationTest {

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Test
    public void testCreateTrainingValid() throws Exception {
        this.resetDb();
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Account account = createDefaultAccount().get();
        Exercise ohp = exerciseRepository.findByName("OHP").get();

        SingleSet singleSet = new SingleSet();
        singleSet.setExercise(ohp);
        singleSet.setReps(2);
        singleSet.setRPE(2d);
        singleSet.setReps(2);

        SuperSet superSet = new SuperSet();
        superSet.addSet(singleSet);

        Training training = new Training();
        long dayTime = 1000 * 60 * 60 * 24;
        training.setDate(new Date(System.currentTimeMillis()));
        training.setDifficulty(2);
        training.setName("newTraining");
        training.setUser(account);
        training.addSuperSet(superSet);

        String inputJson = this.mapToJson(training);

        this.mvc.perform(post("/api/user/" + account.getId() + "/training").contentType(MediaType.APPLICATION_JSON).content(inputJson));

        List<Training> found = trainingRepository.findAllByUserId(account.getId());
        assertThat(found).extracting(Training::getName).containsOnly("newTraining");
    }

}
