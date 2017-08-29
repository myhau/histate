package com.michalfudala.histate.testing

import net.bytebuddy.implementation.bind.annotation.*
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.apache.commons.lang3.builder.ToStringStyle.*
import java.lang.reflect.Method
import java.util.concurrent.Callable


class StatesRepository {

  private val states: MutableList<State> = ArrayList()

  fun findByMethodName(name: String): State? =
      states.find { it.method.methodName == name }


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