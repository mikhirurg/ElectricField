package io.github.mikhirurg.physlab3;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PhysLabGUI {
    public static final int WIDTH = 1920 / 2;
    public static final int HEIGHT = 920;
    private final static int ipadX = 30;
    private final static int ipadY = 20;

    private final static double x1 = -10;
    private final static double y1 = 0;
    private final static double q1 = -10;
    private final static double x2 = 10;
    private final static double y2 = 0;
    private final static double q2 = 10;
    private final static int nlines = 20;

    private static JTextField Q1;
    private static JTextField Q2;
    private static JTextField X1;
    private static JTextField X2;
    private static JTextField Y1;
    private static JTextField Y2;
    private static JTextField nLines;
    private static JButton Set;
    private static JButton Reset;

    private static JTextField X;
    private static JTextField Y;
    private static JButton Find;
    private static JTextArea info;

    private static JFrame graph1;
    private static JFrame graph2;

    private static ElectricityPanel panel;

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;


        JPanel fieldControl = new JPanel(new GridBagLayout());
        TitledBorder border = new TitledBorder("Управление зарядами:");

        fieldControl.setBorder(border);

        JLabel label = new JLabel("Заряды Q1 и Q2 (нКл): ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = ipadX;
        constraints.ipady = ipadY;
        fieldControl.add(label, constraints);

        Q1 = new JTextField(String.valueOf(q1));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        fieldControl.add(Q1, constraints);

        Q2 = new JTextField(String.valueOf(q2));
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        fieldControl.add(Q2, constraints);

        label = new JLabel("Координаты X1 и X2 (см)");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = ipadX;
        constraints.ipady = ipadY;
        fieldControl.add(label, constraints);

        X1 = new JTextField(String.valueOf(x1));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        fieldControl.add(X1, constraints);

        X2 = new JTextField(String.valueOf(x2));
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        fieldControl.add(X2, constraints);

        label = new JLabel("Координаты Y1 и Y2 (см)");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = ipadX;
        constraints.ipady = ipadY;
        fieldControl.add(label, constraints);

        Y1 = new JTextField(String.valueOf(y1));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        fieldControl.add(Y1, constraints);

        Y2 = new JTextField(String.valueOf(y2));
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        fieldControl.add(Y2, constraints);

        label = new JLabel("Количество силовых линий:");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = ipadX;
        constraints.ipady = ipadY;
        fieldControl.add(label, constraints);

        nLines = new JTextField(String.valueOf(nlines));
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        fieldControl.add(nLines, constraints);

        Set = new JButton("Установить");
        constraints.fill = GridBagConstraints.SOUTHWEST;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.gridheight = 1;
        fieldControl.add(Set, constraints);

        Reset = new JButton("Сброс");
        constraints.fill = GridBagConstraints.SOUTHWEST;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridheight = 1;
        fieldControl.add(Reset, constraints);

        Set.addActionListener(e -> {
            panel.repaint();
            try {
                double dQ1 = Double.parseDouble(Q1.getText());
                double dQ2 = Double.parseDouble(Q2.getText());
                double dX1 = Double.parseDouble(X1.getText());
                double dX2 = Double.parseDouble(X2.getText());
                double dY1 = Double.parseDouble(Y1.getText());
                double dY2 = Double.parseDouble(Y2.getText());
                int iNlines = Integer.parseInt(nLines.getText());

                panel.e1.x = dX1;
                panel.e1.y = dY1;
                panel.e1.q = dQ1;
                panel.e2.x = dX2;
                panel.e2.y = dY2;
                panel.e2.q = dQ2;
                panel.nlines = iNlines;

            } catch (NumberFormatException ne) {
                Q1.setText(String.valueOf(panel.e1.q));
                Q2.setText(String.valueOf(panel.e2.q));
                X1.setText(String.valueOf(panel.e1.x));
                X2.setText(String.valueOf(panel.e2.x));
                Y1.setText(String.valueOf(panel.e1.y));
                Y2.setText(String.valueOf(panel.e2.y));
                nLines.setText(String.valueOf(panel.nlines));
                JOptionPane.showMessageDialog(pane, "Ошибка! Неправильный формат данных!");
            }
        });

        Reset.addActionListener(e -> {
            Q1.setText(String.valueOf(q1));
            Q2.setText(String.valueOf(q2));
            X1.setText(String.valueOf(x1));
            X2.setText(String.valueOf(x2));
            Y1.setText(String.valueOf(y1));
            Y2.setText(String.valueOf(y2));
            nLines.setText(String.valueOf(nlines));

            panel.e1.x = x1;
            panel.e1.y = y1;
            panel.e1.q = q1;
            panel.e2.x = x2;
            panel.e2.y = y2;
            panel.e2.q = q2;
            panel.nlines = nlines;

            panel.repaint();
        });

        JButton save = new JButton("Сохранить");

        save.addActionListener(e -> {
            BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            panel.printAll(g2d);
            g2d.dispose();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить график");
            int userSelect = fileChooser.showSaveDialog(panel);
            if (userSelect == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(img, "png", fileChooser.getSelectedFile());
                } catch (IOException ioException) {
                    System.err.println("Error while saving file!");
                }
            }
        });

        constraints.fill = GridBagConstraints.SOUTHWEST;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
        fieldControl.add(save, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        pane.add(fieldControl, constraints);

        JPanel electInfo = new JPanel(new GridBagLayout());
        border = new TitledBorder("Информация:");
        electInfo.setBorder(border);

        label = new JLabel("Координаты X и Y:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = ipadX;
        constraints.ipady = ipadY;
        electInfo.add(label, constraints);

        X = new JTextField("0");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        electInfo.add(X, constraints);

        Y = new JTextField("0");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        electInfo.add(Y, constraints);

        Find = new JButton("Вычислить");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        electInfo.add(Find, constraints);


        panel = new ElectricityPanel(x1, y1, q1, x2, y2, q2, nlines, X1, Y1, X2, Y2, X, Y);
        panel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        panel.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 4;
        constraints.weightx = 1;
        constraints.weighty = 1;
        pane.add(panel, constraints);

        info = new JTextArea();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        info.setEditable(false);
        info.setText("Напряженность: " + panel.calculateE(Double.parseDouble(X.getText()), Double.parseDouble(Y.getText())) + "\n" +
                "Потенциал: " + panel.calculatePhi(Double.parseDouble(X.getText()), Double.parseDouble(Y.getText())));
        electInfo.add(info, constraints);

        panel.info = info;

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        pane.add(electInfo, constraints);

        Find.addActionListener(e -> {
            try {
                info.setText("Напряженность: " + panel.calculateE(Double.parseDouble(X.getText()), Double.parseDouble(Y.getText())) + "\n" +
                        "Потенциал: " + panel.calculatePhi(Double.parseDouble(X.getText()), Double.parseDouble(Y.getText())));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(pane, "Ошибка! Неправильный формат данных!");
            }
        });

        JPanel graphControl = new JPanel();
        border = new TitledBorder("Графики");
        graphControl.setBorder(border);

        JButton phiX = new JButton("Потенциал");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        graphControl.add(phiX, constraints);

        JButton Ex = new JButton("Напряженность");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        graphControl.add(Ex, constraints);

        graph1 = new JFrame("График зависимости потенциала от X");
        int size = 700;
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/icon.png"));
        graph1.setIconImage(icon.getImage());
        graph1.setLayout(new BorderLayout());
        graph1.setPreferredSize(new Dimension(size, size));
        PotentialGraphPanel potentialGraphPanel = new PotentialGraphPanel(panel);
        graph1.add(potentialGraphPanel, BorderLayout.CENTER);

        save = new JButton("Сохранить");

        save.addActionListener(e -> {
            BufferedImage img = new BufferedImage(potentialGraphPanel.getWidth(), potentialGraphPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            potentialGraphPanel.printAll(g2d);
            g2d.dispose();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить график");
            int userSelect = fileChooser.showSaveDialog(graph1);
            if (userSelect == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(img, "png", fileChooser.getSelectedFile());
                } catch (IOException ioException) {
                    System.err.println("Error while saving file!");
                }
            }
        });

        graph1.add(save, BorderLayout.NORTH);
        graph1.pack();

        phiX.addActionListener(e -> {
            graph1.setVisible(!graph1.isVisible());
            graph1.setSize(new Dimension(size, size));
        });

        graph2 = new JFrame("График зависимости напряженности от X");
        graph2.setLayout(new BorderLayout());
        graph2.setPreferredSize(new Dimension(size, size));
        graph2.setIconImage(icon.getImage());
        TensionGraphPanel tensionGraphPanel = new TensionGraphPanel(panel);
        graph2.add(tensionGraphPanel, BorderLayout.CENTER);

        save = new JButton("Сохранить");

        save.addActionListener(e -> {
            BufferedImage img = new BufferedImage(tensionGraphPanel.getWidth(), tensionGraphPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            tensionGraphPanel.printAll(g2d);
            g2d.dispose();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить график");
            int userSelect = fileChooser.showSaveDialog(graph2);
            if (userSelect == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(img, "png", fileChooser.getSelectedFile());
                } catch (IOException ioException) {
                    System.err.println("Error while saving file!");
                }
            }
        });
        graph2.add(save, BorderLayout.NORTH);
        graph2.pack();

        Ex.addActionListener(e -> {
            graph2.setVisible(!graph2.isVisible());
            graph2.setSize(new Dimension(size, size));
        });

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        pane.add(graphControl, constraints);

        JPanel filler = new JPanel();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        pane.add(filler, constraints);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("PhysLabElectricityV1. Mikhail Ushakov M3102.");
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/icon.png"));

        frame.setIconImage(icon.getImage());

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            System.err.println("Error while installing LaF");
        }


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane());
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PhysLabGUI::createAndShowGUI);
    }
}
