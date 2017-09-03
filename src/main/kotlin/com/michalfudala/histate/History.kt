package com.michalfudala.histate

import net.bytebuddy.implementation.bind.annotation.*
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.apache.commons.lang3.builder.ToStringStyle.*
import java.lang.reflect.Method
import java.util.concurrent.Callable

data class Argument(val name: String, val type: String)

data class MethodDescription(
    val packageName: String,
    val methodName: String,
    val returnType: String,
    val arguments: List<Argument>
) {
  companion object {
    fun from(name: String, returnType: String, arguments: List<Argument>): MethodDescription {
      val packageName = name.substringBefore(".")
      val methodName = name.substringAfter(".")

      return MethodDescription(packageName, methodName, returnType, arguments)
    }
  }
}

data class State(
    val method: MethodDescription,
    val returnValue: ReturnValue,
    val argumentValues: Map<Argument, ArgumentValue>
)

data class ReturnValue(val value: String)

data class ArgumentValue(val value: String)


class StatesRepository {

  companion object {
    val instance: StatesRepository = StatesRepository()
  }

  private val states: MutableList<State> = ArrayList()

  fun findByMethodName(name: String): List<State> =
      states.filter { it.method.methodName == name }

  fun findByMethodDescription(description: MethodDescription): List<State> =
      states.filter { it.method == description }


  fun add(state: State) {
    states.add(state)
  }


  @RuntimeType
  @Throws(Exception::class)
  fun intercept(
      @Super
      zuper: Any,
      @This
      thiz: Any,
      @AllArguments
      allArguments: Array<Any>,
      @Origin
      method: Method,
      @SuperCall
      callable: Callable<*>
  ): Any {

    val methodDescription =
        MethodDescription(
            zuper.javaClass.`package`.name,
            method.name,
            method.returnType.name,
            method.parameters.map { Argument(it.name, it.type.name) }
        )


    val namesZippedWithValues = method.parameters.zip(allArguments, { parameter, value -> Pair(Argument(parameter.name, parameter.type.name), ArgumentValue(prettyObject(value))) })

    val argumentValues = namesZippedWithValues.associate { it }

    val returnValue = ReturnValue(prettyObject(callable.call()))

    return State(methodDescription, returnValue, argumentValues)
  }

  private fun prettyObject(o: Any) = ReflectionToStringBuilder.toString(o, SHORT_PREFIX_STYLE)

}