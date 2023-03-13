package io.github.mikhirurg.physlab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ElectricityPanel extends GraphPanel {

    Particle e1, e2;

    final double k = 9e9;

    final int numTicksX = 100;

    private double x0;
    private double y0;
    private double x;
    private double y;
    private int num;

    int nlines;

    double eps = 0;

    JTextField X1;
    JTextField Y1;
    JTextField X2;
    JTextField Y2;
    JTextField X;
    JTextField Y;
    JTextArea info;

    boolean showInfo;
    double infoX;
    double infoY;

    ElectricityPanel(double x1, double y1, double q1, double x2, double y2, double q2, int nlines, JTextField X1, JTextField Y1, JTextField X2, JTextField Y2, JTextField X, JTextField Y) {
        e1 = new Particle(x1, y1, q1);
        e2 = new Particle(x2, y2, q2);

        this.X1 = X1;
        this.X2 = X2;
        this.Y1 = Y1;
        this.Y2 = Y2;
        this.X = X;
        this.Y = Y;

        this.nlines = nlines;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showInfo = false;
                    x0 = e.getX();
                    y0 = e.getY();

                    if ((x0 - getScrX(e1.x)) * (x0 - getScrX(e1.x)) + (y0 + e2.w / 2.0 - getScrY(e1.y)) * (y0 + e2.w / 2.0 - getScrY(e1.y)) <= e1.w * e1.w) {
                        num = 1;
                    } else if ((x0 - getScrX(e2.x)) * (x0 - getScrX(e2.x)) + (y0 + e2.w / 2.0 - getScrY(e2.y)) * (y0 + e2.w / 2.0 - getScrY(e2.y)) <= e2.w * e2.w) {
                        num = 2;
                    }
                } else {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        infoX = (int) getPX(e.getX());
                        infoY = (int) getPY(e.getY());
                        X.setText(String.valueOf(infoX));
                        Y.setText(String.valueOf(infoY));
                        // voltage_title, potential_title
                        info.setText("Напряженность: " + format(calculateE(Double.parseDouble(X.getText()), Double.parseDouble(Y.getText())), 6) + " В/м\n" +
                                "Потенциал: " + format(calculatePhi(Double.parseDouble(X.getText()), Double.parseDouble(Y.getText())), 6) + " В");
                        if (showInfo) {
                            repaint();
                        } else {
                            showInfo = true;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showInfo = false;
                }
                if (num == 1) {
                    X1.setText(format(e1.x));
                    Y1.setText(format(e1.y));
                }
                if (num == 2) {
                    X2.setText(format(e2.x));
                    Y2.setText(format(e2.y));
                }
                num = -1;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (showInfo) {
                    infoX = getPX(e.getX());
                    infoY = getPX(e.getY());
                    repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                x = e.getX();
                y = e.getY();

                double dx = getPX(x) - getPX(x0);
                double dy = getPY(y) - getPY(y0);

                switch (num) {
                    case 1: {
                        e1.x += dx;
                        e1.y += dy;
                    }
                    break;
                    case 2: {
                        e2.x += dx;
                        e2.y += dy;
                    }
                    break;
                }
                x0 = x;
                y0 = y;
                repaint();
            }
        });
    }

    void drawField(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        drawGrid(g2d, (double) getWidth() / numTicksX, (double) getHeight() / numTicksX);
        drawAxis(g2d, (double) getWidth() / numTicksX, 5, (double) getHeight() / numTicksX, 5, 1, 1, true, "X (см)", "Y (см)");

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(1.5f));
        for (int i = 1; i <= nlines; i += 2) {
            double alpha = 2 * Math.PI / nlines;
            double xi0 = e1.x + e1.w / 20.0 * Math.cos(i * alpha);
            double yi0 = e1.y + e1.w / 20.0 * Math.sin(i * alpha);
            double dl = 5 / 10.0;
            int t = 0;
            double q1 = e1.q;
            double q2 = e2.q;
            int arrow = (int) Math.signum(q1);
            if (e1.q != 0) {
                if (q1 < 0) {
                    q1 = -q1;
                    q2 = -q2;
                    arrow = -arrow * 5;
                }
                do {
                    double r1 = Math.sqrt((xi0 - e1.x) * (xi0 - e1.x) + (yi0 - e1.y) * (yi0 - e1.y));
                    double r2 = Math.sqrt((xi0 - e2.x) * (xi0 - e2.x) + (yi0 - e2.y) * (yi0 - e2.y));
                    if (Math.abs(r1) > eps && Math.abs(r2) > eps) {
                        double Ex = (q1 * (xi0 - e1.x) / (r1 * r1 * r1) + q2 * (xi0 - e2.x) / (r2 * r2 * r2));
                        double Ey = (q1 * (yi0 - e1.y) / (r1 * r1 * r1) + q2 * (yi0 - e2.y) / (r2 * r2 * r2));
                        double E = Math.sqrt(Ex * Ex + Ey * Ey);

                        double dx = dl * Ex / E;
                        double dy = dl * Ey / E;

                        g2d.drawLine((int) getScrX(xi0), (int) getScrY(yi0), (int) getScrX(xi0 + dx), (int) getScrY(yi0 + dy));

                        if (t % 10 == 0) {
                            double beta = Math.PI / 4.0 * 3 * arrow;
                            double arrowDx = dx / dl * Math.cos(beta) - dy / dl * Math.sin(beta);
                            double arrowDy = dx / dl * Math.sin(beta) + dy / dl * Math.cos(beta);

                            g2d.drawLine((int) getScrX(xi0), (int) getScrY(yi0), (int) getScrX(xi0 + arrowDx * dl), (int) getScrY(yi0 + arrowDy * dl));

                            arrowDx = dx / dl * Math.cos(beta) + dy / dl * Math.sin(beta);
                            arrowDy = -dx / dl * Math.sin(beta) + dy / dl * Math.cos(beta);

                            g2d.drawLine((int) getScrX(xi0), (int) getScrY(yi0), (int) getScrX(xi0 + arrowDx * dl), (int) getScrY(yi0 + arrowDy * dl));
                        }

                        xi0 += dx;
                        yi0 += dy;
                    }
                    t++;
                } while (t < 2000);
            }
        }
        for (int i = 0; i < nlines; i += 2) {
            double alpha = 2 * Math.PI / nlines;
            double xi0 = e2.x + e2.w / 20.0 * Math.cos(i * alpha);
            double yi0 = e2.y + e2.w / 20.0 * Math.sin(i * alpha);
            double dl = 5 / 10.0;
            int t = 0;
            double q1 = e1.q;
            double q2 = e2.q;
            int arrow = (int) Math.signum(q2);
            if (e2.q != 0) {
                if (q2 < 0) {
                    q1 = -q1;
                    q2 = -q2;
                    arrow = -arrow * 5;
                }
                do {
                    double r1 = Math.sqrt((xi0 - e1.x) * (xi0 - e1.x) + (yi0 - e1.y) * (yi0 - e1.y));
                    double r2 = Math.sqrt((xi0 - e2.x) * (xi0 - e2.x) + (yi0 - e2.y) * (yi0 - e2.y));
                    if (Math.abs(r1) > eps && Math.abs(r2) > eps) {
                        double Ex = q1 * (xi0 - e1.x) / (r1 * r1 * r1) + q2 * (xi0 - e2.x) / (r2 * r2 * r2);
                        double Ey = q1 * (yi0 - e1.y) / (r1 * r1 * r1) + q2 * (yi0 - e2.y) / (r2 * r2 * r2);
                        double E = Math.sqrt(Ex * Ex + Ey * Ey);

                        double dx = dl * Ex / E;
                        double dy = dl * Ey / E;

                        g2d.drawLine((int) getScrX(xi0), (int) getScrY(yi0), (int) getScrX(xi0 + dx), (int) getScrY(yi0 + dy));

                        if (t % 10 == 0) {
                            double beta = Math.PI / 4.0 * 3 * arrow;
                            double arrowDx = dx / dl * Math.cos(beta) - dy / dl * Math.sin(beta);
                            double arrowDy = dx / dl * Math.sin(beta) + dy / dl * Math.cos(beta);

                            g2d.drawLine((int) getScrX(xi0), (int) getScrY(yi0), (int) getScrX(xi0 + arrowDx * dl), (int) getScrY(yi0 + arrowDy * dl));

                            arrowDx = dx / dl * Math.cos(beta) + dy / dl * Math.sin(beta);
                            arrowDy = -dx / dl * Math.sin(beta) + dy / dl * Math.cos(beta);

                            g2d.drawLine((int) getScrX(xi0), (int) getScrY(yi0), (int) getScrX(xi0 + arrowDx * dl), (int) getScrY(yi0 + arrowDy * dl));
                        }

                        xi0 += dx;
                        yi0 += dy;
                    }
                    t++;
                } while (t < 2000);
            }
        }

    }

    public double calculateE(double x, double y) {

        double r1 = Math.sqrt((x - e1.x) * (x - e1.x) + (y - e1.y) * (y - e1.y)) * 1e-2;
        double r2 = Math.sqrt((x - e2.x) * (x - e2.x) + (y - e2.y) * (y - e2.y)) * 1e-2;

        double q1 = e1.q * 1e-9;
        double q2 = e2.q * 1e-9;

        return (q1 / (r1 * r1) + q2 / (r2 * r2)) * k;
    }

    public double calculatePhi(double x, double y) {

        double r1 = Math.sqrt((x - e1.x) * (x - e1.x) + (y - e1.y) * (y - e1.y)) * 1e-2;
        double r2 = Math.sqrt((x - e2.x) * (x - e2.x) + (y - e2.y) * (y - e2.y)) * 1e-2;

        double p1, p2;

        double q1 = e1.q * 1e-9;
        double q2 = e2.q * 1e-9;

        p1 = q1 == 0 ? 0 : q1 / r1 * k;
        p2 = q2 == 0 ? 0 : q2 / r2 * k;

        return p1 + p2;
    }

    public double calculatePhiX(double x) {

        double r1 = (x - e1.x) * 1e-2;
        double r2 = (x - e2.x) * 1e-2;

        double p1, p2;

        double q1 = e1.q * 1e-9;
        double q2 = e2.q * 1e-9;

        p1 = q1 == 0 ? 0 : q1 / r1 * k;
        p2 = q2 == 0 ? 0 : q2 / r2 * k;

        return p1 + p2;
    }

    public double calculateEx(double x) {
        double r1 = (x - e1.x) * 1e-2;
        double r2 = (x - e2.x) * 1e-2;

        double q1 = e1.q * 1e-9;
        double q2 = e2.q * 1e-9;

        double p1 = q1 == 0 ? 0 : q1 / (r1 * r1) * k;
        double p2 = q2 == 0 ? 0 : q2 / (r2 * r2) * k;

        return p1 + p2;
    }

    private double getX0() {
        return getWidth() / 2.0;
    }

    private double getY0() {
        return getHeight() / 2.0;
    }

    private double getScrX(double x) {
        return getX0() + x * getWidth() / numTicksX;
    }

    private double getScrY(double y) {
        return getY0() - y * getHeight() / numTicksX;
    }

    public double getPX(double x) {
        return (x - getX0()) * (double) numTicksX / getWidth();
    }

    public double getPY(double y) {
        return (getY0() - y) * (double) numTicksX / getHeight();
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawField(g);
        e1.paint(g, getWidth(), getHeight(), this::getScrX, this::getScrY);
        e2.paint(g, getWidth(), getHeight(), this::getScrX, this::getScrY);

        if (showInfo) {
            int crossLen = 5;
            g2d.setColor(Color.RED);
            g2d.drawLine((int) getScrX(infoX) - crossLen, (int) getScrY(infoY) - crossLen, (int) getScrX(infoX) + crossLen, (int) getScrY(infoY) + crossLen);
            g2d.drawLine((int) getScrX(infoX) + crossLen, (int) getScrY(infoY) - crossLen, (int) getScrX(infoX) - crossLen, (int) getScrY(infoY) + crossLen);
            // voltage_title, potential_title
            String builder = "Напряжённость: " + format(calculateE(infoX, infoY), 6) +
                    "\nПотенциал: " + format(calculatePhi(infoX, infoY), 6);
            drawText(g2d, builder, (int) getScrX(infoX), (int) getScrY(infoY));
            g2d.setColor(Color.BLACK);
        }
    }


}
