package com.mycompany.registrationandlogin;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class RegistrationAndLoginApp {
    public static HashMap<String, String> userDatabase = new HashMap<>(); // Stores registered users

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }

    // Registration Form
    public static class RegistrationForm extends JFrame {
        public JTextField usernameField, phoneField;
        public JPasswordField passwordField;
        public JComboBox<String> countryCodeComboBox;
        public JLabel messageLabel;

        public RegistrationForm() {
            // Set up the JFrame
            setTitle("Registration Form");
            setSize(600, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

            // Add maximize, minimize, and close buttons
            JButton maximizeButton = new JButton("⬜");
            maximizeButton.setBounds(500, 10, 50, 30);
            add(maximizeButton);
            maximizeButton.addActionListener(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

            JButton minimizeButton = new JButton("_");
            minimizeButton.setBounds(450, 10, 50, 30);
            add(minimizeButton);
            minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

            JButton closeButton = new JButton("X");
            closeButton.setBounds(550, 10, 50, 30);
            add(closeButton);
            closeButton.addActionListener(e -> System.exit(0));

            // Add components
            JLabel titleLabel = new JLabel("Registration Form");
            titleLabel.setBounds(200, 50, 200, 30);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(new Color(255, 105, 180)); // Pink text
            add(titleLabel);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setBounds(50, 100, 150, 25);
            add(usernameLabel);

            usernameField = new JTextField();
            usernameField.setBounds(200, 100, 300, 25);
            add(usernameField);

            JLabel phoneLabel = new JLabel("Phone Number:");
            phoneLabel.setBounds(50, 150, 150, 25);
            add(phoneLabel);

            phoneField = new JTextField();
            phoneField.setBounds(200, 150, 200, 25);
            add(phoneField);

            JLabel countryCodeLabel = new JLabel("Country Code:");
            countryCodeLabel.setBounds(50, 200, 150, 25);
            add(countryCodeLabel);

            // Populate country code dropdown
            String[] countryCodes = {"+27 (South Africa)"};
            countryCodeComboBox = new JComboBox<>(countryCodes);
            countryCodeComboBox.setBounds(200, 200, 300, 25);
            add(countryCodeComboBox);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 250, 150, 25);
            add(passwordLabel);

            passwordField = new JPasswordField();
            passwordField.setBounds(200, 250, 300, 25);
            add(passwordField);

            JButton registerButton = new JButton("Register");
            registerButton.setBounds(250, 300, 100, 30);
            registerButton.setBackground(new Color(255, 182, 193)); // Light pink button
            add(registerButton);

            JButton goToLoginButton = new JButton("Go to Login");
            goToLoginButton.setBounds(250, 350, 100, 30);
            goToLoginButton.setBackground(new Color(255, 182, 193)); // Light pink button
            add(goToLoginButton);

            messageLabel = new JLabel("");
            messageLabel.setBounds(50, 400, 500, 200);
            messageLabel.setForeground(Color.RED);
            messageLabel.setVerticalAlignment(SwingConstants.TOP);
            add(messageLabel);

            // Add ActionListener for the Register button
            registerButton.addActionListener(e -> registerUser());

            // Add ActionListener for the Go to Login button
            goToLoginButton.addActionListener(e -> {
                dispose(); // Close the registration form
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            });
        }

        public void registerUser() {
            String username = usernameField.getText();
            String phone = phoneField.getText();
            String countryCode = (String) countryCodeComboBox.getSelectedItem(); // Get selected country code
            String password = new String(passwordField.getPassword());

            // Boolean validations
            boolean isUsernameValid = username.matches("^[a-zA-Z0-9_]{1,5}$") && username.contains("_"); // Username must contain an underscore and be no more than 5 characters
            boolean isPhoneValid = phone.matches("0\\d{9}"); // South African phone number must start with 0 and be 10 digits
            boolean isPasswordValid = password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$"); // Password must meet the criteria
            boolean isUsernameUnique = !userDatabase.containsKey(username); // Check if username is unique

            // Build success or error messages
            String usernameMessage = isUsernameValid
                ? (isUsernameUnique ? "Username successfully captured." : "Username already exists. Please choose a different username.")
                : "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
            String phoneMessage = isPhoneValid ? "Cellphone number successfully added." : "Cellphone number has been incorrectly added.";
            String passwordMessage = isPasswordValid ? "Password successfully captured." : "Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";

            // Display messages
            if (isUsernameValid && isPhoneValid && isPasswordValid && isUsernameUnique) {
                userDatabase.put(username, password); // Store username and password in the database
                messageLabel.setText("<html>" + usernameMessage + "<br>" + phoneMessage + "<br>" + passwordMessage + "</html>");
                messageLabel.setForeground(new Color(0, 100, 0)); // Dark green
            } else {
                messageLabel.setText("<html>" + usernameMessage + "<br>" + phoneMessage + "<br>" + passwordMessage + "</html>");
                messageLabel.setForeground(Color.RED);
            }
        }
    }

    // Login Form
    public static class LoginForm extends JFrame {
        public JTextField usernameField;
        public JPasswordField passwordField;
        public JLabel messageLabel;

        public LoginForm() {
            // Set up the JFrame
            setTitle("Login Form");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

            // Add maximize, minimize, and close buttons
            JButton maximizeButton = new JButton("⬜");
            maximizeButton.setBounds(400, 10, 50, 30);
            add(maximizeButton);
            maximizeButton.addActionListener(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

            JButton minimizeButton = new JButton("_");
            minimizeButton.setBounds(350, 10, 50, 30);
            add(minimizeButton);
            minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

            JButton closeButton = new JButton("X");
            closeButton.setBounds(450, 10, 50, 30);
            add(closeButton);
            closeButton.addActionListener(e -> System.exit(0));

            // Add components
            JLabel titleLabel = new JLabel("Login Form");
            titleLabel.setBounds(180, 50, 200, 30);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(new Color(255, 105, 180)); // Pink text
            add(titleLabel);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setBounds(50, 100, 150, 25);
            add(usernameLabel);

            usernameField = new JTextField();
            usernameField.setBounds(200, 100, 200, 25);
            add(usernameField);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 150, 150, 25);
            add(passwordLabel);

            passwordField = new JPasswordField();
            passwordField.setBounds(200, 150, 200, 25);
            add(passwordField);

            JButton loginButton = new JButton("Login");
            loginButton.setBounds(200, 200, 100, 30);
            loginButton.setBackground(new Color(255, 182, 193)); // Light pink button
            add(loginButton);

            JButton createAccountButton = new JButton("Create Account");
            createAccountButton.setBounds(200, 250, 150, 30);
            createAccountButton.setBackground(new Color(255, 182, 193)); // Light pink button
            add(createAccountButton);

            messageLabel = new JLabel("");
            messageLabel.setBounds(50, 300, 400, 50);
            messageLabel.setForeground(Color.RED);
            messageLabel.setVerticalAlignment(SwingConstants.TOP);
            add(messageLabel);

            // Add ActionListener for the Login button
            loginButton.addActionListener(e -> loginUser());

            // Add ActionListener for the Create Account button
            createAccountButton.addActionListener(e -> {
                dispose(); // Close the login form
                RegistrationForm registrationForm = new RegistrationForm();
                registrationForm.setVisible(true);
            });
        }

        public void loginUser() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Boolean validations
            boolean isLoginValid = userDatabase.containsKey(username) && userDatabase.get(username).equals(password);

            // Display messages
            String loginMessage = isLoginValid ? "Welcome " + username + " it's great to see you once again." : "Invalid username or password.";
            messageLabel.setText(loginMessage);
            messageLabel.setForeground(isLoginValid ? new Color(0, 100, 0) : Color.RED); // Dark green for success, red for failure
        }
    }
}