package hr.fer.nenr.ann;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.BLACK;

public class TestDrawer extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 1500;
    ;
    private final ArtificialNeuralNetwork ann;
    private final int M;

    public TestDrawer(int M, ArtificialNeuralNetwork ann) throws HeadlessException {
        this.M = M;
        this.ann = ann;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    }

    private void initGUI() {
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
                        List<Double> prediction = ann.predict(List.of(example)).get(0);
                        System.out.println(prediction);
                        double max = prediction.get(0);
                        int maxIndex = 0;
                        for (int i = 1; i < prediction.size(); i++) {
                            //System.out.println(prediction.get(i));
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

        getContentPane().add(testing);
    }

}
