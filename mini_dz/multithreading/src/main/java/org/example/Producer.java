package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Producer implements Runnable {
    private final ArrayList<Integer> buffer;
    int size;

    public Producer(ArrayList<Integer> buffer, int size) {
        this.buffer = buffer;
        this.size = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                produce();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void produce() throws InterruptedException {
        Random rnd = new Random();
        synchronized (buffer) {
            if (buffer.size() >= size) {
                buffer.wait();
            }
            int value = rnd.nextInt(10);
            System.out.println("Produced " + value);
            buffer.add(value);
            buffer.notifyAll();
        }
    }
}
