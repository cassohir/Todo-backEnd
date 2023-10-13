package br.com.server.todolist.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.server.todolist.models.UserModel;
import br.com.server.todolist.repository.IUserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public UserModel createUser(UserModel userModel) throws Exception {
        String passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);
        UserModel user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null) throw new Exception("User already exists");
        return this.userRepository.save(userModel);
    }
    public UserModel getByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

}
