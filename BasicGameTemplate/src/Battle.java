import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import util.*;

public class Battle extends JFrame {
    private GameObject enemy;

    public Battle(GameObject enemy) {
        this.enemy = enemy;
        setTitle("Battle Mode!");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel backgroundLabel = new JLabel(new ImageIcon("res/battle.png"));
        backgroundLabel.setBounds(0, 0, 1000, 1000); // Set size to match frame
        add(backgroundLabel);

        JLabel label = new JLabel("You encountered a " + enemy.getClass().getSimpleName() + "!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton fightButton = new JButton("Fight");
        JButton runButton = new JButton("Run");

        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battleLogic();
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                escapeBattle();
            }
        });


        buttonPanel.add(fightButton);
        buttonPanel.add(runButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void battleLogic() {
        JOptionPane.showMessageDialog(this, "You defeated " + enemy.getClass().getSimpleName() + "!");
        dispose(); // Close battle window
        MainWindow.resumeGame(); // Resume the main game loop
    }

    private void escapeBattle() {
        JOptionPane.showMessageDialog(this, "You ran away!");
        dispose(); // Close battle window
        MainWindow.resumeGame(); // Resume the main game loop
    }
}
