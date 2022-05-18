package com.langrsoft.cucumber;

public class Calculator {
    private int value;

    public void clear() {
        value = 0;
    }

    public void enter(int number) {
        value = number;
    }

    public void add(int number) {
        value += number;
    }

    public void div(int number) {
        value /= number;
    }

    public void multiply(int number) {
        value *= number;
    }

    public void invert() {
        value *= -1;
    }

    public void square() {
        value = value * 2;
    }

    public int value() {
        return value;
    }
}
