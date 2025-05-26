package com.mycompany.registrationandlogin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RegistrationAndLoginApp {

    // Store users: username -> password
    public static HashMap<String, String> userDatabase = new HashMap<>();

    // Message lists - track sent and stored messages
    public List<Message> sentMessages = new ArrayList<>();
    public List<Message> storedMessages = new ArrayList<>();

    // Gson instance for JSON storage
    public Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Current logged-in username
    public String loggedInUser = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }

    // ----------------------------
    // Registration Form (unchanged)
    // ----------------------------
    public static class RegistrationForm extends JFrame {
        public JTextField usernameField, phoneField;
        public JPasswordField passwordField;
        public JComboBox<String> countryCodeComboBox;
        public JLabel messageLabel;

        public RegistrationForm() {
            setTitle("Registration Form");
            setSize(600, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

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
            registerButton.setBackground(new Color(255, 182, 193)); // Light pink
            add(registerButton);

            JButton goToLoginButton = new JButton("Go to Login");
            goToLoginButton.setBounds(250, 350, 100, 30);
            goToLoginButton.setBackground(new Color(255, 182, 193));
            add(goToLoginButton);

            messageLabel = new JLabel("");
            messageLabel.setBounds(50, 400, 500, 200);
            messageLabel.setForeground(Color.RED);
            messageLabel.setVerticalAlignment(SwingConstants.TOP);
            add(messageLabel);

            registerButton.addActionListener(e -> registerUser());
            goToLoginButton.addActionListener(e -> {
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            });
        }

        public void registerUser() {
            String username = usernameField.getText();
            String phone = phoneField.getText();
            String countryCode = (String) countryCodeComboBox.getSelectedItem();
            String password = new String(passwordField.getPassword());

            boolean isUsernameValid = username.matches("^[a-zA-Z0-9_]{1,5}$") && username.contains("_");
            boolean isPhoneValid = phone.matches("0\\d{9}");
            boolean isPasswordValid = password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$");
            boolean isUsernameUnique = !userDatabase.containsKey(username);

            String usernameMessage = isUsernameValid
                    ? (isUsernameUnique ? "Username successfully captured." : "Username already exists. Please choose a different username.")
                    : "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
            String phoneMessage = isPhoneValid ? "Cellphone number successfully added." : "Cellphone number has been incorrectly added.";
            String passwordMessage = isPasswordValid ? "Password successfully captured." : "Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";

            if (isUsernameValid && isPhoneValid && isPasswordValid && isUsernameUnique) {
                userDatabase.put(username, password);
                messageLabel.setText("<html>" + usernameMessage + "<br>" + phoneMessage + "<br>" + passwordMessage + "</html>");
                messageLabel.setForeground(new Color(0, 100, 0));
            } else {
                messageLabel.setText("<html>" + usernameMessage + "<br>" + phoneMessage + "<br>" + passwordMessage + "</html>");
                messageLabel.setForeground(Color.RED);
            }
        }
    }

    
    // Login Form (unchanged)
   
    public static class LoginForm extends JFrame {
        public JTextField usernameField;
        public JPasswordField passwordField;
        public JLabel messageLabel;

        public LoginForm() {
            setTitle("Login Form");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            getContentPane().setBackground(new Color(173, 216, 230)); // Light blue background

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
            loginButton.setBackground(new Color(255, 182, 193));
            add(loginButton);

            JButton createAccountButton = new JButton("Create Account");
            createAccountButton.setBounds(200, 250, 150, 30);
            createAccountButton.setBackground(new Color(255, 182, 193));
            add(createAccountButton);

            messageLabel = new JLabel("");
            messageLabel.setBounds(50, 300, 400, 50);
            messageLabel.setForeground(Color.RED);
            messageLabel.setVerticalAlignment(SwingConstants.TOP);
            add(messageLabel);

            loginButton.addActionListener(e -> loginUser());
            createAccountButton.addActionListener(e -> {
                dispose();
                RegistrationForm registrationForm = new RegistrationForm();
                registrationForm.setVisible(true);
            });
        }

        public void loginUser() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean isLoginValid = userDatabase.containsKey(username) && userDatabase.get(username).equals(password);

            String loginMessage = isLoginValid ? "Welcome " + username + " it's great to see you once again." : "Invalid username or password.";
            messageLabel.setText(loginMessage);
            messageLabel.setForeground(isLoginValid ? new Color(0, 100, 0) : Color.RED);

            if (isLoginValid) {
                // Open QuickChat on successful login
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    RegistrationAndLoginApp app = new RegistrationAndLoginApp();
                    app.loggedInUser = username;
                    app.runQuickChat();
                });
            }
        }
    }

    // ------------------------
    // QuickChat and Messaging
    // ------------------------
    public void runQuickChat() {
        // Apply pink and blue theme to JOptionPane
        UIManager.put("OptionPane.background", new Color(173, 216, 230)); // Light blue
        UIManager.put("Panel.background", new Color(173, 216, 230));
        UIManager.put("OptionPane.messageForeground", new Color(255, 105, 180)); // Pink text

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.", "Welcome", JOptionPane.INFORMATION_MESSAGE);

        String inputNumMessages;
        int numMessages = 0;

        // Ask for number of messages to send
        while (true) {
            inputNumMessages = JOptionPane.showInputDialog(null, "How many messages do you want to send?", "Number of Messages", JOptionPane.QUESTION_MESSAGE);
            if (inputNumMessages == null) { // User pressed Cancel
                System.exit(0);
            }
            try {
                numMessages = Integer.parseInt(inputNumMessages);
                if (numMessages <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        int messagesSentCount = 0;

        while (true) {
            String menu = "Select an option:\n"
                    + "1) Send Messages\n"
                    + "2) Show recently sent messages\n"
                    + "3) Quit";
            String choice = JOptionPane.showInputDialog(null, menu, "QuickChat Menu", JOptionPane.QUESTION_MESSAGE);
            if (choice == null) {
                continue; // Show menu again
            }

            switch (choice) {
                case "1":
                    // Send messages
                    for (int i = 0; i < numMessages; i++) {
                        String recipient = JOptionPane.showInputDialog(null, "Enter recipient number (with international code):", "Message " + (i + 1), JOptionPane.QUESTION_MESSAGE);
                        if (recipient == null) break;

                        if (!Message.checkRecipientCell(recipient)) {
                            JOptionPane.showMessageDialog(null, "Recipient number must be at most 10 characters and start with '+' sign.", "Invalid Recipient", JOptionPane.ERROR_MESSAGE);
                            i--;
                            continue;
                        }

                        String messageText = JOptionPane.showInputDialog(null, "Enter message (max 250 chars):", "Message " + (i + 1), JOptionPane.QUESTION_MESSAGE);
                        if (messageText == null) break;

                        if (messageText.length() > 250) {
                            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.", "Message Too Long", JOptionPane.ERROR_MESSAGE);
                            i--;
                            continue;
                        }

                        // Generate messageID (10-digit random number)
                        String messageID = generateRandom10DigitID();

                        Message msg = new Message(i + 1, messageID, recipient, messageText);
                        String hash = msg.createMessageHash();

                        // Show message hash
                        JOptionPane.showMessageDialog(null, "Message Hash: " + hash, "Message Hash", JOptionPane.INFORMATION_MESSAGE);

                        // Ask user to Send, Disregard, or Store
                        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
                        int option = JOptionPane.showOptionDialog(null,
                                "Choose an option:",
                                "Send Options",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);

                        if (option == 0) { // Send
                            sentMessages.add(msg);
                            messagesSentCount++;
                            JOptionPane.showMessageDialog(null, "Message sent.\n\n" + msg.printMessageDetails(), "Message Sent", JOptionPane.INFORMATION_MESSAGE);
                        } else if (option == 1) { // Disregard
                            JOptionPane.showMessageDialog(null, "Message disregarded.\nPress 0 to delete message if needed.", "Message Disregarded", JOptionPane.INFORMATION_MESSAGE);
                        } else if (option == 2) { // Store
                            storedMessages.add(msg);
                            storeMessagesToJson();
                            JOptionPane.showMessageDialog(null, "Message stored successfully.", "Message Stored", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "No option selected, message disregarded.", "No Selection", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    // Show total messages sent so far
                    JOptionPane.showMessageDialog(null, "Total messages sent so far: " + messagesSentCount, "Summary", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case "2":
                    // Show recently sent messages - coming soon
                    JOptionPane.showMessageDialog(null, "Coming Soon.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case "3":
                    // Quit
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, or 3.", "Invalid Choice", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Generates a random 10-digit message ID as a String
    public static String generateRandom10DigitID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Stores storedMessages list to JSON file
    public void storeMessagesToJson() {
        try {
            String json = gson.toJson(storedMessages);
            
            System.out.println("Stored messages JSON:\n" + json);
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error storing messages: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    // Message class
    
    public static class Message {
        public int num; // Message number
        public String messageID; // 10-digit unique ID
        public String recipient;
        public String messageContent;

        public Message(int num, String messageID, String recipient, String messageContent) {
            this.num = num;
            this.messageID = messageID;
            this.recipient = recipient;
            this.messageContent = messageContent;
        }

        // Check if message ID is no more than 10 characters
        public static boolean checkMessageID(String id) {
            return id != null && id.length() <= 10;
        }

        // Check recipient number: no more than 10 characters and starts with '+'
        public static boolean checkRecipientCell(String recipient) {
            return recipient != null && recipient.length() <= 10 && recipient.startsWith("+");
        }

        // Creates message hash: first two digits of messageID + ":" + message number + first and last words of message content (caps)
        public String createMessageHash() {
            String firstTwoDigits = messageID.substring(0, 2);
            String firstWord = "";
            String lastWord = "";
            String[] words = messageContent.trim().split("\\s+");
            if (words.length > 0) {
                firstWord = words[0];
                lastWord = words[words.length - 1];
            }
            return (firstTwoDigits + ":" + num + firstWord + lastWord).toUpperCase();
        }

        // Print details for the message in order: MessageID, Message Hash, Recipient, Message
        public String printMessageDetails() {
            return "Message ID: " + messageID + "\n"
                    + "Message Hash: " + createMessageHash() + "\n"
                    + "Recipient: " + recipient + "\n"
                    + "Message: " + messageContent;
        }
    }
}
