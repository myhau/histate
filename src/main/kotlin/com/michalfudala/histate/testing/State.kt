package com.michalfudala.histate.testing



data class Argument(val name: String, val type: String)

data class MethodDescription(
    val packageName: String,
    val methodName: String,
    val returnType: String,
    val arguments: List<Argument>
)

data class State(
    val method: MethodDescription,
    val returnValue: ReturnValue,
    val argumentValues: Map<Argument, ArgumentValue>
)

data class ReturnValue(val value: String)

data class ArgumentValue(val value: String)