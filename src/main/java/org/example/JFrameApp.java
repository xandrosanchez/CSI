package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class JFrameApp extends JFrame {

    public JFrameApp() throws IOException {
        add(new JPanelApp());
        setBounds(600, 300, 600, 300);
        setTitle("Архиватор");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);// показать
        setLocationRelativeTo(null);
        setLayout(null);
        //setResizable(false);// Фиксированный размер
    }


}