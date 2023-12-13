package org.liny.Tests;

import org.junit.jupiter.api.Test;
import org.liny.Managers.PasswordManager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordManagerTest {

    private static final String TEST_DB_URL = "jdbc:mysql://d4.aurorix.net:3306/s11944_anarchy";
    private static final String TEST_DB_USER = "u11944_HjaF3FL0R2";
    private static final String TEST_DB_PASSWORD = "SbDwFqC@+iD5erM7QAYHE@Jo";

    @Test
    public void testGetPassword() {
        String playerName = "Krismus";

        String password = PasswordManager.getPassword(playerName);

        String expectedPassword = null;

        assertEquals(expectedPassword, password);
    }

    @Test
    public void testGetPasswords() {
        printPassword("m4estro48");
        printPassword("membraner");
        printPassword("Haska1233");
    }

    private static void printPassword(String playerName) {
        String password = PasswordManager.getPassword(playerName);
        System.out.println("Player: " + playerName + ", Password: " + password);

    }
}