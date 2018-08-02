package assignment2;

import static org.junit.Assert.*;
import org.junit.Test;

public class MarksheetTest {
    
    double grades[] = new double[]{20, 30, 40, 50, 60};
    Marksheet sheet = new Marksheet(5, grades );

    // Positive test cases for finding average of grades
    @Test
    public void testAverageOfGrades1() {
        assertEquals(40, sheet.averageOfGrades(), 0);
    }
    
    // Negative test case for finding average of grades
    @Test
    public void testAverageOfGrades2() {
        assertNotEquals(30, sheet.averageOfGrades(), 0);
    }
    
    // Positive test case for finding minimum of grades
    @Test
    public void testMinimumGrade1() {
        assertEquals(20, sheet. minimumGrade(), 0);
    }
    
    // Negative test case for finding minimum of grades
    @Test
    public void testMinimumGrade2() {
        assertNotEquals(25, sheet. minimumGrade(), 0);
    }
   
    // Positive test case for finding maximum of grades
    @Test
    public void testMaximumGradeGrades1() {
        assertEquals(60, sheet. maximumGrade(), 0);
    }

    // Negative test case for finding maximum of grades
    @Test
    public void testMaximumGradeGrades2() {
        assertNotEquals(50, sheet. maximumGrade(), 0);
    }
   
    // Positive test case for finding percentage of passed students
    @Test
    public void testPercentagePassed1() {
        assertEquals(60, sheet.percentagePassed(), 0);
    }
    
    // Negative test case for finding percentage of passed students
    @Test
    public void testPercentagePassed2() {
        assertNotEquals(55, sheet.percentagePassed(), 0);
    }
}
