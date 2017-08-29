package com.michalfudala.histate.testing;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

final public class Histate {

    public static void instrument(StatesRepository repository) {
        Instrumentation instrumentation = ByteBuddyAgent.install();

        new AgentBuilder.Default()
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .type(ElementMatchers.nameEndsWith("ForTesting"))
                .transform((builder, typeDescription, classLoader, javaModule) -> {
                    return builder.method(ElementMatchers.any())
                            .intercept(MethodDelegation.to(repository));
                })
                .installOnByteBuddyAgent();


        System.out.println(repository.findByMethodName("add"));
    }
}
