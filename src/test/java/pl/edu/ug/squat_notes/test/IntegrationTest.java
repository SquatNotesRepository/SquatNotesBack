package pl.edu.ug.squat_notes.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pl.edu.ug.squat_notes.SquatNotesApplication;
import pl.edu.ug.squat_notes.domain.Account;
import pl.edu.ug.squat_notes.domain.Chatbox;
import pl.edu.ug.squat_notes.domain.Exercise;
import pl.edu.ug.squat_notes.repository.AccountRepository;
import pl.edu.ug.squat_notes.repository.ChatboxRepository;
import pl.edu.ug.squat_notes.service.DbInit;

import java.util.Date;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SquatNotesApplication.class)
@WebAppConfiguration
@Transactional
abstract public class IntegrationTest {

    protected static final String DEFAULT_LOGIN_PWD = "bob";
    protected static final String DEFAULT_CHATBOX_TITLE = "defaultTitle";

    MockMvc mvc;

    @Autowired
    private ChatboxRepository chatboxRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    DbInit dbInit;

    @Before
    public void resetDb() {
        dbInit.postConstruct();
    }


    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected Optional<Account> createDefaultAccount() {
        if (!(getDefaultAccount().isPresent())) {
            Account acc = new Account();
            acc.setName("t");
            acc.setEmail("t@t.t");
            acc.setSurname("t");
            acc.setLogin(DEFAULT_LOGIN_PWD);
            acc.setPassword(DEFAULT_LOGIN_PWD);
            acc.setDateOfBirthday(new Date());
            acc.setSex("t");

            accountRepository.saveAndFlush(acc);
        }
        return getDefaultAccount();
    }

    protected Optional<Account> getDefaultAccount() {
        return accountRepository.findByLoginAndPassword(DEFAULT_LOGIN_PWD, DEFAULT_LOGIN_PWD);
    }

    protected String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}
