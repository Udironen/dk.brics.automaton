package test.java.bricsTests;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import test.java.bricsTests.regexOpps.*;

@RunWith(Suite.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Suite.SuiteClasses({
        ComplementTests.class,
        ConcatinationTests.class,
        IntersectionTests.class,
        KleeneStarTests.class,
        UnionTests.class,

        IsEmptyTests.class,
        MinusTests.class,
        OptionalTests.class,
        RunTests.class,
        SubsetOfTests.class
})


public class BricsTestsSuite {


}
