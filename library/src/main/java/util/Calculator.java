package util;

public class Calculator {
    private int value;

    public Calculator() {
//        System.out.println("creating a new instance");
    }

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
        value = value * value;
    }

    public int value() {
        return value;
    }
}
