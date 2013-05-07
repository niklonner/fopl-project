package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Assert;

public class ReflectionHelperTest {
  @Test
  public void testMultiply() {
      String underscored = "an_under_scored_string";
      ReflectionHelper rh = new ReflectionHelper();
      Assert.assertEquals(rh.toCamelCase(underscored), "anUnderScoredString");
  }
}
