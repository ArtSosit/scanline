import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

public class DemoScanline extends JFrame {

    public static void main(String[] args) {
        DemoScanline demo = new DemoScanline();

        demo.setSize(750, 750);
        demo.setVisible(true);
        demo.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public DemoScanline() {
        FillPolygonDemo p = new FillPolygonDemo();
        this.add(p);
    }

    class FillPolygonDemo extends JPanel {
        int num = 750;
        int LE[] = new int[num];
        int RE[] = new int[num];

        public FillPolygonDemo() {
            setSize(num, num);
            setLayout(null);

        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.magenta);
            fill(g);
        }

        void fillscanline(int x1, int y1, int x2, int y2) {
            float x, M;
            int t, y;
            // ymin to ymax
            if (y1 > y2) {
                t = x1;
                x1 = x2;
                x2 = t;
                t = y1;
                y1 = y2;
                y2 = t;
            }
            if (y2 - y1 == 0) {
                M = (x2 - x1);

            } else {
                M = (float) (x2 - x1) / (float) (y2 - y1);
            }

            x = x1;
            for (y = y1; y < y2; y++) {
                if (x < LE[y]) {
                    LE[y] = (int) x;
                }
                if (x > RE[y]) {
                    RE[y] = (int) x;
                }
                x = x + M;
            }
        }

        void fill(Graphics g) {
            int x1 = 50, y1 = 150, x2 = 100, y2 = 150, x3 = 100, y3 = 50, x4 = 50, y4 = 50;
            ddaLine(g, x1, y1, x2, y2);
            ddaLine(g, x2, y2, x3, y3);
            ddaLine(g, x3, y3, x4, y4);
            ddaLine(g, x4, y4, x1, y1);

            for (int i = 0; i < num; i++) {
                LE[i] = num;
                RE[i] = 0;

            }

            fillscanline(x1, y1, x2, y2);
            fillscanline(x2, y2, x3, y3);
            fillscanline(x3, y3, x4, y4);
            fillscanline(x4, y4, x1, y1);

            g.setColor(Color.BLACK);
            for (int y = 0; y < num; y++) {
                for (int x = LE[y] + 1; x < RE[y]; x++) {
                    g.fillOval(x, y, 2, 2);
                }
            }

        }

        public void ddaLine(Graphics g, int x1, int y1, int x2, int y2) {

            double dx, dy;
            double x, y;
            dx = x2 - x1;
            dy = y2 - y1;
            double m = dx / dy;
            double step;
            double xinc, yinc;

            if (Math.abs(dx) > Math.abs(dy))
                step = Math.abs(dx);
            else
                step = Math.abs(dy);

            xinc = (dx / step);
            yinc = (dy / step);
            x = x1;
            y = y1;

            g.fillOval((int) x, (int) y, 2, 2);

            for (float i = 0; i < step; i++) {
                x = x + xinc;
                y = y + yinc;
                g.fillOval((int) x, (int) y, 2, 2);
            }

        }
    }

}