package com.paytm.sdechallenge.movingavglist.impl;

import com.paytm.sdechallenge.movingavglist.MovingAverageList;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MovingAverageDoubleList implements MovingAverageList<Double> {

    private List<Double> movingAverageList;
    private int slotSize;
    private double average;
    private Logger logger;

    public MovingAverageDoubleList(int slotSize) {
        initialize();
        if (slotSize <= 0) {
            logger.error("Illegal slot size - " + slotSize);
            throw new IllegalArgumentException("Illegal slot size");
        }

        movingAverageList = new ArrayList();
        this.slotSize = slotSize;
    }

    @Override
    public Double getMovingAverage() {
        return this.average;
    }

    @Override
    public synchronized void addElement(Double element) {
        if (element == null) {
            logger.error("Illegal double value - " + element);
            throw new IllegalArgumentException("Illegal Null argument");
        }
        movingAverageList.add(element);
        calculateNewAverage();
    }

    @Override
    public List<Double> getList() {
        return this.movingAverageList;
    }

    public void initialize() {
        logger = Logger.getLogger(MovingAverageDoubleList.class);
        BasicConfigurator.configure();
    }

    private void calculateNewAverage() {
        int lastIndex = movingAverageList.size() - 1;

        if (lastIndex == 0) average = movingAverageList.get(0);
        else if (lastIndex < slotSize - 1) {
            double newSum = (average * lastIndex) + movingAverageList.get(lastIndex);
            double newNoOfValues = movingAverageList.size();
            average = newSum / newNoOfValues;
        } else {
            int idxToBeRemoved = movingAverageList.size() - slotSize;
            average -= movingAverageList.get(idxToBeRemoved) / (double) slotSize;
            average += movingAverageList.get(lastIndex) / (double) slotSize;
        }
        logger.info("Average updated - " + average);
    }

    public static void main(String[] args) {
        MovingAverageList<Double> list = new MovingAverageDoubleList(4);
        list.addElement(1d);
        list.addElement(2d);
        list.addElement(3d);
        list.addElement(4d);
        list.addElement(5d);
        list.addElement(6d);
        list.addElement(7d);
    }
}


