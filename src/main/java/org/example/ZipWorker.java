package org.example;

import java.io.*;
import java.util.zip.*;

public class ZipWorker {

    public boolean createZIP(String filename) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("C:\\Users\\User\\Desktop\\output.zip"));
             FileInputStream fis = new FileInputStream(filename);) {
            ZipEntry entry1 = new ZipEntry(new File(filename).getName());
            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zout.write(buffer);
            // закрываем текущую запись для новой записи
            zout.closeEntry();
        } catch (Exception ex) {
            ex.getMessage();
        }
        return true;
    }

    public String unpackingZIP(String filename) {
        String forReturn = null;
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(filename))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {

                name = entry.getName(); // получим название файла

                FileOutputStream fout = new FileOutputStream("C:\\Users\\User\\Desktop\\new " + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
                forReturn = "File: " + name + " is ready";
            }
        } catch (Exception ex) {
            ex.toString();
            return "Error in stage of unpacking an archive";
        }
        return forReturn;
    }


}
