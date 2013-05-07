package util;

import java.util.ArrayList;
import java.lang.reflect.*;

import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class ReflectionHelperTest {
    static ReflectionHelper rh;

    @BeforeClass
    public static void setup() {
        rh = new ReflectionHelper();
    }

    @Test
    public void toCamelCase() {
        String underscored = "an_under_scored_string";
        assertEquals("anUnderScoredString", rh.toCamelCase(underscored));
    }

    @Test
    public void toCamelCase_LeadingUnderscore() {
        String underscored = "_an_under_scored_string";
        assertEquals("_anUnderScoredString", rh.toCamelCase(underscored));
    }

    @Test
    public void toCamelCase_SeveralLeadingUnderscores() {
        String underscored = "___an_under_scored_string";
        assertEquals("___anUnderScoredString", rh.toCamelCase(underscored));
    }

    @Test
    public void makePrimitive() {
        // makePrimitive is private but we can use reflection to access it anyway.
        try {
            Method method = ReflectionHelper.class.getDeclaredMethod("makePrimitive", java.lang.Class.class);
            method.setAccessible(true);
            Class c = (Class) method.invoke(rh, Integer.class);
            assertEquals(int.class, c);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void call() {
        String s = "this is a String";
        int l = 0;

        try {
            l = (int) rh.call(s, "length");
        } catch (NoSuchMethodException e) {}

        assertEquals(s.length(), l);
    }

    @Test
    public void call_static() {
        String s = "";

        try {
            s = (String) rh.call(String.class, "valueOf", 1337);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        assertEquals("1337", s);
    }
}
