package tic.tac;

import tic.tac.util.SomeExceptionException;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Toe {

    static final Dimension FRAME_SIZE = new Dimension(350, 600);

    static final Color BG_COLOUR = new Color(30, 32, 40);
    static final Color TEXT_COLOUR = new Color(255, 255, 255);

    static JFrame frame = new JFrame(System.getenv("USERNAME") + "'s TacTic"); //Get username and set as title
    static JPanel panel = new JPanel(null);

    static JButton button1 = new JButton();
    static JButton button2 = new JButton();
    static JButton button3 = new JButton();
    static JButton button4 = new JButton();
    static JButton button5 = new JButton();
    static JButton button6 = new JButton();
    static JButton button7 = new JButton();
    static JButton button8 = new JButton();
    static JButton button9 = new JButton();

    static JButton resetButton = new JButton("Reset");

    static String player = "X";

    static JLabel turnText = new JLabel(player + " starts");

    static boolean turn = true;

    final JButton[] ALL_BUTTONS = {
            button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9
    };

    final int[][] ALL_COMBINATIONS = {
            //Rows
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},

            //Columns
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},

            //Diagonals
            {0, 4, 8},
            {2, 4, 6},
    };

    final ImageIcon TIC_X;
    final ImageIcon TIC_O;

    public Toe() { //Draw frame and buttons

        TIC_X = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/tic/tac/res/tic_tacX.png")));
        TIC_O = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/tic/tac/res/tic_tacO.png")));

        frame.setSize(FRAME_SIZE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(TIC_X.getImage());
        frame.add(panel);

        //First row
        button1.setBounds(50, 50, 75, 100);
        button1.setBackground(BG_COLOUR);
        button1.setActionCommand("1");
        panel.add(button1);

        button2.setBounds(130, 50, 75, 100);
        button2.setBackground(BG_COLOUR);
        button2.setActionCommand("2");
        panel.add(button2);

        button3.setBounds(210, 50, 75, 100);
        button3.setBackground(BG_COLOUR);
        button3.setActionCommand("3");
        panel.add(button3);

        //Second row
        button4.setBounds(50, 155, 75, 100);
        button4.setBackground(BG_COLOUR);
        button4.setActionCommand("4");
        panel.add(button4);

        button5.setBounds(130, 155, 75, 100);
        button5.setBackground(BG_COLOUR);
        button5.setActionCommand("5");
        panel.add(button5);

        button6.setBounds(210, 155, 75, 100);
        button6.setBackground(BG_COLOUR);
        button6.setActionCommand("6");
        panel.add(button6);

        //Third row
        button7.setBounds(50, 260, 75, 100);
        button7.setBackground(BG_COLOUR);
        button7.setActionCommand("7");
        panel.add(button7);

        button8.setBounds(130, 260, 75, 100);
        button8.setBackground(BG_COLOUR);
        button8.setActionCommand("8");
        panel.add(button8);

        button9.setBounds(210, 260, 75, 100);
        button9.setBackground(BG_COLOUR);
        button9.setActionCommand("9");
        panel.add(button9);

        resetButton.setBounds(130, 420, 75, 30);
        resetButton.setBackground(BG_COLOUR);
        resetButton.setForeground(TEXT_COLOUR);
        resetButton.addActionListener(_ -> {
            resetAllButtons();
            resetButton.setVisible(false);
        });
        resetButton.setVisible(false);
        panel.add(resetButton);

        turnText.setBounds(135, 380, 75, 40);
        turnText.setForeground(TEXT_COLOUR);
        panel.add(turnText);

        panel.setBackground(BG_COLOUR);

        frame.setVisible(true);

        for (JButton button : ALL_BUTTONS) {
            button.addActionListener(e -> decideTurn(getButtonPressed(e.getActionCommand())));
        }
    }

    private void decideTurn(JButton button) { //Switch player

        turn = !turn;
        button.setEnabled(false);

        turnText.setText("It's " + mark(button) + "'s turn");

        if (checkWinner(turn ? "O" : "X")) {
            turnText.setText(turn ? "O wins!" : "X wins!");
            frame.setIconImage(turn ? TIC_O.getImage() : TIC_X.getImage());
            disableAllButtons();
        }

        if (isBoardFilled()) {
            resetButton.setVisible(true);
        }
    }

    private String mark(JButton button) { //Mark button with the player's symbol
        button.setIcon(turn ? TIC_O : TIC_X);

        System.out.println("Button pressed: " + button.getActionCommand());

        return player = turn ? "X" : "O";
    }

    private JButton getButtonPressed(String actionCommand) { //Returns the button that was clicked

        return switch (actionCommand) {
            case "1" -> button1;
            case "2" -> button2;
            case "3" -> button3;
            case "4" -> button4;
            case "5" -> button5;
            case "6" -> button6;
            case "7" -> button7;
            case "8" -> button8;
            case "9" -> button9;
            default -> throw new SomeExceptionException("Something happened :)");
        };
    }

    private boolean checkWinner(String player) { //Check if either player has got a combination

        ImageIcon icon = player.equals("X") ? TIC_X : TIC_O;

        for (int[] combination : ALL_COMBINATIONS) {
            if (Objects.equals(ALL_BUTTONS[combination[0]].getIcon(), icon) &&
                    Objects.equals(ALL_BUTTONS[combination[1]].getIcon(), icon) &&
                    Objects.equals(ALL_BUTTONS[combination[2]].getIcon(), icon)) { //Looks like C
                return true;
            }
        }

        return false;
    }

    private void resetAllButtons() { //Reset the game...
        for (JButton button : ALL_BUTTONS) {
            button.setIcon(null);
            button.setEnabled(true);
        }
    }

    private void disableAllButtons() { //Make buttons unclickable
        for (JButton button : ALL_BUTTONS) {
            button.setEnabled(false);
        }
    }

    private boolean isBoardFilled() {
        for (JButton button : ALL_BUTTONS) {
            if (button.isEnabled()) {
                return false;
            }
        }

        return true;
    }
}