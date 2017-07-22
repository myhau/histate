package com.michalfudala.histate.testing;

public class SomeClassForTesting {

    private int and = 1;

    public static class Num {
        private int what;

        public Num(int what) {
            this.what = what;
        }


        public int get() {
            return what;
        }
    }

    public int add(int a, int b) {
        return a + b + and;
    }

    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    public Num add(Num a, Num b) {
        return new Num(a.get() + b.get());
    }

    public int sub(Integer a, Integer b) {
        return a - b;
    }
}
