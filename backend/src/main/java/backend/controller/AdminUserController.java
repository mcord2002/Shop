package backend.controller;

import backend.exception.AdminUserNotFoundException;
import backend.exception.UserNotFoundException;
import backend.model.AdminUserModel;

import backend.model.UserModel;
import backend.repository.AdminUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class AdminUserController {

    @Autowired
   private  AdminUserRepository adminUserRepository;

    @PostMapping("/admin")
    public AdminUserModel newAdminUserModel(@RequestBody AdminUserModel newAdminUserModel){
        return adminUserRepository.save(newAdminUserModel);
    }

    //user Login
    @PostMapping("/Alogin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AdminUserModel loginDetails) {
        System.out.println("Login request received:");
        System.out.println("Email: " + loginDetails.getEmail());
        System.out.println("Password: " + loginDetails.getPassword());

        AdminUserModel user = (AdminUserModel) adminUserRepository.findByEmail(loginDetails.getEmail())
                .orElseThrow(() -> new AdminUserNotFoundException("Email not found: " + loginDetails.getEmail()));

        if (user.getPassword().equals(loginDetails.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login Successful");
            response.put("id", user.getId());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials!"));
        }
    }


    //Display
    @GetMapping("/admin")
    List<AdminUserModel> getAllUsers(){
        return adminUserRepository.findAll();
    }
    @GetMapping("/admin/{id}")
    AdminUserModel getUserId(@PathVariable Long id){
        return adminUserRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));

    }
    @PutMapping("/admin/{id}")
    public AdminUserModel updateAdmin(@RequestBody AdminUserModel updatedUser, @PathVariable Long id) {
        return adminUserRepository.findById(id).map(user -> {
            user.setFullname(updatedUser.getFullname());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return adminUserRepository.save(user);
        }).orElseThrow(() -> new AdminUserNotFoundException(id));
    }
    //Delete
    @DeleteMapping("/admin/{id}")
    String deleteProfile(@PathVariable Long id){
        if(!adminUserRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        adminUserRepository.deleteById(id);
        return  "user account "+ id +"delete";
    }
}
