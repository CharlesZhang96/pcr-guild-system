package com.charleszhang.pcrguildsystem.util;

import org.junit.jupiter.api.Test;

public class IdWorkerTest {

    @Test
    public void idWorkerTest(String[] args) {
        IdWorker idWorker = new IdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }
}
