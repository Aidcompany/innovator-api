package com.innovator.innovator.services;

import com.innovator.innovator.ExcelHelper;
import com.innovator.innovator.models.User;
import com.innovator.innovator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class ExecService {
    private UserRepository userRepository;

    public ByteArrayInputStream load() {
        List<User> users = userRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.usersToExcel(users);
        return in;
    }
}
