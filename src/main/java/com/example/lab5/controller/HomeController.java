package com.example.lab5.controller;

import com.example.lab5.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private List<User> userList = new ArrayList<>();

    // Khởi tạo danh sách User
    public HomeController() {
        userList.add(new User("1", "Nguyen Van A", "it22ab@example.com"));
        userList.add(new User("2", "Le Van B", "it22ab@example.com"));
        userList.add(new User("3", "Nguyen Van C", "it22ab@example.com"));
        userList.add(new User("4", "Nguyen Van D", "it22ab@example.com"));
        userList.add(new User("5", "Nguyen Van E", "it22ab@example.com"));
        userList.add(new User("6", "Nguyen Van F", "it22ab@example.com"));
        userList.add(new User("7", "Nguyen Van G", "it22ab@example.com"));
    }

    // Bắt GET /home request và trả về view
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "John");
        return "index";  // Tên của View
    }

    // Trả về danh sách User
    @GetMapping("/users")
    @ResponseBody
    public List<User> getUserList() {
        return userList;
    }

    // Trả về một User cụ thể theo ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String userId) {
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                return ResponseEntity.status(200).body(user);
            }
        }
        return ResponseEntity.status(404).body(null);  // Trả về lỗi 404 nếu không tìm thấy
    }
    //
    @PostMapping
}
