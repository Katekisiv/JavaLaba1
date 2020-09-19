import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLengthCounter {
    @Test(expected = StringIndexOutOfBoundsException.class)
    public void testCount() {
        LengthCounter lengthCounter = new LengthCounter(",", "+");
        assertEquals("7+3", lengthCounter.count("\"abc,def\",abc"));
        assertEquals("5+4+3", lengthCounter.count("\"a\"bc,def\",abc"));
        assertEquals("5+2+2", lengthCounter.count("ab\"cd,d\",as"));
        lengthCounter.count("abc,abc, ,");
        assertEquals("0+1+1", lengthCounter.count("\"\", ,a"));

    }
}
