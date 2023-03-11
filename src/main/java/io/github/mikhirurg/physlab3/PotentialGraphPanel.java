package io.github.mikhirurg.physlab3;

import java.awt.*;

public class PotentialGraphPanel extends GraphPanel {

    private ElectricityPanel electricityPanel;
    final int numTicks = 40;

    PotentialGraphPanel(ElectricityPanel electricityPanel) {
        this.electricityPanel = electricityPanel;
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawGrid(g2d, (double)getWidth() / numTicks, (double)getHeight() / numTicks);
        drawAxis(g2d, (double) getWidth() / numTicks, 5, (double) getHeight() / numTicks, 5, 1, 50000, true, "X (м)", "Потенциал (В)");

        double scale = 100;

        g2d.setStroke(new BasicStroke(1.5f));

        double ox = -getWidth() * 10;
        double oy = electricityPanel.calculatePhiX(electricityPanel.getPX(ox));
        for (double x = -getWidth() * 10 + 1; x < getWidth() * 10; x++) {
            double y = electricityPanel.calculatePhiX(x);
            g2d.drawLine((int) (ox + getWidth() / 2.0), (int) (getHeight() / 2.0 - oy), (int) (x + getWidth() / 2.0), (int) (getHeight() / 2.0 - y));
            ox = x;
            oy = y;
        }

    }
}
