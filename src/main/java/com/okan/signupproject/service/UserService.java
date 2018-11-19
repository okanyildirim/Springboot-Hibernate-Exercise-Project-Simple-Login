package com.okan.signupproject.service;

import com.okan.signupproject.model.User;
import com.okan.signupproject.model.User;

public interface UserService {

     User findUserByEmail(String email);
     void saveUser(User user);
}
