package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class winnerTwoPanel extends JPanel {
    private Launcher lf;
    private BufferedImage menuBackground;


    public winnerTwoPanel(Launcher lf) {
        this.lf = lf;
        menuBackground = ResourceManager.getSprite("menu");
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        JButton start = new JButton("Continue");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 350, 250, 50);
        start.addActionListener((actionEvent -> this.lf.setFrame("end")));
        this.add(start);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 40));
        String message = "Tank 2 Winner!";
        int centX = 300;
        int buttonWidth = 250;
        int messageX = ( centX + buttonWidth - g.getFontMetrics().stringWidth(message)) / 2;
        int messageY = 310;
        g.drawString(message, messageX, messageY);
    }


}

