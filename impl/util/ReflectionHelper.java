package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.lang.reflect.*;

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
        if (name.equals("sendTo")) {
            System.out.println("hej");
            paramtypes[1] = SetModifier.class;
        }

        try {
            Method method = type.getMethod(name, paramtypes);
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
}
