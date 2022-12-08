package screens.user_login;


import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Frameworks/Drivers layer

public class LoginScreen extends JFrame implements ActionListener {
    /**
     * The username chosen by the user
     */
    JTextField username = new JTextField(15);
    /**
     * The password
     */
    JPasswordField password = new JPasswordField(15);
    UserLoginController userLoginController;


    /**
     * A window with a title and a JButton.
     */
    public LoginScreen(UserLoginController userLoginController) {
        this.userLoginController = userLoginController;

        JLabel title = new JLabel("Login Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), username);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), password);

        JButton logIn = new JButton("Log in");
        JButton cancel = new JButton("Cancel");

        JPanel buttons = new JPanel();
        buttons.add(logIn);
        buttons.add(cancel);

        logIn.addActionListener(this);
        cancel.addActionListener(this);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(title);
        main.add(usernameInfo);
        main.add(passwordInfo);
        main.add(buttons);
        this.setContentPane(main);

        this.pack();
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {

        System.out.println("Click " + evt.getActionCommand());
        try{
            userLoginController.create(username.getText(),
                    String.valueOf(password.getPassword()));
            JOptionPane.showMessageDialog(this, username.getText() + " log in.");

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
    }
}