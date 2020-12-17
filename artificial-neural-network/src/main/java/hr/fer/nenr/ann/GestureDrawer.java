package hr.fer.nenr.ann;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.BLACK;

public class GestureDrawer extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 1500;
    private static final int NUMBER_OF_CLASSES = 5;
    private static final int NUMBER_OF_EXAMPLES = 10;
    private final List<Example> examples = new ArrayList<>();
    private final int M;
    private final ArtificialNeuralNetwork ann;
    private final int batchSize;

    public GestureDrawer(int M, File file, ArtificialNeuralNetwork ann, int batchSize) throws HeadlessException {
        this.M = M;
        this.ann = ann;
        this.batchSize = batchSize;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI(file);
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    }

    private void initGUI(File file) {
        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent drawing = new JPanel() {
            final List<Point> currentPoints = new ArrayList<>();
            final int examplesPerClass = NUMBER_OF_EXAMPLES / NUMBER_OF_CLASSES;
            int currentExampleNumber = 0;

            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        currentPoints.clear();
                        currentPoints.add(new Point(e.getPoint().getX(), e.getPoint().getY()));
                    }

                    public void mouseReleased(MouseEvent e) {
                        List<Double> expectedLabel = new ArrayList<>(NUMBER_OF_CLASSES);
                        int expectedClass = currentExampleNumber++ / examplesPerClass;
                        for (int i = 0; i < NUMBER_OF_CLASSES; i++) {
                            if (i == expectedClass) expectedLabel.add(1.0);
                            else expectedLabel.add(0.0);
                        }
                        Example example = new Example(new ArrayList<>(PointHandler.findRepresentativePoints(currentPoints, M)), expectedLabel);
                        examples.add(example);
                        removeAll();
                        updateUI();
                        if (currentExampleNumber == NUMBER_OF_EXAMPLES) {
                            for (Example ex : examples) {
                                PointHandler.writeExampleInFile(file, ex);
                            }
                            List<Example> examplesFromFile = PointHandler.readExamplesFromFile(file, M);
                            System.out.println(examplesFromFile);
                            ann.train(examples, batchSize);
                        }
                    }
                });
                addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        currentPoints.add(new Point(e.getPoint().getX(), e.getPoint().getY()));
                        paint(getGraphics());
                    }
                });
            }

            public void paint(Graphics graphics) {
                super.paint(graphics);
                graphics.setColor(BLACK);
                for (int i = 1; i < currentPoints.size(); i++) {
                    graphics.drawLine((int) currentPoints.get(i - 1).getX(), (int) currentPoints.get(i - 1).getY(), (int) currentPoints.get(i).getX(), (int) currentPoints.get(i).getY());
                }
            }
        };

        JLabel label = new JLabel("");
        JComponent testing = new JPanel() {
            final java.util.List<Point> currentPoints = new ArrayList<>();

            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        currentPoints.clear();
                        currentPoints.add(new Point(e.getPoint().getX(), e.getPoint().getY()));
                    }

                    public void mouseReleased(MouseEvent e) {
                        Example example = new Example(new ArrayList<>(PointHandler.findRepresentativePoints(currentPoints, M)), java.util.List.of());
                        System.out.println(example);
                        List<Double> prediction = ann.predict(List.of(example)).get(0);
                        System.out.println(prediction.toString());
                        double max = prediction.get(0);
                        int maxIndex = 0;
                        for (int i = 1; i < prediction.size(); i++) {
                            if (prediction.get(i) > max) {
                                max = prediction.get(i);
                                maxIndex = i;
                            }
                        }
                        System.out.println(maxIndex);
                        removeAll();
                        updateUI();
                    }
                });
                addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        currentPoints.add(new Point(e.getPoint().getX(), e.getPoint().getY()));
                        paint(getGraphics());
                    }
                });
            }

            public void paint(Graphics graphics) {
                super.paint(graphics);
                graphics.setColor(BLACK);
                for (int i = 1; i < currentPoints.size(); i++) {
                    graphics.drawLine((int) currentPoints.get(i - 1).getX(), (int) currentPoints.get(i - 1).getY(), (int) currentPoints.get(i).getX(), (int) currentPoints.get(i).getY());
                }
            }
        };
        label.setVisible(true);
        testing.add(label);
        tabbedPane.addTab("Drawing", drawing);
        tabbedPane.addTab("Testing", testing);

        getContentPane().add(tabbedPane);

    }
}
