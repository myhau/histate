package com.michalfudala.histate.testing;


public class Main {


    public static void main(String[] args) {
//        Histate.instrument();

        SomeClassForTesting testing = new SomeClassForTesting();

        System.out.println(PrintFunctionAllCalls.prettyFieldsContext(testing));

        testing.add(Integer.valueOf(10), Integer.valueOf(10));

    }

}

