package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ZipServiceTest {
    ZipService zipService = new ZipService();

    @org.junit.jupiter.api.Test
    void createZIPTestTrue() throws IOException {
        boolean result = zipService.createZIP("C:\\Users\\User\\Desktop\\TestForCSI\\Test.txt");

        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void createZIPTestFalse() throws IOException {
        boolean result = zipService.createZIP("C:\\Users\\User\\Desktop\\Java_Polnoe_rukovodstvo_-_Gerbert_Shildt_10-e_izdanie.pdf");

        assertFalse(result);
    }

    @org.junit.jupiter.api.Test
    void unpackingZIP() throws IOException {
        String actual = zipService.unpackingZIP("C:\\Users\\User\\IdeaProjects\\CSO\\output.zip");

        assertEquals("File: Test.txt is ready",actual);
    }

    @org.junit.jupiter.api.Test
    void createZIPTestException() throws IOException {
        assertThrows(FileNotFoundException.class, () ->{
            zipService.createZIP("C:\\Users\\User\\Desktop\\visualvm_215");
        });

    }
}