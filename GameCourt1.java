package org.cis120.Dodge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.io.*;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
public class GameCourt1 extends JPanel implements LevelUp {

    // the state of the game logic
    private Square1 square; // the Black Square, keyboard control
    private Circle1 snitch; // the Golden Snitch, bounces
    private Poison1 poison; // the Poison Mushroom, doesn't move
    private boolean win;

    private boolean playing = false; // whether the game is running
    private final JLabel status; // Current status text, i.e. "Running..."
    private double score;

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 300;
    public static final int SQUARE_VELOCITY = 4;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    private static ArrayList<Circle1> balls = new ArrayList<Circle1>();

    public GameCourt1(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    square.setVx(-SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    square.setVx(SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    square.setVy(SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    square.setVy(-SQUARE_VELOCITY);
                }
            }

            public void keyReleased(KeyEvent e) {
                square.setVx(0);
                square.setVy(0);
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        square = new Square1(COURT_WIDTH, COURT_HEIGHT, Color.BLACK);
        poison = new Poison1(COURT_WIDTH, COURT_HEIGHT, 130, 130);
        snitch = new Circle1(COURT_WIDTH, COURT_HEIGHT, Color.RED);
        score = 0;
        win = false;
        balls.removeAll(balls);
        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */

    void tick() {

        if (playing) {

            AddObjectsAfterBounce();
            score += 0.1;

            square.move();
            snitch.move();

            if (snitch.DoesHitWall()) {
                balls.add(new Circle1(COURT_WIDTH, COURT_HEIGHT, Color.RED));
            }

            // make the snitch bounce off walls...
            snitch.bounce(snitch.hitWall());
            // ...and the mushroom
            snitch.bounce(snitch.hitObj(poison));



            // check for the game end conditions
            if (square.intersects(poison)) {
                playing = false;
                status.setText("You lose! ");
                status.repaint();
                win = false;
            } else if (square.intersects(snitch)) {
                playing = false;
                status.setText("You Win! Score: " + score);
                status.repaint();
                win = true;
            }
            try {
                if (win) {
                    saveHighScore(score);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // update the display
            repaint();
        }
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        square.draw(g);
        poison.draw(g);
        snitch.draw(g);
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

    @Override
    public void AddObjectsAfterBounce() {
        for (int i = 0; i < balls.size(); i++) {
            // requestFocusInWindow();
            balls.get(i).move();

            // make the snitch bounce off walls...
            balls.get(i).bounce(balls.get(i).hitWall());
            // ...and the mushroom
            balls.get(i).bounce(balls.get(i).hitObj(poison));
        }
    }

    public Double getHighScore() throws IOException {
        FileReader file = null;
        BufferedReader reader = null;
        try {
            file = new FileReader("highscore.txt");
            reader = new BufferedReader(file);
            String l = reader.readLine();
            String[] arr = l.split(":");
            return Double.parseDouble(arr[1]);
        } catch (IOException e){
            return 1.0;
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }

    }




    public void saveHighScore(double score) throws IOException {
        //System.out.println("hello");
        if( score < getHighScore()) {
            String name = JOptionPane.showInputDialog
                    ("High Score:" + score);
            String highscore = "High Score:" + score;
            //System.out.println(highscore);

            File score1 = new File("highscore.txt");
            if(!score1.exists()) {
                try {
                    score1.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            FileWriter write = new FileWriter("highscore.txt", false);
            BufferedWriter writer = new BufferedWriter(write);
            try {
                writer.write(highscore);
                writer.flush();
                writer.close();
            }
            catch (Exception e){

            }
            finally {
                try {
                    if(write != null) {
                        write.close();
                    }
                }
                catch (IOException e) {
                    throw e;
                }
            }
        }
    }

}