package pl.edu.ug.squat_notes.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.ug.squat_notes.domain.Account;
import pl.edu.ug.squat_notes.domain.Chatbox;
import pl.edu.ug.squat_notes.domain.Message;
import pl.edu.ug.squat_notes.repository.AccountRepository;
import pl.edu.ug.squat_notes.repository.ChatboxRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ChatboxRestControllerIntegrationTest extends IntegrationTest {

    private static final String DEFAULT_CHATBOX_TITLE = "defaultTitle";


    @Autowired
    private ChatboxRepository chatboxRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testCreateChatboxValid() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Account account = createDefaultAccount().get();

        Message msg = new Message();
        msg.setSentByUser(true);
        msg.setText("testMsg");
        msg.setMessageDate(new Date());
        ArrayList<Message> msgList = new ArrayList<>();
        msgList.add(msg);

        Chatbox chatbox = new Chatbox();
        chatbox.setClosed(false);
        chatbox.setDate(new Date());
        chatbox.setTitle("validTitle123");
        chatbox.setUser(account);
        chatbox.setMessageList(msgList);

        String inputJson = this.mapToJson(chatbox);

        this.mvc.perform(post("/api/chatbox").contentType(MediaType.APPLICATION_JSON).content(inputJson));

        List<Chatbox> found = chatboxRepository.findAll();
        assertThat(found).extracting(Chatbox::getTitle).contains("validTitle123");
    }

    @Test
    public void testCreateChatboxNoMessage() throws Exception {
        resetDb();
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Account account = createDefaultAccount().get();

        ArrayList<Message> msgList = new ArrayList<>();

        Chatbox chatbox = new Chatbox();
        chatbox.setClosed(false);
        chatbox.setDate(new Date());
        chatbox.setTitle("validTitle123");
        chatbox.setUser(account);
        chatbox.setMessageList(msgList);

        int chatBoxCount = chatboxRepository.findAll().size();
        String inputJson = this.mapToJson(chatbox);

        this.mvc.perform(post("/api/chatbox").contentType(MediaType.APPLICATION_JSON).content(inputJson));

        List<Chatbox> found = chatboxRepository.findAll();
        assertThat(found.size()).isEqualTo(chatBoxCount);
    }

    @Test
    public void testUpdateChatboxValid() throws Exception {
        resetDb();
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Account account = createDefaultAccount().get();

        List<Chatbox> chatboxList = chatboxRepository.findAllByUserId(account.getId());
        Chatbox chatbox = new Chatbox();
        for (Chatbox chatb : chatboxList) {
            if (chatb.getTitle().equals(DEFAULT_CHATBOX_TITLE)) {
                chatbox = chatb;
            }
        }
        assertThat(chatbox.getTitle()).isEqualTo(DEFAULT_CHATBOX_TITLE);

        Long tempChatboxId = chatbox.getId();
        int msgCount = chatbox.getMessageList().size();

        Message msg = new Message();
        msg.setSentByUser(true);
        msg.setText("newMessage");
        msg.setMessageDate(new Date());
        chatbox.getMessageList().add(msg);

        String inputJson = this.mapToJson(chatbox);

        this.mvc.perform(patch("/api/chatbox").contentType(MediaType.APPLICATION_JSON).content(inputJson));

        Optional<Chatbox> found = chatboxRepository.findById(tempChatboxId);
        assertThat(found.isPresent()).isEqualTo(true);
        List<Message> messages = found.get().getMessageList();
        assertThat(messages.size()).isEqualTo(msgCount + 1);
        assertThat(messages).extracting(Message::getText).contains("newMessage");
    }

    @Test
    public void testUpdateChatboxEmptyMessage() throws Exception {
        resetDb();
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Account account = createDefaultAccount().get();

        List<Chatbox> chatboxList = chatboxRepository.findAllByUserId(account.getId());
        Chatbox chatbox = new Chatbox();
        for (Chatbox chatb : chatboxList) {
            if (chatb.getTitle().equals(DEFAULT_CHATBOX_TITLE)) {
                chatbox = chatb;
            }
        }
        assertThat(chatbox.getTitle()).isEqualTo(DEFAULT_CHATBOX_TITLE);

        ObjectMapper objectMapper = new ObjectMapper();
        Chatbox tempChatbox = objectMapper.readValue(objectMapper.writeValueAsString(chatbox), Chatbox.class);

        Long tempChatboxId = chatbox.getId();
        int msgCount = chatbox.getMessageList().size();

        Message msg = new Message();
        msg.setSentByUser(true);
        msg.setText("");
        msg.setMessageDate(new Date());
        tempChatbox.getMessageList().add(msg);


        String inputJson = this.mapToJson(tempChatbox);

        this.mvc.perform(patch("/api/chatbox").contentType(MediaType.APPLICATION_JSON).content(inputJson));

        Optional<Chatbox> found = chatboxRepository.findById(tempChatboxId);
        assertThat(found.isPresent()).isEqualTo(true);
        List<Message> messages = found.get().getMessageList();
        assertThat(messages.size()).isEqualTo(msgCount);
    }

    @Before
    public void createDefaultChatbox() {
        Account account = createDefaultAccount().get();

        ArrayList<Message> msgList = new ArrayList<>();

        Message msg = new Message();
        msg.setSentByUser(true);
        msg.setText("testMsg");
        msg.setMessageDate(new Date());
        msgList.add(msg);

        msg = new Message();
        msg.setSentByUser(true);
        msg.setText("testMsg2");
        msg.setMessageDate(new Date());
        msgList.add(msg);

        Chatbox chatbox = new Chatbox();
        chatbox.setClosed(false);
        chatbox.setDate(new Date());
        chatbox.setTitle(DEFAULT_CHATBOX_TITLE);
        chatbox.setUser(account);
        chatbox.setMessageList(msgList);

        chatboxRepository.saveAndFlush(chatbox);
    }

}
