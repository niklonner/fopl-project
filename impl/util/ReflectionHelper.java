package util;

import java.lang.reflect.*;

public class ReflectionHelper {

    /**
     * Changes strings of the form "camel_cased" to "camelCased".
     * Works correctly with initial underscores e.g. "_camel_cased"
     */
    public String toCamelCase(String name) {
        int initialUnderscores = 0;
        StringBuilder builder = new StringBuilder(name);

        //remove initial underscores
        while(builder.charAt(0) == '_') {
            ++initialUnderscores;
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
        String underscores = new String(new char[initialUnderscores]).replace("\0", "_");

        return underscores + builder.toString();
    }
}
