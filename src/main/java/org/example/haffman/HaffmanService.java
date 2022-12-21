package org.example.haffman;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class HaffmanService {

    protected static String readFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    protected static TreeMap<Character, Integer> countFrequency(String text) {
        TreeMap<Character, Integer> freqMap = new TreeMap<>();
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Integer count = freqMap.get(c);
            freqMap.put(c, count != null ? count + 1 : 1);
        }
        return freqMap;
    }

    protected static CodeTreeNode huffman(ArrayList<CodeTreeNode> codeTreeNodes) {
        while (codeTreeNodes.size() > 1) {
            Collections.sort(codeTreeNodes);
            CodeTreeNode left = codeTreeNodes.remove(codeTreeNodes.size() - 1);
            CodeTreeNode right = codeTreeNodes.remove(codeTreeNodes.size() - 1);

            CodeTreeNode parent = new CodeTreeNode(null, right.weight + left.weight, left, right);
            codeTreeNodes.add(parent);
        }
        return  codeTreeNodes.get(0);
    }

    protected static void saveToFile(File output, Map<Character, Integer> frequencies, String bits) {
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(output));
            os.writeInt(frequencies.size());
            for (Character character: frequencies.keySet()) {
                os.writeChar(character);
                os.writeInt(frequencies.get(character));
            }
            int compressedSizeBits = bits.length();
            BitArray bitArray = new BitArray(compressedSizeBits);
            for (int i = 0; i < bits.length(); i++) {
                bitArray.set(i, bits.charAt(i) != '0' ? 1 : 0);
            }

            os.writeInt(compressedSizeBits);
            os.write(bitArray.bytes, 0, bitArray.getSizeInBytes());
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void loadFromFile(File input, Map<Character, Integer> frequencies, StringBuilder bits) {
        try {
            DataInputStream os = new DataInputStream(new FileInputStream(input));
            int frequencyTableSize = os.readInt();
            for (int i = 0; i < frequencyTableSize; i++) {
                frequencies.put(os.readChar(), os.readInt());
            }
            int dataSizeBits = os.readInt();
            BitArray bitArray = new BitArray(dataSizeBits);
            os.read(bitArray.bytes, 0, bitArray.getSizeInBytes());
            os.close();

            for (int i = 0; i < bitArray.size; i++) {
                bits.append(bitArray.get(i) != 0 ? "1" : 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static String huffmanDecode(String encoded, CodeTreeNode tree) {
        StringBuilder decoded = new StringBuilder();

        CodeTreeNode node = tree;
        for (int i = 0; i < encoded.length(); i++) {
            node = encoded.charAt(i) == '0' ? node.left : node.right;
            if (node.content != null) {
                decoded.append(node.content);
                node = tree;
            }
        }
        return decoded.toString();
    }
}
