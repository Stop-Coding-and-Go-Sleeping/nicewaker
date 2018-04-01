package com.example.exp.sleep;

public interface TestView {
    void addPoint2(Double x, Double y);
    void setLux(Float lux);
    void invalidate();
    boolean post(Runnable runnable);
}
