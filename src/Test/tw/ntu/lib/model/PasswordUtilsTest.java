package tw.ntu.lib.model;

import org.junit.Test;

public class PasswordUtilsTest {


    @Test
    public void protectUserPassword() {
        String myPassword = "password";

        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);

        // Print out protected password
        System.out.println("My secure password = " + mySecurePassword);
        System.out.println("Salt value = " + salt);

    }

}