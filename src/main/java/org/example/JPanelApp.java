package org.example;

import org.example.haffman.HaffmanController;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JPanelApp extends JPanel {
    private String pathToTheTile;

    public JPanelApp() {
        JTextField textField = new JTextField(30);
        textField.setText("Drag your file here");
        JButton checkButton = new JButton("Check");
        JButton haffmanButton = new JButton("Haffman");
        JButton unpackingButton = new JButton("Unpacking");
        JButton rleButton = new JButton("RLE");


        haffmanButton.setBounds(100, 100, 100, 50);
        checkButton.setBounds(510, 100, 60, 30);// Устанавливаем размер и положение компонента
        unpackingButton.setBounds(200, 100, 200, 50);
        textField.setBounds(50, 100, 450, 30);
        rleButton.setBounds(100, 100, 100, 50);

        add(textField);
        add(checkButton);// Добавить компоненты
        add(haffmanButton);
        add(unpackingButton);
        add(rleButton);

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
            }

        });

        // Кнопка zip
        haffmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HaffmanController controller = new HaffmanController();
                boolean tg = pathToTheTile.endsWith("txt");
                try {
                    if (tg){
                        textField.setText(controller.archiving(pathToTheTile));
                    }else {
                        textField.setText("It's not txt");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Кнопка unpacking
        unpackingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HaffmanController controller = new HaffmanController();
                boolean tg = pathToTheTile.endsWith("huf");
                try {
                    if (tg){
                        controller.unzipping(pathToTheTile);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}

