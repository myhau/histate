package com.michalfudala.histate

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.implementation.MethodDelegation
import net.bytebuddy.matcher.ElementMatchers


class HistateInstrumentation {


  fun main(args: Array<String>) {

    val statesRepository = StatesRepository.instance

    HistateInstrumentation.instrument(statesRepository)

  }


  companion object {
    fun instrument(repository: StatesRepository) {
      AgentBuilder.Default()
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
          .type(ElementMatchers.nameEndsWith<TypeDescription>("ForTesting"))
          .transform({ builder, typeDescription, classLoader, javaModule ->
            builder.method(ElementMatchers.any())
                .intercept(MethodDelegation.to(repository))
          })
          .installOnByteBuddyAgent()
    }
  }


}

