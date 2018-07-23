package assignment2;

import static org.junit.Assert.*;

import org.junit.Test;

public class MarksheetTest {
    
    double grades[] = new double[]{20, 30, 40, 50, 60};
    Marksheet sheet = new Marksheet(5, grades );

    //Postive test case for finding average of grades
    @Test
    public void testAverageOfGrades() {
        assertEquals(40, sheet.averageOfGrades(), 0);
    }
    
    @Test
    public void testMinimumGrade() {
        assertEquals(20, sheet. minimumGrade(), 0);
    }
   
    @Test
    public void testMaximumGradeGrades() {
        assertEquals(60, sheet. maximumGrade(), 0);
    }
   
    @Test
    public void testPercentagePassed() {
        assertEquals(60, sheet.percentagePassed(), 0);
    }
   

}
