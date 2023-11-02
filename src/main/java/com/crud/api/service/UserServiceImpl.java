package com.crud.api.service;

import javax.validation.OverridesAttribute;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.api.entity.User;
import com.crud.api.entity.UserModel;
import com.crud.api.exceptions.ItemExistsException;
// import com.crud.api.exceptions.ResourceNotFoundException;
import com.crud.api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override

    public User createUser(UserModel user) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
            throw new ItemExistsException("This user Already Exist. You can not create new User");
        } else {
            User newUser = new User();
            BeanUtils.copyProperties(user, newUser);
            newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
            return userRepository.save(newUser);

        }

    }

    @Override

    public Boolean checkUserExist(UserModel user) {
         return Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()));
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email" + email));
    }

    @Override
    public void deleteUser() {
        User existingUser = getUser();
        userRepository.delete(existingUser);
    }

    @Override
    public User updatUser(UserModel user) {
        User existingUser = getUser();
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(
                user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        existingUser.setAge(user.getAge() != null ? user.getAge() : existingUser.getAge());
        return userRepository.save(existingUser);
    }

    @Override
    public User getUser() {
        Long userId = getLoggedInUser().getId();
        return userRepository.findById(userId).orElseThrow();
    }

}
