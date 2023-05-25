package com.freeing.common.support.reflection;

import com.freeing.common.support.reflection.invoker.GetterSetterMethodInvoker;
import com.freeing.common.support.reflection.property.PropertyNameHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yanggy
 */
public class Reflector {

    private final Class<?> type;
    private String[] readablePropertyNames;
    private String[] writablePropertyNames;
    private final Map<String, Object> setMethods = new HashMap<>();
    private final Map<String, Object> getMethods = new HashMap<>();
    private final Map<String, Class<?>> setTypes = new HashMap<>();
    private final Map<String, Class<?>> getTypes = new HashMap<>();
    private Constructor<?> defaultConstructor;

    public Reflector(Class<?> clazz) {
        type = clazz;
        addDefaultConstructor(clazz);
        addGetMethods(clazz);
    }

    private void addDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] allConstruct = clazz.getDeclaredConstructors();
        if (allConstruct.length == 1) {
            defaultConstructor = allConstruct[0];
            return;
        }
        Arrays.stream(allConstruct).filter(constructor -> constructor.getParameterTypes().length == 0)
            .findAny()
            .ifPresent(constructor -> this.defaultConstructor = constructor);
    }

    private void addGetMethods(Class<?> clazz) {
        Method[] methods = getClassMethods(clazz);
        // 冲突确实是可能的存在的，如：子类存在getter方法 String getTemplate() 而父类 Object getTemplate()，这种情况在 getClassMethods 是无法去重的
        // 又或者就参数不同其他方法签名都相同
        Map<String, List<Method>> conflictingGetters = new HashMap<>();
        Arrays.stream(methods).filter(method -> method.getParameterTypes().length == 0
                && method.getReturnType() != void.class
                && PropertyNameHelper.isGetter(method.getName()))
            .forEach(method -> addMethodConflict(conflictingGetters, method));

        resolveGetterConflicts(conflictingGetters);
    }

    private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
        for (Map.Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
            Method winner = null;
            String propName = entry.getKey();
            // 模棱两可的
            boolean isAmbiguous = false;
            // 由于 conflictingGetters 是根据方法对应的属性进行收集的，若 getter/setter 不按标准写冲突是可能的
            // 以返回值类型最多的那个为最终方法
            for (Method candidate : entry.getValue()) {
                if (winner == null) {
                    winner = candidate;
                    continue;
                }
                Class<?> winnerType = winner.getReturnType();
                Class<?> candidateType = candidate.getReturnType();
                // getter 方法返回值类型相同方法名相同，则存在 getter 方法不按标准写，如方法有参数
                if (candidateType.equals(winnerType)) {
                    if (!boolean.class.equals(candidateType)) {
                        isAmbiguous = true;
                        break;
                    } else if (candidate.getName().startsWith("is")) {
                        winner = candidate;
                    }
                } else if (candidateType.isAssignableFrom(winnerType)) {
                    // 返回值类型是父子类情况，winnerType 是父类
                } else if (winnerType.isAssignableFrom(candidateType)) {
                    // 返回值类型是父子类情况，candidateType 是父类
                    winner = candidate;
                } else {
                    // 该 Type 的父子类中存在有相同方法签名但是返回值类型没有任何关系的方法
                    isAmbiguous = true;
                    break;
                }
            }
            if (isAmbiguous) {
                throw new IllegalArgumentException("Illegal overloaded getter method with ambiguous types: "
                    + entry.getValue());
            }
            addGetMethod(propName, winner);
        }
    }

    private void addGetMethod(String name, Method method) {
        getMethods.put(name, new GetterSetterMethodInvoker(method));
        getTypes.put(name, method.getReturnType());
    }

    private void addMethodConflict(Map<String, List<Method>> conflictingMethods, Method method) {
        String name = PropertyNameHelper.methodToProperty(method.getName());
        if (isValidPropertyName(name)) {
            List<Method> list = conflictingMethods.computeIfAbsent(name, k -> new ArrayList<>());
            list.add(method);
        }
    }

    /**
     *  获取此类和任何超类中声明的所有方法包括静态方法
     *
     * @param clazz Class
     * @return
     */
    private Method[] getClassMethods(Class<?> clazz) {
        // key=方法签名
        HashMap<String, Method> uniqueMethodMap = new HashMap<>();
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            Method[] declaredMethods = currentClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                String signature = getSignature(method);
                if (!method.isBridge() && !uniqueMethodMap.containsKey(signature)) {
                    uniqueMethodMap.put(signature, method);
                }
            }
            // interface 接口方法处理，因为该类也可能是抽象的
            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                Method[] anInterfaceMethods = anInterface.getDeclaredMethods();
                for (Method method : anInterfaceMethods) {
                    String signature = getSignature(method);
                    if (!method.isBridge() && !uniqueMethodMap.containsKey(signature)) {
                        uniqueMethodMap.put(signature, method);
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        Collection<Method> methods = uniqueMethodMap.values();
        return methods.toArray(new Method[0]);

    }

    /**
     * 获取方法签名
     * 返回值类型#方法名:参数类型1,参数类型2,...
     *
     * @param method
     * @return
     */
    private String getSignature(Method method) {
        StringBuilder builder = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (returnType != void.class) {
            builder.append(returnType.getName()).append('#');
        }
        builder.append(method.getName());
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            builder.append(i == 0 ? ":" : ",")
                .append(parameterTypes[i].getName());

        }
        return builder.toString();
    }

    private boolean isValidPropertyName(String name) {
        return !(name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name));
    }
}
