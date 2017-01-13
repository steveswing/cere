package com.briarshore.rule;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;

/**
 * Class: CustomPureJavaReflectionProvider
 */
public class CustomPureJavaReflectionProvider extends PureJavaReflectionProvider {
    private static final Logger log = LoggerFactory.getLogger(CustomPureJavaReflectionProvider.class);

    private static boolean assignableFrom(final Class<?>[] parameterTypes, final Class cls) {
        for (final Class<?> parameterType : parameterTypes) {
            if (parameterType.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    public void writeField(final Object object, final String fieldName, final Object value, final Class definedIn) {
        if (null != definedIn) {
            final Set<Method> methods = new HashSet<>(Arrays.asList(definedIn.getMethods())).stream().filter(
                    m -> null != m && null != value && m.getName().startsWith("set") && m.getName().endsWith(fieldName) && assignableFrom(m.getParameterTypes(), value.getClass())).collect(
                    Collectors.toSet());

//            final Set<Method> methods = Sets.filter(Sets.newHashSet(definedIn.getMethods()), new Predicate<Method>() {
//                @Override
//                public boolean test(@Nullable final Method m) {
//                    return null != m && null != value && Strings.startsWith(m.getName(), "set") && Strings.endsWith(m.getName(), Strings.capitalize(fieldName)) && assignableFrom(m
// .getParameterTypes(),
//                            value.getClass());
//                }
//
//                private boolean assignableFrom(final Class<?>[] parameterTypes, final Class cls) {
//                    for (final Class<?> parameterType : parameterTypes) {
//                        if (parameterType.isAssignableFrom(cls)) {
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });

            if (!methods.isEmpty()) {
                for (final Method method : methods) {
                    try {
                        log.debug("calling {}", method.getName());
                        method.invoke(object, value);
                        return;
                    } catch (final IllegalAccessException | InvocationTargetException ignored) {
                    }
                }
            } else {
                final Optional<Field> f = Arrays.stream(definedIn.getDeclaredFields()).filter(n -> n.getName().equalsIgnoreCase(fieldName)).findFirst();

//                final Field f = FieldUtils.getDeclaredField(definedIn, fieldName, true);
//                if (null != f) {
                if (f.isPresent()) {
                    if (Collection.class.isAssignableFrom(f.get().getType())) {
                        log.debug("setter for {} not found", fieldName);
                    }
                } else {
                    log.debug("getDeclaredField {} {} is not found", definedIn.getSimpleName(), fieldName);
                }
            }
        } else {
            return;
        }

        super.writeField(object, fieldName, value, definedIn);
    }
}
