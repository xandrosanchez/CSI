package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JPanelApp extends JPanel {

    ImageIcon imageIcon = new ImageIcon("miet.png");
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 165, 0, null);
    }

    private String pathToTheTile;

    public JPanelApp() {

        JLabel jLabel = new JLabel(new ImageIcon("miet.png"));
        jLabel.setBounds(0,165,600,135);
        add(jLabel);
        JTextField textField = new JTextField(30);
        textField.setText("Drag your file here");
        JButton checkButton = new JButton("Check");
        JButton zipButton = new JButton("ZIP");
        JButton unpackingButton = new JButton("Unpacking");
        jLabel.setIcon(imageIcon);


        zipButton.setBounds(100, 100, 100, 50);
        checkButton.setBounds(510, 100, 60, 30);// Устанавливаем размер и положение компонента
        unpackingButton.setBounds(200, 100, 200, 50);
        textField.setBounds(50, 100, 450, 30);

        add(textField);
        add(checkButton);// Добавить компоненты
        add(zipButton);
        add(unpackingButton);

        // Перетащите в файл
        textField.setTransferHandler(new TransferHandler() {
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

                    String filepath = o.toString();
                    if (filepath.startsWith("[")) {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]")) {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }
                    textField.setText(filepath);
                    pathToTheTile = filepath;
                    textField.setText("Save!");

                    return true;
                } catch (Exception exception) {
                    exception.getMessage();
                }
                return false;
            }

            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (DataFlavor flavor : flavors) {
                    if (DataFlavor.javaFileListFlavor.equals(flavor)) {
                        return true;
                    }
                }
                return false;
            }
        });



        // Кнопка просмотра
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc2 = new JFileChooser("./");
                fc2.showOpenDialog(checkButton);
                String lu = fc2.getSelectedFile().getAbsolutePath();
                textField.setText(lu);
                pathToTheTile = textField.getText();
                textField.setText("Save!");
                boolean b1 = pathToTheTile.endsWith("zip");
            }

        });

        // Кнопка zip
        zipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZipService zipService = new ZipService();

                if (pathToTheTile != null) {
                    boolean b1 = false;
                    try {
                        b1 = zipService.createZIP(pathToTheTile);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (b1){
                        textField.setText("Ready");
                    }
                    else {
                        textField.setText("Error in stage of creating an archive");
                    }
                }
            }
        });

        // Кнопка unpacking
        unpackingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZipService zipService = new ZipService();
                if (pathToTheTile != null & pathToTheTile.endsWith("zip")  ) {
                    try {
                        textField.setText(zipService.unpackingZIP(pathToTheTile));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else {
                    textField.setText("It is not ZIP");
                }
            }
        });
    }


    private class SignalProcess {
    }
}

