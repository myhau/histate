package com.michalfudala.histate.testing;

import com.speedment.common.mapstream.MapStream;
import net.bytebuddy.implementation.bind.annotation.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jooq.lambda.Unchecked;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;

import static java.util.stream.Collectors.joining;

public class PrintFunctionAllCalls {

    @RuntimeType
    public Object intercept(
            @Super
                    Object zuper,
            @This
                    Object thiz,
            @AllArguments
                    Object[] allArguments,
            @Origin
                    Method method,
            @SuperCall
                    Callable<?> callable
    ) throws Exception {
        Object result = callable.call();


//        System.out.println("METHOD CALL CONTEXT: " + prettyFieldsContext(zuper));
        System.out.println("METHOD CALL:" + prettyMethodCall(thiz, allArguments, method, result));

        return result;
    }

    public static String prettyFieldsContext(Object thiz) {
        Field[] fields = thiz.getClass().getDeclaredFields();

        MapStream<String, Object> fieldNameToValue = MapStream.fromValues(Arrays.stream(fields), Field::getName)
                .mapValue(field -> { field.setAccessible(true); return field; } )
                .mapValue(Unchecked.function(field -> field.get(thiz)));


        return fieldNameToValue
                .map((fieldName, fieldValue) -> fieldName + " = " + fieldValue)
                .collect(joining("\n"));
    }

    private static String objectToString(Object o) {
        return ReflectionToStringBuilder.toString(o, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String prettyMethodCall(Object thiz, Object[] arguments, Method method, Object returned) {

        String prettyArguments =
                Arrays.stream(arguments)
                        .map(arg -> objectToString(arg))
//                        .map(Object::toString)
                        .collect(joining(", "));

        String prettyCall =
                thiz.getClass().getName() + "." + method.getName() + "(" + prettyArguments + ") = " + objectToString(returned);

        return prettyCall;
    }

}
