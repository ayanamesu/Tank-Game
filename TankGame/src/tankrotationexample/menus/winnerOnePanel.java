package tankrotationexample.menus;

import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class winnerOnePanel extends JPanel {
    private Launcher lf;

    public winnerOnePanel(Launcher lf) {
        this.lf = lf;
        JButton start = new JButton("Continue");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(600, 800, 250, 50);
        start.addActionListener((actionEvent -> this.lf.setFrame("end")));
        this.add(start);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(ResourceManager.getSprite("tank1"), 0, 0, null);
    }


}
