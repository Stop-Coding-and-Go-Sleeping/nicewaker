package com.example.exp.sleep;

/**
 * The TestView interface provides four methods to  update the data.
 */

public interface TestView {
    void addPoint2(Double x, Double y);

    void setLux(Float lux);

    void invalidate();

    boolean post(Runnable runnable);
}
