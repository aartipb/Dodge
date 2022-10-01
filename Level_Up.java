package org.cis120.Dodge;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.io.*;

public class Level_Up extends JPanel implements LevelUp {
    // the state of the game logic
    private Square1 sq; // the Black Square, keyboard control
    private Circle1 snitch; // the Golden Snitch, bounces
    private Poison1 po; // the Poison Mushroom, doesn't move
    private double score;
    //private Circle_1 red_pl; //what we want the player to change colors to back and forth
    private boolean playing = false; // whether the game is running
    private final JLabel status; // Current status text, i.e. "Running..."
    private boolean win;

    private static ArrayList<Poison1> invaders = new ArrayList<Poison1>();

    public Level_Up(JLabel status) {
        setBorder(BorderFactory.createLineBorder(Color.RED));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        Timer timer = new Timer(35, e -> tik());
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
                    sq.setVx(-4);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    sq.setVx(4);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    sq.setVy(4);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    sq.setVy(-4);
                }
            }

            public void keyReleased(KeyEvent e) {
                sq.setVx(0);
                sq.setVy(0);
            }
        });

        this.status = status;
    }


    public void reset_1() {
        sq = new Square1(300, 300, Color.BLACK);
        po = new Poison1(300, 300, (int) (Math.random() * (300)), (int) (Math.random() * (300)));
        snitch = new Circle1(300, 300, Color.YELLOW);
        score = 0;
        invaders.removeAll(invaders);
        playing = true;
        status.setText("Running...");
        win = false;
        score = 0;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    void tik() {
        if (playing) {
            //System.out.println("hello");

            sq.move();
            snitch.move();
            //change();

            AddObjectsAfterBounce();
            score += 0.1;

            // make the snitch bounce off walls...
            snitch.bounce(snitch.hitWall());
            // ...and the mushroom
            snitch.bounce(snitch.hitObj(po));


            // check for the game end conditions
            for (int i = 0; i < invaders.size(); i++) {
                if (sq.intersects(po) || sq.intersects(invaders.get(i))) {
                    playing = false;
                    status.setText("You lose! ");
                    status.repaint();
                    win = false;
                }
            }
            if (sq.intersects(snitch)) {
                playing = false;
                status.setText("You Win! Score: " + score);
                status.repaint();
                win = true;
            }
            try {
                if (win) {
                    // System.out.println("hello");
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
            sq.draw(g);
            po.draw(g);
            snitch.draw(g);
            for (int i = 0; i < invaders.size(); i++) {
                invaders.get(i).draw(g);
            }
        }

        @Override
        public void AddObjectsAfterBounce() {
            if (snitch.DoesHitWall()) {
                invaders.add(new Poison1(300, 300, (int) (Math.random() * (300)), (int) (Math.random() * (300))));
                //repaint();
            }
        }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }

    public Double getHighScore() throws IOException {
        //System.out.println("hello1");
        FileReader file = null;
        BufferedReader reader = null;
        try {
            file = new FileReader("highscores.txt");
            reader = new BufferedReader(file);
            String l = reader.readLine();
            String[] arr = l.split(":");
           // System.out.println(arr[1]);
            return Double.parseDouble(arr[1]);
        }
        catch (IOException e){
            return -1.0;
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
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
           // System.out.println(highscore);

            File score1 = new File("highscores.txt");
            if (!score1.exists()) {
                try {
                    score1.createNewFile();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }

            FileWriter write = new FileWriter("highscores.txt", false);
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
                    if (write != null) {
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

