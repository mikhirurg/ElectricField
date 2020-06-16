package io.github.mikhirurg.physlab3;

import javax.swing.*;
import java.awt.*;
import java.util.Formatter;

public class GraphPanel extends JPanel {

    double MAX = 1e10;
    double MIN = -1e10;

    public void drawGrid(Graphics2D g2d, double scaleX, double scaleY) {
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(new Color(65, 130, 197));
        BasicStroke stroke = (BasicStroke) g2d.getStroke();
        g2d.setStroke(new BasicStroke(0.3f));
        for (double y = 0; y < getHeight(); y += scaleY) {
            g2d.drawLine(0, (int) y, getWidth(), (int) y);
        }
        for (double x = getWidth() / 2.0; x < getWidth(); x += scaleX) {
            g2d.drawLine((int) x, 0, (int) x, getHeight());
        }
        for (double x = getWidth() / 2.0; x >= 0; x -= scaleX) {
            g2d.drawLine((int) x, 0, (int) x, getHeight());
        }
        g2d.setStroke(stroke);
    }

    public void drawAxis(Graphics2D g2d, double majorTickX, int minorTickX, double majorTickY, int minorTickY, int scaleX, int scaleY, boolean tickLabels, String nameX, String nameY) {
        BasicStroke stroke = (BasicStroke) g2d.getStroke();
        g2d.setStroke(new BasicStroke(1.5f));
        double fontH = g2d.getFontMetrics().getHeight();

        g2d.setColor(Color.BLACK);

        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);


        g2d.drawString(nameX, (int) (getWidth() - majorTickX), (int) (getHeight() / 2 - fontH / 2));
        g2d.drawString(nameY, getWidth() / 2, (int) (fontH));

        int lenM = 5;
        int len = 3;

        g2d.setStroke(new BasicStroke(1f));

        for (double x = getWidth() / 2.0 + majorTickX; x < getWidth() - majorTickX; x += 4 * majorTickX) {
            g2d.drawLine((int) x, getHeight() / 2, (int) x, getHeight() / 2 - lenM);
            if (tickLabels) {
                String text = format(scaleX * (x - getWidth() / 2.0) / majorTickX * 1e-2);
                int offset = g2d.getFontMetrics().stringWidth(text) / 2;
                g2d.drawString(text, (int) x - offset, (int) (getHeight() / 2 - fontH / 2));
            }
        }

        for (double x = getWidth() / 2.0 - 4 * majorTickX; x >= 0; x -= majorTickX * 4) {
            g2d.drawLine((int) x, getHeight() / 2, (int) x, getHeight() / 2 - lenM);
            if (tickLabels) {
                String text = format(scaleX * (x - getWidth() / 2.0) / majorTickX * 1e-2);
                int offset = g2d.getFontMetrics().stringWidth(text) / 2;
                g2d.drawString(text, (int) x - offset, (int) (getHeight() / 2 - fontH / 2));
            }
        }

        for (double y = getHeight() / 2.0 + 2 * majorTickY; y < getHeight(); y += 2 * majorTickY) {
            g2d.drawLine(getWidth() / 2, (int) y, getWidth() / 2 + lenM, (int) y);
            if (tickLabels) {
                g2d.drawString(format(scaleY * (y - getHeight() / 2.0) / majorTickX * 1e-2), getWidth() / 2 + lenM, (int) (getHeight() - (y - fontH / 2)));
            }
        }

        for (double y = getHeight() / 2.0 - majorTickY; y >= 0; y -= 2 * majorTickY) {
            g2d.drawLine(getWidth() / 2, (int) y, getWidth() / 2 + lenM, (int) y);
            if (tickLabels) {
                g2d.drawString(format(scaleY * (y - getHeight() / 2.0) / majorTickX * 1e-2), getWidth() / 2 + lenM, (int) (getHeight() - (y - fontH / 2)));
            }
        }

        g2d.setStroke(stroke);
    }

    public void drawText(Graphics2D g2, String str, int x, int y) {
        for (String line : str.split("\n")) {
            g2.drawString(line, x, y);
            y += g2.getFontMetrics().getHeight();
        }
    }

    public String format(double d) {
        return String.format("%.2f", d).replaceAll(",", ".");
    }

    public String format(double d, int n) {
        return String.format("%." + n + "f", d).replaceAll(",", ".");
    }
}
