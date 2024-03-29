package org.cis120.Dodge;

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a circle of a specified color.
 */
public class Circle1 extends GameObj1 {
    public static final int SIZE = 20;
    public static final int INIT_POS_X = 40;
    public static final int INIT_POS_Y = 40;
    public static final int INIT_VEL_X = 2;
    public static final int INIT_VEL_Y = 3;

    final private Color color;


    public Circle1(int courtWidth, int courtHeight, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
                courtWidth, courtHeight, color);

        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

}