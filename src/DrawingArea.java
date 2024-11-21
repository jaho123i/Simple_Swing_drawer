import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DrawingArea extends JPanel {

    String savingString = "";
    BufferedImage image;
    Graphics2D imageGraphics;
    int x;
    int y;
    Mode mode;
    Color penColor;

    enum Mode {
        PEN,
        CIRCLE,
        SQUARE
    }

    public DrawingArea() {
        mode = Mode.PEN;
        penColor = Color.BLACK;
        image = new BufferedImage(2056, 2056, BufferedImage.TYPE_INT_RGB);
        imageGraphics = image.createGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, 2056, 2056);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    if(mode == Mode.PEN) {
                        x = event.getX();
                        y = event.getY();
                    }
                    else if(mode == Mode.CIRCLE) {
                        int r = new Random().nextInt(256);
                        int g = new Random().nextInt(256);
                        int b = new Random().nextInt(256);

                        imageGraphics.setColor(new Color(r, g, b));
                        imageGraphics.fillOval(event.getX() - 25, event.getY() - 25, 50, 50);
                        savingString += "C "+r+" "+g+" "+b+" "+(event.getX() - 25)+" "+(event.getY() - 25)+"\n";
                        repaint();
                    }
                    else {
                        int r = new Random().nextInt(256);
                        int g = new Random().nextInt(256);
                        int b = new Random().nextInt(256);
                        imageGraphics.setColor(new Color(r, g, b));
                        imageGraphics.fillRect(event.getX() - 25, event.getY() - 25, 50, 50);
                        savingString += "S "+r+" "+g+" "+b+" "+(event.getX() - 25)+" "+(event.getY() - 25)+"\n";
                        repaint();
                    }
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent event) {
                if(mode == Mode.PEN) {
                    if (SwingUtilities.isLeftMouseButton(event)) {
                        int X = event.getX();
                        int Y = event.getY();
                        imageGraphics.setColor(penColor);
                        imageGraphics.drawLine(x, y, X, Y);
                        savingString += "P "+penColor.getRed()+" "+penColor.getGreen()+" "+penColor.getBlue()+" "+x+" "+y+" "+X+" "+Y+"\n";
                        repaint();
                        x = X;
                        y = Y;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void drawFromRecord(String record) {
        String[] recArr = record.split(" ");

        String type = recArr[0];

        int r = Integer.parseInt(recArr[1]);
        int g = Integer.parseInt(recArr[2]);
        int b = Integer.parseInt(recArr[3]);
        int x = Integer.parseInt(recArr[4]);
        int y = Integer.parseInt(recArr[5]);

        if (type.equals("C")) {
            imageGraphics.setColor(new Color(r, g, b));
            imageGraphics.fillOval(x - 25, y - 25, 50, 50);
            savingString += "C "+r+" "+g+" "+b+" "+(x - 25)+" "+(y - 25)+"\n";
            repaint();
        } else if (type.equals("S")) {

        } else if (type.equals("P")) {
            int X = Integer.parseInt(recArr[6]);
            int Y = Integer.parseInt(recArr[7]);

            imageGraphics.setColor(new Color(r,g,b));
            imageGraphics.drawLine(x, y, X, Y);
            savingString += "P "+penColor.getRed()+" "+penColor.getGreen()+" "+penColor.getBlue()+" "+x+" "+y+" "+X+" "+Y+"\n";
            repaint();
        }

    }

}
