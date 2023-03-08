package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

// DONT REALLY NEED TO CHANGE ANYTHING IN HERE. MAYBE COULD ADD THINGS BUT THATS IT. PROB NOT NEEDED

// USE SQL SELECT WITH JOINS TO FIND ACCOUNT ID, DONT ADD NUMBERS TO GET IT

public interface UserDao {

    List<User> findAll(); // dont need to use

    User getUserById(int id); // dont need to use

    User findByUsername(String username); // dont need to use

    int findIdByUsername(String username); // will use, principal object username, has that .getName() method

    boolean create(String username, String password); // dont need to use
}
