package util;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

import sets.*;
import java.lang.reflect.*;

/**
 * Hides some of the intricacies of working with java reflection and provides some
 * useful helper methods for method calling.
 */
public class ReflectionHelper {

    /**
     * Changes strings of the form "camel_cased" to "camelCased".
     * Leaves initial underscores unchanged.
     */
    public String toCamelCase(String name) {
        int nrUnderscores = 0;
        StringBuilder builder = new StringBuilder(name);

        //remove initial underscores
        while(builder.charAt(0) == '_') {
            ++nrUnderscores;
            builder.deleteCharAt(0);
        }

        //camel case the rest of the string
        int index = builder.indexOf("_");

        while(index != -1) {
            builder.deleteCharAt(index);
            char c = builder.charAt(index);
            builder.setCharAt(index, Character.toUpperCase(c));
            index = builder.indexOf("_");
        }

        // nifty way to replicate characters
        String underscores = new String(new char[nrUnderscores]).replace("\0", "_");

        return underscores + builder.toString();
    }

    public String toClassCamelCase(String name) {
        String cc = toCamelCase(name);
        if (cc.length() > 0) {
            return Character.toUpperCase(cc.charAt(0)) + cc.substring(1);
        } else {
            return cc;
        }
    }

    public Object call(Class type, String name, Object... param) throws NoSuchMethodException {
        return call(type, null, name, Arrays.asList(param));
    }

    public Object call(Object obj, String name, Object... param) throws NoSuchMethodException {
        return call(obj.getClass(), obj, name, Arrays.asList(param));
    }

    public Object call(Class type, String name) throws NoSuchMethodException {
        return call(type, null, name, new ArrayList<Object>());
    }

    public Object call(Class type, String name, List<Object> param) throws NoSuchMethodException {
        return call(type, null, name, param);
    }

    public Object call(Object obj, String name) throws NoSuchMethodException {
        return call(obj.getClass(), obj, name, new ArrayList<Object>());
    }

    public Object call(Object obj, String name, List<Object> param) throws NoSuchMethodException {
        return call(obj.getClass(), obj, name, param);
    }

    /**
     * Wrapps the complexity of java reflection
     */
    private Object call(Class type, Object obj, String name, List<Object> param) throws NoSuchMethodException {
        Class[] paramtypes = new Class[param.size()];

        for(int i = 0; i < paramtypes.length; i++) {
            paramtypes[i] = makePrimitive(param.get(i).getClass());
        }
        try {
            Method method = findMethod(type, name, paramtypes);
            return method.invoke(obj, param.toArray());
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(type.getName() +
                    " doesn't contain the method: " + name + "( ");

            for(Class c : paramtypes) {
                sb.append( c.getName() + ", ");
            }

            sb.deleteCharAt(sb.length()-1);
            sb.deleteCharAt(sb.length()-1);
            sb.append(" )\n");

            throw new NoSuchMethodException(sb.toString());
        }
    }

    /**
     * Converts object classes with primitive alternatives when possible.
     */
    private Class makePrimitive(java.lang.Class type) {
        switch(type.getCanonicalName()) {
            case "java.lang.Boolean":
                return boolean.class;
            case "java.lang.Byte":
                return byte.class;
            case "java.lang.Short":
                return short.class;
            case "java.lang.Integer":
                return int.class;
            case "java.lang.Long":
                return long.class;
            case "java.lang.Float":
                return float.class;
            case "java.lang.Character":
                return char.class;
            case "java.lang.Void":
                return void.class;
            default:
                return type;
        }
    }

    private Method findMethod(Class<?> type, String name, Class<?>[] paramtypes) {
        try {
            return type.getMethod(name, paramtypes);
        } catch (NoSuchMethodException e) {
            return findMethodHelper(type, name, paramtypes, 0);
        }
    }

    // recursive, horrible complexity... might cause stack overflow
    private Method findMethodHelper(Class<?> type, String name, Class<?>[] paramtypes, int i) {
        if (i==paramtypes.length) {
            try {
                return type.getMethod(name, paramtypes);
            } catch (Exception e) {
                return null;
            }
        } else {
            for (Class<?> clazz : getSuperTypes(paramtypes[i])) {
                Class<?> tmp = paramtypes[i];
                paramtypes[i] = clazz;
                Method m = findMethodHelper(type, name, paramtypes, i+1);
                paramtypes[i] = tmp;
                if (m!=null) {
                    return m;
                }
            }
            return null;
        }
    }

    private Class<?>[] getSuperTypes(Class<?> type) {
        Class<?>[] superClasses = getSuperClasses(type).toArray(new Class<?>[]{});
        Class<?>[] interfaces = type.getInterfaces();
        Class<?>[] superTypes;
        if (type.isInterface()) {
            superTypes = new Class<?>[superClasses.length + interfaces.length + 1];
            superTypes[superTypes.length-1] = type;
        } else {
            superTypes = new Class<?>[superClasses.length + interfaces.length];
        }
        System.arraycopy(superClasses,0,superTypes,0,superClasses.length);
        System.arraycopy(interfaces,0,superTypes,superClasses.length,interfaces.length);
        return superTypes;
    }

    private List<Class<?>> getSuperClasses(Class<?> type) {
        Class<?> superClass = type.getSuperclass();
        List<Class<?>> list;
        if (superClass == null) {
            list = new LinkedList<>();
        } else {
            list = getSuperClasses(superClass);
        }
        list.add(type);
        return list;
    }

    public static void main(String[] args) throws Exception{
        model.Bracket.Builder<Integer> builder = new model.Bracket.Builder<>();
        Method m = new ReflectionHelper().findMethod(builder.getClass(), "sendTo", new Class<?>[]{String.class,TopMod.class});
        System.out.println(m);
    }
}
