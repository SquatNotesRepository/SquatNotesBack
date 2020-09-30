package pl.edu.ug.squat_notes.service;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.edu.ug.squat_notes.domain.*;
import pl.edu.ug.squat_notes.repository.*;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

@Component
public class DbInit {

    private ExerciseRepository exerciseRepository;

    private TrainingRepository trainingRepository;

    private AccountRepository accountRepository;

    private AccountService accountService;

    private MuscleRepository muscleRepository;

    private ChatboxRepository chatBoxRepository;

    private MessageRepository messageRepository;

    private TrainingService trainingService;

    @Autowired
    public DbInit(ExerciseRepository exerciseRepository, TrainingRepository trainingRepository, AccountRepository accountRepository,
                  AccountService accountService, MuscleRepository muscleRepository, ChatboxRepository chatBoxRepository,
                  MessageRepository messageRepository, TrainingService trainingService) {
        this.exerciseRepository = exerciseRepository;
        this.trainingRepository = trainingRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.muscleRepository = muscleRepository;
        this.chatBoxRepository = chatBoxRepository;
        this.messageRepository = messageRepository;
        this.trainingService = trainingService;
    }

    @PostConstruct
    public void postConstruct() {
        if (accountRepository.findAll().isEmpty()) {
            //init user
            Account user = new Account();
            user.setName("Jeffrey");
            user.setDateOfBirthday(new Date(90, 4, 20));
            user.setEmail("jeff@test.pl");
            user.setLogin("TEST123");
            user.setPassword("TEST123!");
            user.setSex("male");
            user.setSurname("Tester");
            user.setType(0);
            accountRepository.save(user);
            //init support
            Account support = new Account();
            support.setName("Supp");
            support.setDateOfBirthday(new Date(90, 4, 21));
            support.setEmail("supp@test.pl");
            support.setLogin("SUPPORT123");
            support.setPassword("SUPPORT123!");
            support.setSex("male");
            support.setSurname("Sup");
            support.setType(1);
            accountRepository.save(support);

            Chatbox chatBox = new Chatbox();
            chatBox.setUser(user);
            chatBox.setMessageList(new ArrayList<Message>());
            chatBox.setTitle("mamma mia");
            chatBox.setDate(new Date(20, 4, 21));
            chatBox.setClosed(true);

            Message message = new Message();
            message.setMessageDate(new Date(20, 4, 21));
            String mammaMia = "[Verse 1]\n" +
                    "I've been cheated by you since I don't know when\n" +
                    "So I made up my mind, it must come to an end\n" +
                    "Look at me now, will I ever learn?\n" +
                    "I don't know how, but I suddenly lose control\n" +
                    "There's a fire within my soul";
            message.setText(mammaMia);
            message.setSentByUser(true);
            chatBox.addMessage(message);

            message = new Message();
            message.setMessageDate(new Date(20, 4, 22));
            mammaMia = "ok";
            message.setText(mammaMia);
            message.setSentByUser(false);
            chatBox.addMessage(message);

            message = new Message();
            message.setMessageDate(new Date(20, 4, 23));
            mammaMia = "[Pre-Chorus]\n" +
                    "Just one look and I can hear a bell ring\n" +
                    "One more look and I forget everything, woah";
            message.setText(mammaMia);
            message.setSentByUser(true);
            chatBox.addMessage(message);

            message = new Message();
            message.setMessageDate(new Date(20, 4, 24));
            mammaMia = "stop";
            message.setText(mammaMia);
            message.setSentByUser(false);
            chatBox.addMessage(message);

            message = new Message();
            message.setMessageDate(new Date(20, 4, 24));
            mammaMia = "pls";
            message.setText(mammaMia);
            message.setSentByUser(false);
            chatBox.addMessage(message);

            message = new Message();
            message.setMessageDate(new Date(20, 4, 25));
            mammaMia = "[Chorus]\n" +
                    "Mamma mia, here I go again\n" +
                    "My my, how can I resist you?\n" +
                    "Mamma mia, does it show again?\n" +
                    "My my, just how much I've missed you\n" +
                    "Yes, I've been brokenhearted\n" +
                    "Blue since the day we parted\n" +
                    "Why, why did I ever let you go?\n" +
                    "...";
            message.setText(mammaMia);
            message.setSentByUser(true);
            chatBox.addMessage(message);

            message = new Message();
            message.setMessageDate(new Date(20, 4, 26));
            mammaMia = "that's enough, im closing this chatroom";
            message.setText(mammaMia);
            message.setSentByUser(false);
            chatBox.addMessage(message);

            chatBoxRepository.save(chatBox);

            chatBox = new Chatbox();
            chatBox.setUser(user);
            chatBox.setMessageList(new ArrayList<Message>());
            chatBox.setTitle("przykładowy otwarty temat 1");
            chatBox.setDate(new Date(20, 4, 23));
            chatBox.setClosed(false);
            chatBoxRepository.save(chatBox);


            chatBox = new Chatbox();
            chatBox.setUser(user);
            chatBox.setMessageList(new ArrayList<Message>());
            chatBox.setTitle("przykładowy zamknięty temat");
            chatBox.setDate(new Date(20, 4, 22));
            chatBox.setClosed(true);
            chatBoxRepository.save(chatBox);


            //init muscles
            Muscle chest = new Muscle("Chest");
            muscleRepository.save(chest);
            Muscle legs = new Muscle("Legs");
            muscleRepository.save(legs);
            Muscle arms = new Muscle("Arms");
            muscleRepository.save(arms);
            Muscle shoulders = new Muscle("Shoulders");
            muscleRepository.save(shoulders);
            Muscle back = new Muscle("Back");
            muscleRepository.save(back);
            Muscle abs = new Muscle("ABS");
            muscleRepository.save(abs);

            //init exercises
            Exercise ohp = new Exercise("OHP");
            ohp.addTargetMuscle(shoulders);
            ohp.addTargetMuscle(arms);
            ohp.addTargetMuscle(chest);
            exerciseRepository.save(ohp);
            Exercise squat = new Exercise("Squat");
            squat.addTargetMuscle(legs);
            exerciseRepository.save(squat);
            Exercise deadlift = new Exercise("Deadlift");
            deadlift.addTargetMuscle(legs);
            exerciseRepository.save(deadlift);
            Exercise benchPress = new Exercise("Bench press");
            benchPress.addTargetMuscle(shoulders);
            benchPress.addTargetMuscle(arms);
            benchPress.addTargetMuscle(chest);
            exerciseRepository.save(benchPress);

            String[] trainingNamesTmp = {"Push day", "Pull day", "Chest day", "Back day", "Arms day"};
            //init training with sets
            for (int u = 0; u < 0; u++) {
                Faker faker = new Faker();
                user = new Account();
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                user.setName(firstName);
                user.setSurname(lastName);
                user.setDateOfBirthday(faker.date().birthday(16, 50));
                user.setEmail(firstName + "." + lastName + "@gmail.com");
                user.setLogin(firstName);
                user.setPassword(firstName + "123!");
                user.setSex((u % 2 == 0 ? "male" : "female"));
                if (accountService.addUser(user).getStatusCode() != HttpStatus.OK) {
                    continue;
                }
                Random rand = new Random();
                int lvlTmp = 1;
                if (user.getSex() == "male") {
                    lvlTmp = rand.nextInt(10);
                } else {
                    lvlTmp = rand.nextInt(5);
                }
                for (int i = 0; i < 20; i++) {
                    Training training = new Training();
                    long dayTime = 1000 * 60 * 60 * 24;
                    training.setDate(new Date(System.currentTimeMillis() - dayTime * 100 + dayTime * i * 3));
                    training.setName(trainingNamesTmp[i % trainingNamesTmp.length]);
                    training.setDifficulty(rand.nextInt(10) + 1);
                    training.setUser(user);
                    for (int j = 0; j < 5; j++) {
                        SuperSet superSet = new SuperSet();
                        SingleSet singleSet = new SingleSet();
                        singleSet.setExercise(ohp);
                        singleSet.setReps(rand.nextInt(10 - 5) + 5);
                        singleSet.setRPE(Math.round((5d + 5d * rand.nextDouble()) * 2) / 2d);
                        singleSet.setWeight(lvlTmp * 8d + i * rand.nextDouble());
                        singleSet.setSuperSet(superSet);
                        superSet.addSet(singleSet);
                        singleSet = new SingleSet();
                        singleSet.setExercise(squat);
                        singleSet.setReps(3);
                        singleSet.setRPE(7d);
                        singleSet.setWeight(lvlTmp * 30d + i * rand.nextDouble());
                        singleSet.setSuperSet(superSet);
                        superSet.addSet(singleSet);
                        singleSet = new SingleSet();
                        singleSet.setExercise(benchPress);
                        singleSet.setReps(6);
                        singleSet.setRPE(9.5);
                        singleSet.setWeight(lvlTmp * 15d + i * rand.nextDouble());
                        singleSet.setSuperSet(superSet);
                        superSet.addSet(singleSet);
                        singleSet = new SingleSet();
                        singleSet.setExercise(deadlift);
                        singleSet.setReps(1);
                        singleSet.setRPE(10d);
                        singleSet.setWeight(lvlTmp * 40d + i * rand.nextDouble());
                        singleSet.setSuperSet(superSet);
                        superSet.addSet(singleSet);

                        training.addSuperSet(superSet);
                        accountRepository.save(user);
                        if (i == 19 && j == 4) {
                            // aby obliczyc poziom zaawansowania
                            trainingService.addTraining(training);
                        } else {
                            // aby szybciej startowal
                            trainingRepository.save(training);
                        }
                    }
                }
            }
        }
    }
}
