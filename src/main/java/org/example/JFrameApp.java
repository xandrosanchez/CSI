package org.example;

import javax.swing.*;
import java.io.IOException;

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