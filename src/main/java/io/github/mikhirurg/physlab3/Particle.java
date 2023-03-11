package io.github.mikhirurg.physlab3;

import java.awt.*;

public class Particle {
    double x;
    double y;
    double q;

    final int w = 20;

    public Particle(double x, double y, double q) {
        this.x = x;
        this.y = y;
        this.q = q;
    }

    public void paint(Graphics g, int width, int height, Convert cx, Convert cy) {
        Graphics2D g2d = (Graphics2D) g;
        if (q < 0) {
            g2d.setColor(Color.BLUE);
        } else {
            if (q > 0) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLACK);
            }
        }
        g2d.fillOval((int) ((cx.convert(x)) - (w / 2.0)), (int) (cy.convert(y) - (w / 2.0)), w, w);
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int) (cx.convert(x) - (w / 2.0)), (int) (cy.convert(y) - (w / 2.0)), w, w);
    }


    public interface Convert {
        double convert(double a);
    }
}
