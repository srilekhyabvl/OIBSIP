import javax.swing.*;
import java.awt.*;//abstract window toolkit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumber {
// vairables for game

    int minRange;
    int maxRange;
    int score;
    int generatedNumber;
    int maxAttempts;
    int attempts;
//   variables for window
    JFrame frame;
    JLabel title, prompt;
    JTextField text;
    JButton button;

    public GuessTheNumber() {
        minRange = 1;
        maxRange = 100;
        maxAttempts = 10;
        score = 0;
        attempts = 0;
//    set the window
        frame = new JFrame("Guess the Number");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
//    describe add label,textfield and button 
        title = new JLabel("Welcome to Guess the Number Game!!");
        generatedNumber = generateNumber();
        prompt = new JLabel("I have picked a number between " + minRange + " and " + maxRange + ". Try to Guess it! You have " + maxAttempts + " attempts.");
        text = new JTextField(15);
        button = new JButton("Submit!");
//    working on the "button"
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
//    add label,textfield and button to my frame
        frame.add(title);
        frame.add(prompt);
        frame.add(text);
        frame.add(button);
        frame.setSize(500, 300);
        frame.setVisible(true);

    }

    private int generateNumber() {
        Random random = new Random();
        return random.nextInt(maxRange + minRange - 1) + minRange;
    }

    private void check() {
        attempts++;
        int usernumber = Integer.parseInt(text.getText());
        if (generatedNumber == usernumber) {
            score += maxAttempts - attempts + 1;
            JOptionPane.showMessageDialog(frame, "You have guessed it Correctly!!Hurray!");
            JOptionPane.showMessageDialog(frame, "Your Final score is: " + score);
            reset();
        } else if (maxAttempts > attempts) {
            if (generatedNumber < usernumber) {
//          lower
                JOptionPane.showMessageDialog(frame, "Guess the lower number");
            } else {
//              higher 
                JOptionPane.showMessageDialog(frame, "Guess the higher number");

            }
            text.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "You are out of your attempts and Your Final score is: " + score);
            reset();
        }
    }

    private void reset() {
        score = 0;
        attempts = 0;
        text.setText("");
        generatedNumber = generateNumber();
        JOptionPane.showMessageDialog(frame, "Let's play another round");
    }

    public static void main(String[] args) {
        new GuessTheNumber();
    }
}
