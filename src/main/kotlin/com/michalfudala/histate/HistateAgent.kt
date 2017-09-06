package com.michalfudala.histate

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.implementation.MethodDelegation
import net.bytebuddy.matcher.ElementMatchers
import java.lang.instrument.Instrumentation


class HistateAgent {


  fun main(args: Array<String>) {

    val statesRepository = StatesRepository.instance

  }

  companion object {

    @JvmStatic
    fun premain(agentArgs: String?, inst: Instrumentation) {
      println("Success inside agent !!")
      instrument(inst, StatesRepository.instance)
    }

    fun instrument(instrumentation: Instrumentation, repository: StatesRepository) {
      AgentBuilder.Default()
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
          .type(ElementMatchers.nameEndsWith<TypeDescription>("ForTesting"))
          .transform({ builder, typeDescription, classLoader, javaModule ->
            builder.method(ElementMatchers.any())
                .intercept(MethodDelegation.to(repository))
          })
          .installOn(instrumentation)
    }
  }


}

