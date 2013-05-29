import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import util.ReflectionHelperTest;
import sets.SetTest;

@RunWith(Suite.class)
@SuiteClasses({ ReflectionHelperTest.class, SetTest.class})
public class AllTests {

}
