package com.freeing.common.support.reflection;

import com.freeing.common.support.reflection.invoker.AmbiguousMethodInvoker;
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

    private final Map<String, String> caseInsensitivePropertyMap = new HashMap<>();


    public Reflector(Class<?> clazz) {
        type = clazz;
        addDefaultConstructor(clazz);
        addGetMethods(clazz);
        addSetMethods(clazz);
        ddFields(clazz);
        readablePropertyNames = getMethods.keySet().toArray(new String[0]);
        writablePropertyNames = setMethods.keySet().toArray(new String[0]);
        for (String propName : readablePropertyNames) {
            caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
        }
        for (String propName : writablePropertyNames) {
            caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
        }
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

    private void addSetMethods(Class<?> clazz) {
        Map<String, List<Method>> conflictingSetters = new HashMap<>();
        Method[] methods = getClassMethods(clazz);
        Arrays.stream(methods).filter(method -> method.getParameterTypes().length == 1
                && PropertyNameHelper.isSetter(method.getName())
                && method.getReturnType() == void.class)
            .forEach(method -> addMethodConflict(conflictingSetters, method));
        resolveSetterConflicts(conflictingSetters);
    }

    private void ddFields(Class<?> clazz) {
        // TODO
    }


    private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
        for (Map.Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
            Method winner = null;
            String propName = entry.getKey();
            // 模棱两可的
            boolean isAmbiguous = false;
            // 由于 conflictingGetters 是根据方法对应的属性进行收集的，若 getter/setter 不按标准写冲突是可能的
            // 以子类为主
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
            addGetMethod(propName, winner, isAmbiguous);
        }
    }

    private void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
        for (String propName : conflictingSetters.keySet()) {
            List<Method> setters = conflictingSetters.get(propName);
            // getter 方法是否缓存有该属性的
            Class<?> getterType = getTypes.get(propName);
            boolean isSetterAmbiguous = false;
            Method match = null;
            for (Method setter : setters) {
                // setter 与 getter 方法属性匹配，setter 入参类型 == getter 返回值类型，由于 getter 已经处理过
                if (setter.getParameterTypes()[0].equals(getterType)) {
                    match = setter;
                    break;
                }
                // 或者只纯粹的有 setter 方法或者 setter 重载
                if (match == null) {
                    match = setter;
                    continue;
                }
                isSetterAmbiguous = true;
                // 查看入参
                Class<?> paramType1 = match.getParameterTypes()[0];
                Class<?> paramType2 = setter.getParameterTypes()[0];
                // 父子类关系 paramType1 可以转换为 paramType2（父类）
                if (paramType1.isAssignableFrom(paramType2)) {
                    match = setter;
                    continue;
                }
                if (paramType2.isAssignableFrom(paramType1)) {
                    continue;
                }
                GetterSetterMethodInvoker invoker =
                    new AmbiguousMethodInvoker(setter, "Ambiguous setters definition");
                setMethods.put(propName, invoker);
                setTypes.put(propName, setter.getReturnType());
            }
            if (!isSetterAmbiguous && match != null) {
                GetterSetterMethodInvoker invoker = new GetterSetterMethodInvoker(match);
                setMethods.put(propName, invoker);
                setTypes.put(propName, match.getReturnType());
            }
        }
    }

    private void addGetMethod(String name, Method method, boolean isAmbiguous) {
        if (isAmbiguous) {
            GetterSetterMethodInvoker invoker =
                new AmbiguousMethodInvoker(method, "Ambiguous setters definition");
            setMethods.put(name, invoker);
            setTypes.put(name, method.getReturnType());
            return;
        }
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
