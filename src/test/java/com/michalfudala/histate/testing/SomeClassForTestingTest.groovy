package com.michalfudala.histate.testing

import spock.lang.Specification
import spock.lang.Unroll

import static SomeClassForTesting.*

class SomeClassForTestingTest extends Specification {

    void setup() {
        Histate.instrument()
    }

    @Unroll
    def "test add"() {
        given:
        def computer = new SomeClassForTesting()

        when:
        def result = computer.add(a, b)

        then:
        result == expectedResult

        where:
        a  | b  | expectedResult

        // fixme: ye, bad
        10 | 10 | 21
        -1 | 10 | 10
    }

    @Unroll
    def "test add for Num"() {
        given:
        def computer = new SomeClassForTesting()

        when:
        def result = computer.add(new Num(a), new Num(b))

        then:
        result.get() == c

        where:
        a   | b   | c
        100 | 100 | 200
        -1  | -2  | -3
    }

    @Unroll
    def "test sub"() {

        def computer = new SomeClassForTesting()

        when:
        def result = computer.sub(a, b)

        then:
        result == expectedResult

        where:
        a  | b  | expectedResult
        10 | 10 | 0
        20 | 10 | 10
        50 | 40 | 10
        -1 | -1 | 0
    }
}
