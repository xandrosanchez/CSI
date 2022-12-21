package org.example.haffman;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class HaffmanController {

    public String archiving(String filename) throws IOException {

        String text = HaffmanService.readFiles(filename);
        // вычисляем частоты символов в тексте
        TreeMap<Character, Integer> frequencies = HaffmanService.countFrequency(text);
        // генерируем список листов дерева
        ArrayList<CodeTreeNode> codeTreeNodes = new ArrayList<>();
        for(Character c: frequencies.keySet()) {
            codeTreeNodes.add(new CodeTreeNode(c, frequencies.get(c)));
        }
        // строим кодовое дерево с помощью алгоритма Хаффмана
        CodeTreeNode tree = HaffmanService.huffman(codeTreeNodes);
        // генерируем таблицу префиксных кодов для кодируемых символов с помощью кодового дерева
        TreeMap<Character, String> codes = new TreeMap<>();
        for(Character c: frequencies.keySet()) {
            codes.put(c, tree.getCodeForCharacter(c, ""));
        }
        // кодируем текст, заменяем символы соответствующими кодами
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }
        File file = new File("C:\\Users\\User\\Desktop\\compressed.huf");
        HaffmanService.saveToFile(file, frequencies, encoded.toString());
        codeTreeNodes.clear();
        return ("Compressed string size: " + encoded.length() + " bit");
    }

    public boolean unzipping(String filename) throws IOException {
        TreeMap<Character, Integer> frequencies = new TreeMap<>();
        ArrayList<CodeTreeNode> codeTreeNodes = new ArrayList<>();
        StringBuilder encoded = new StringBuilder();
        File file = new File(filename);
        // извлечение сжатой информации из файла
        HaffmanService.loadFromFile(file, frequencies, encoded);
        // генерация листов и построение кодового дерева Хаффмана на основе таблицы частот сжатого файла
        for(Character c: frequencies.keySet()) {
            codeTreeNodes.add(new CodeTreeNode(c, frequencies.get(c)));
        }
        CodeTreeNode tree = HaffmanService.huffman(codeTreeNodes);
        // декодирование обратно исходной информации из сжатой
        String decoded = HaffmanService.huffmanDecode(encoded.toString(), tree);
        // сохранение в файл декодированной информации
        Files.write(Paths.get("C:\\Users\\User\\Desktop\\decompressed.txt"), decoded.getBytes());
        return false;
    }
}
