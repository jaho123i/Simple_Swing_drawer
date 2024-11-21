import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimpleDrawEditor sde = new SimpleDrawEditor();
    }
}

class SimpleDrawEditor {
    DrawingArea drawingArea;
    File workingFile;
    public SimpleDrawEditor() {
        SwingUtilities.invokeLater(() -> createGUI());
    }

    private void createGUI() {
        JFrame jFrame = new JFrame("SimpleDrawEditor");
        jFrame.setSize(1000,600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        jFrame.setJMenuBar(menuBar);

        drawingArea = new DrawingArea();
        jFrame.add(drawingArea,BorderLayout.CENTER);
        drawingArea.setVisible(true);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem open = new JMenuItem("Open");
        fileMenu.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                workingFile = fileChooser.getSelectedFile();
                try {
                    Scanner scanner = new Scanner(workingFile);
                    String record = scanner.nextLine();
                    drawingArea.drawFromRecord(record);

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        JMenuItem save = new JMenuItem("Save");
        fileMenu.add(save);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workingFile.delete();
                try {
                    workingFile.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    PrintWriter print = new PrintWriter(workingFile);
                    print.print(drawingArea.savingString);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JMenuItem saveAs = new JMenuItem("Save As...");
        fileMenu.add(saveAs);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (workingFile == null) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new File(""));
                    workingFile = fileChooser.getSelectedFile();
                }
                try {
                    PrintWriter print = new PrintWriter(workingFile);
                    print.print(drawingArea.savingString);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        fileMenu.addSeparator();

        JMenuItem quit = new JMenuItem("Quit");
        fileMenu.add(quit);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        open.setMnemonic('O');
        save.setMnemonic('S');
        saveAs.setMnemonic('A');
        quit.setMnemonic('Q');



        JMenu drawMenu = new JMenu("Draw");
        menuBar.add(drawMenu);

        JMenuItem figure = new JMenu("Figure");
        drawMenu.add(figure);

        JMenuItem circle = new JMenuItem("Circle");
        figure.add(circle);
        circle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingArea.mode = DrawingArea.Mode.CIRCLE;
            }
        });
        JMenuItem square = new JMenuItem("Square");
        figure.add(square);
        square.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
        square.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingArea.mode = DrawingArea.Mode.SQUARE;
            }
        });
        JMenuItem pen = new JMenuItem("Pen");
        figure.add(pen);
        pen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        pen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingArea.mode = DrawingArea.Mode.PEN;
            }
        });
        JMenuItem color = new JMenuItem("Color");
        drawMenu.add(color);
        color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.SHIFT_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingArea.penColor = JColorChooser.showDialog(null, "Wybierz kolor", Color.BLACK);
            }
        });
        drawMenu.addSeparator();
        JMenuItem clear = new JMenuItem("Clear");
        drawMenu.add(clear);
        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingArea.imageGraphics.setColor(Color.WHITE);
                drawingArea.imageGraphics.fillRect(0, 0, 2056, 2056);
                drawingArea.repaint();
            }
        });
        circle.setMnemonic('C');
        square.setMnemonic('S');
        pen.setMnemonic('P');
        color.setMnemonic('L');
        clear.setMnemonic('I');
    }
}
