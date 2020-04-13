package com.softnautics.snvr;

import java.util.LinkedList;
import java.util.Queue;

public class NalQueue {
    private Queue<NAL> mUnusedList = new LinkedList<>();
    private Queue<NAL> mNalQueue = new LinkedList<>();
    private static final int SIZE = 100;
    private static final int DEFAULT_BUFFER_SIZE = 100 * 1000;

    NalQueue() {
        for (int i = 0; i < SIZE; i++) {
            NAL nal = new NAL();
            nal.buf = new byte[DEFAULT_BUFFER_SIZE];
            mUnusedList.add(nal);
        }
    }

    synchronized public NAL obtain(int length) {
        NAL nal = mUnusedList.poll();
        if (nal == null) {
            return null;
        }
        if (nal.buf.length < length) {
            nal.buf = new byte[length];
        }
        nal.length = length;
        return nal;
    }

    synchronized public void add(NAL nal) {
        mNalQueue.add(nal);
    }

    synchronized public NAL peek() {
        return mNalQueue.peek();
    }

    synchronized public void remove() {
        NAL nal = mNalQueue.remove();
        mUnusedList.add(nal);
    }

    synchronized public void clear() {
        mUnusedList.addAll(mNalQueue);
        mNalQueue.clear();
    }

    synchronized public int size() {
        return mNalQueue.size();
    }
}
