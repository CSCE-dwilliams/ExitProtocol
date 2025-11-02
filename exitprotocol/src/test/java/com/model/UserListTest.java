package com.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Assert;
import com.model.*;

public class UserListTest {
    @Test
    public void testTesting(){
        assertTrue(true);
    }

    @Test
    public void testValidLogin(){
        Library library = Library.getInstance();
        library.login();
        String email = library.getCurrentUser().getEmail().toLowerCase();
    }

    @Test
    public void test(){

    }
}
