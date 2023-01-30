package com.example;
// Source: https://java-programming.mooc.fi/part-6/3-introduction-to-testing
public class Calculator {

    private int value;

    public Calculator() {
        this.value = 0;
    }

    public void add(int number) {
        this.value = this.value + number;
    }

    public void subtract(int number) {
        this.value = this.value - number;
    }

    public int getValue() {
        return this.value;
    }
}