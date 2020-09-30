package pl.edu.ug.squat_notes.test;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.ug.squat_notes.domain.Exercise;
import pl.edu.ug.squat_notes.domain.Muscle;
import pl.edu.ug.squat_notes.repository.MuscleRepository;

import java.util.HashSet;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@DataJpaTest
public class MuscleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MuscleRepository muscleRepository;

    @Test
    public void whenFindByName_thenReturnMuscle() {
        Muscle m1 = new Muscle();
        m1.setName("randomName345");
        entityManager.persist(m1);
        entityManager.flush();

        Optional<Muscle> result = muscleRepository.findByName(m1.getName());
        assertTrue(result.isPresent());

        Muscle found = result.get();
        assertEquals(m1, found);
    }
}
