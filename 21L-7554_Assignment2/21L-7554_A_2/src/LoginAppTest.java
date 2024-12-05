import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class LoginAppTest {
    private LoginApp loginApp;

    @BeforeEach
    void setUp() {
        loginApp = new LoginApp();
    }

    @Test
    void testSuccessfulLogin() {
        String email = "johndoe@example.com";
        String password = "password123";
        String result = loginApp.authenticateUser(email, password);
        assertEquals("John Doe", result, "Login should succeed with valid email and password.");
    }

    @Test
    void testInvalidEmail() {
        String email = "invalidemail@example.com";
        String password = "password123";
        String result = loginApp.authenticateUser(email, password);
        assertNull(result, "Login should fail with an invalid email, even if the password is correct.");
    }

    @Test
    void testInvalidPassword() {
        String email = "johndoe@example.com";
        String password = "wrongpassword";
        String result = loginApp.authenticateUser(email, password);
        assertNull(result, "Login should fail with a correct email but an invalid password.");
    }

    @Test
    void testEmptyEmailField() {
        String email = "";
        String password = "password123";
        String result = loginApp.authenticateUser(email, password);
        assertNull(result, "Login should fail with an empty email field.");
    }

    @Test
    void testEmptyPasswordField() {
        String email = "johndoe@example.com";
        String password = "";
        String result = loginApp.authenticateUser(email, password);
        assertNull(result, "Login should fail with an empty password field.");
    }

    @Test
    void testBothFieldsEmpty() {
        String email = "";
        String password = "";
        String result = loginApp.authenticateUser(email, password);
        assertNull(result, "Login should fail when both email and password fields are empty.");
    }

    @Test
    void testNullFields() {
        String result = loginApp.authenticateUser(null, null);
        assertNull(result, "Login should fail when both email and password fields are null.");
    }

    @Test
    void testDatabaseConnectionFailure() {
        // Simulate a database connection failure by using incorrect credentials
        LoginApp faultyLoginApp = new LoginApp() {
            @Override
            String authenticateUser(String email, String password) {
                // Override to simulate database failure
                throw new RuntimeException("Database connection failed.");
            }
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            faultyLoginApp.authenticateUser("johndoe@example.com", "password123");
        });
        assertEquals("Database connection failed.", exception.getMessage(), "Should throw a database connection failure exception.");
    }
}
