package org.example;

import java.util.ArrayList;

public class Consumer implements Runnable {
    private final ArrayList<Integer> buffer;

    public Consumer(ArrayList<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException {
        synchronized (buffer) {
            if (buffer.isEmpty()) {
                buffer.wait();
            }
            System.out.println("Consumed " + buffer.get(0));
            buffer.remove(0);
            buffer.notifyAll();
        }
    }

}
