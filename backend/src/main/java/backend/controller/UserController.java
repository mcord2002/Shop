package backend.controller;

import backend.exception.UserNotFoundException;
import backend.model.UserModel;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public UserModel newUserModel(@RequestBody UserModel newUserModel){
        return userRepository.save(newUserModel);
    }

    //user Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login (@RequestBody UserModel loginDetails){
        UserModel user = userRepository.findByEmail(loginDetails.getEmail()).orElseThrow(()-> new UserNotFoundException("Email not found : "+loginDetails.getEmail()));

        if (user.getPassword().equals(loginDetails.getPassword())){
            Map<String,Object> response = new HashMap<>();
            response.put("message","Login Successfull");
            response.put("id",user.getId());
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","invalid credentials !"));
        }
    }
    //Display
    @GetMapping("/user")
    List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/user/{id}")
    UserModel getUserId(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));

    }

    //Update
    @PutMapping("user/{id}")
    UserModel updateProfile(@RequestBody UserModel newUserModel,@PathVariable Long id){
        return userRepository.findById(id).map(userModel -> {
            userModel.setFullname(newUserModel.getFullname());
            userModel.setEmail(newUserModel.getEmail());
            userModel.setPassword(newUserModel.getPassword());
            userModel.setPhone(newUserModel.getPhone());
            return  userRepository.save(userModel);
        }).orElseThrow(()-> new UserNotFoundException(id));
    }

    //Delete
    @DeleteMapping("/user/{id}")
    String deleteProfile(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return  "user account "+ id +"delete";
    }



}
