package org.example;

import static org.junit.jupiter.api.Assertions.*;

class ZipWorkerTest {
    ZipWorker zipWorker = new ZipWorker();

    @org.junit.jupiter.api.Test
    void createZIPTestTrue() {
        boolean result = zipWorker.createZIP("C:\\Users\\User\\Desktop\\TestForCSI\\Test.txt");

        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void unpackingZIP() {
        String actual = zipWorker.unpackingZIP("C:\\Users\\User\\Desktop\\output.zip");

        assertEquals("File: Test.txt is ready",actual);
    }

    @org.junit.jupiter.api.Test
    void unpackingZIPException() {
        zipWorker.unpackingZIP("C:\\Users\\User\\Desktop\\TestForCSI\\Test.txt");
        /*assertThrows(NullPointerException.class,
                () -> zipWorker.unpackingZIP("C:\\Users\\User\\Desktop\\TestForCSI\\Test.txt"));*/
    }
}