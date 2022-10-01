package org.cis120.Dodge;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;


public class DodgeTest {
    private Circle1 snitch;
    private ArrayList<Circle1> balls;
    private Poison1 mushroom;
    private Square1 square;

    public DodgeTest() {
        snitch = new Circle1(300,300, Color.BLACK);
        balls = new ArrayList<Circle1>();
        balls.add(snitch);
        balls.add(new Circle1(300, 300, Color.BLACK));
        square = new Square1(300,300, Color.BLACK);
    }

    @Test
    public void testCollisionWithSnitch() {
        assertFalse(square.intersects(snitch));
        square = new Square1(300,300, Color.BLACK);
    }

    @Test
    public void testCollisionWithPoison() {
        assertFalse(square.intersects(balls.get(1)));
        snitch = new Circle1(300,300,Color.BLACK);
        assertTrue(snitch.intersects(balls.get(1)));
    }

    @Test
    public void testCollisionWithWall() {
        ArrayList<Circle1> balls = new ArrayList<Circle1>(20);
        for (int i = 0; i < balls.size(); i++) {
            balls.add(new Circle1(300, 300, Color.BLACK));
        }
    }

    @Test
    public void testCollisionUpper() {

        ArrayList<Circle1> upperwall = new ArrayList<Circle1>(20);
        for (int i = 0; i < upperwall.size(); i++) {
            balls.add(new Circle1(300,300,Color.BLACK));
        }

        for (Circle1 circle : upperwall) {
            assertTrue(snitch.equals(circle));
        }
    }

    @Test
    public void testCollisionDown() {
        ArrayList<Point> downWall = new ArrayList<Point>(30);
        for (int i = 0; i < downWall.size(); i++) {
            downWall.add(new Point(i, 31));
        }

        for (Point point : downWall) {
            assertFalse(snitch.equals(point));
        }
    }



}
