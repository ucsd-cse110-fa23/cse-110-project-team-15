// package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

// import org.junit.Test;
// import static org.junit.Assert;

public class Test1 {

    @Test
    public void testStudentCompareTo() {
        assertEquals("hi", "hi");
    }

    @Test
    public void wrongTest() {
        assertEquals("hi", "hi");
        assertEquals("hi", "he");
    }
    @Test
    public void test2() {
        assertEquals("hi", "hi");
        assertEquals("hi", "he");
    }
}
