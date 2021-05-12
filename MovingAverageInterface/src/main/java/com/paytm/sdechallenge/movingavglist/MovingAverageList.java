package com.paytm.sdechallenge.movingavglist;

import java.util.List;

public interface MovingAverageList<T> {

    List<T> getList();

    void addElement(T element);

    T getMovingAverage();

}
