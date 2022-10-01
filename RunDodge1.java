package org.cis120.Dodge;

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;
import java.io.*;


/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunDodge1 implements Runnable, Serializable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("TOP LEVEL FRAME");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);


        final GameCourt1 court = new GameCourt1(status);
        frame.add(court, BorderLayout.CENTER);

        final Level_Up lu = new Level_Up(status);

        // Main playing area


        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Level 1");
        reset.addActionListener(e -> {
            frame.add(court, BorderLayout.CENTER);
            court.reset();
        });
        control_panel.add(reset);

        final JButton Level1 = new JButton("Level 2");
        Level1.addActionListener(e -> {
            frame.add(lu, BorderLayout.CENTER);
            lu.reset_1();
        });
        control_panel.add(Level1);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> {
            JOptionPane.showMessageDialog(control_panel,
                        "When you open the game, you will be in a level where " +
                                "with every bounce of the" +
                                "first red ball, another red ball is added to the game. \n You " +
                                "have to navigate the square using arrow keys" +
                                "to the original ball. \n" +
                                "To increase the difficulty of the game, click on Level 2. " +
                                "Mushrooms will appear every time" +
                                "that the ball bounces. The object of this level is to touch the " +
                                "ball without touching  \n" +

                                "any of the randomly popping up poisons. You may also reset each " +
                                "level at any time by clicking on Level 1\n" +
                                "or level two respectively. \n"
                );
        });
        control_panel.add(instructions);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
        lu.reset_1();


    }
}