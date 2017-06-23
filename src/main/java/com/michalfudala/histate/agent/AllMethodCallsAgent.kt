package com.michalfudala.histate.agent

import java.lang.instrument.Instrumentation


fun premain(arguments: String?, instrumentation: Instrumentation) {
  println("Hello from AgentX 'premain'!")
}

