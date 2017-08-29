package com.michalfudala.histate.testing;


public class Main {


    public static void main(String[] args) {

        StatesRepository statesRepository = new StatesRepository();

        Histate.instrument(statesRepository);

        SomeClassForTesting testing = new SomeClassForTesting();

//        System.out.println(PrintFunctionAllCalls.prettyFieldsContext(testing));

        testing.add(Integer.valueOf(10), Integer.valueOf(10));

        System.out.println(statesRepository.findByMethodName("add"));

    }

}

