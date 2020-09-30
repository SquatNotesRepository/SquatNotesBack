package pl.edu.ug.squat_notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.ug.squat_notes.domain.Account;
import pl.edu.ug.squat_notes.repository.AccountRepository;
import pl.edu.ug.squat_notes.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<Account> findByLoginAndPassword(String login, String password) {
        Optional<Account> userFromDB = accountRepository.findByLoginAndPassword(login, password);
        if (userFromDB.isPresent()) {
            return ResponseEntity.ok(userFromDB.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @Override
    public ResponseEntity<Account> addUser(Account user) {
        Boolean isLoginBusy = accountRepository.existsByLogin(user.getLogin());
        Boolean isEmailBusy = accountRepository.existsByEmail(user.getEmail());
        if (isLoginBusy || isEmailBusy) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            if (Utils.isValid(user)) {
                return ResponseEntity.ok(accountRepository.save(user));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
    }

    @Override
    public ResponseEntity<List<Account>> findAll() {
        List<Account> userList = accountRepository.findAll();
        List<Account> filteredUserList = new ArrayList<>();
        for (Account u : userList) {
            filteredUserList.add(filterUserFields(u));
        }
        return ResponseEntity.ok(filteredUserList);
    }

    private Account filterUserFields(Account user) {
        Account filteredUser = new Account();
        filteredUser.setId(user.getId());
        filteredUser.setLogin(user.getLogin());
        filteredUser.setName(user.getName());
        filteredUser.setEmail(user.getEmail());
        filteredUser.setSurname(user.getSurname());
        filteredUser.setSex(user.getSex());
        filteredUser.setDateOfBirthday(user.getDateOfBirthday());
        filteredUser.setAdvanced(user.getAdvanced());
        return filteredUser;
    }

    @Override
    public ResponseEntity<Account> findById(Long id) {
        Optional<Account> user = accountRepository.findById(id);
        if(user.isPresent()) {
            return ResponseEntity.ok(filterUserFields(user.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
}
