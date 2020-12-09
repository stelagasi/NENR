package hr.fer.nenr.ann;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.BLACK;

public class GestureDrawer extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 1500;
    private final List<List<Point>> examples = new ArrayList<>();
    private static int M;

    public GestureDrawer() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    }

    private void initGUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent drawing = new JPanel() {
            final List<Point> currentPoints = new ArrayList<>();
            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        currentPoints.clear();
                        currentPoints.add(new Point(e.getPoint().getX(), e.getPoint().getY()));
                    }

                    public void mouseReleased(MouseEvent e) {
                        examples.add(new ArrayList<>(PointHandler.findRepresentativePoints(currentPoints, M)));
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
                        graphics.drawLine((int) currentPoints.get(i-1).getX(), (int) currentPoints.get(i-1).getY(), (int) currentPoints.get(i).getX(), (int) currentPoints.get(i).getY());
                    }
            }
        };
       // JComponent learning = new JPanel();
        JComponent testing = new JPanel() {
        };
        tabbedPane.addTab("Drawing", drawing);
       // tabbedPane.addTab("Learning", learning);
        tabbedPane.addTab("Testing", testing);

        getContentPane().add(tabbedPane);

    }

    public static void main(String[] args) {
        if(args.length != 4) throw new IllegalArgumentException("Number of arguments given is not 4");
        M = Integer.parseInt(args[1]);

        SwingUtilities.invokeLater(() -> new GestureDrawer().setVisible(true));
    }
}
