package assignment1;

import static org.junit.Assert.*;

import org.junit.Test;

public class JobSchedulerTest {
    
    double array[][] = new double[][]{{0, 10}, {6, 20}, {60, 10}, {110, 5}};
    JobScheduler obj = new JobScheduler(4, array);
    
    // Positive test cases for obtaining arrival time of a process
    @Test
    public void testGetArrivalTime1() {
        assertEquals(6, obj.getArrivalTime(2), 0);
    }
    
    @Test
    public void testGetArrivalTime2() {
        assertEquals(110, obj.getArrivalTime(4), 0);
    }
    
    // Negative test cases for obtaining arrival time of a process
    @Test
    public void testGetArrivalTime3() {
        assertNotEquals(5, obj.getArrivalTime(1), 0);
    }
    
    @Test
    public void testGetArrivalTime4() {
        assertNotEquals(60, obj.getArrivalTime(4), 0);
    }
    
    // Positive test cases for obtaining burst time of a process
    @Test
    public void testBurstTime1() {
        assertEquals(20, obj.getBurstTime(2), 0);
    }
    
    @Test
    public void testBurstTime2() {
        assertEquals(5, obj.getBurstTime(4), 0);
    }
    
    // Negative test cases for obtaining burst time of a process
    @Test
    public void testBurstTime3() {
        assertNotEquals(5, obj.getBurstTime(1), 0);
    }
    
    @Test
    public void testBurstTime4() {
        assertNotEquals(60, obj.getBurstTime(4), 0);
    }
    
    // Positive test cases for obtaining completion time of a process
    @Test
    public void testGetCompletionTime1() {
        assertEquals(30, obj.getProcessCompletionTime(2), 0);
    }
    
    @Test
    public void testGetCompletionTime2() {
        assertEquals(115, obj.getProcessCompletionTime(4), 0);
    }
    
    // Negative test cases for obtaining completion time of a process
    @Test
    public void testGetCompletionTime3() {
        assertNotEquals(5, obj.getProcessCompletionTime(1), 0);
    }
    
    @Test
    public void testGetCompletionTime4() {
        assertNotEquals(60, obj.getProcessCompletionTime(4), 0);
    }
    
    // Positive test cases for obtaining waiting time of a process
    @Test
    public void testGetProcessWaitingTime1() {
        assertEquals(4, obj.getProcessWaitingTime(2), 0);
    }
    
    @Test
    public void testGetProcessWaitingTime2() {
        assertEquals(0, obj.getProcessWaitingTime(4), 0);
    }
    
    // Negative test cases for obtaining waiting time of a process
    @Test
    public void testGetProcessWaitingTime3() {
        assertNotEquals(5, obj.getProcessWaitingTime(1), 0);
    }
    
    @Test
    public void testGetProcessWaitingTime4() {
        assertNotEquals(60, obj.getProcessWaitingTime(4), 0);
    }
    
    // Positive test cases for obtaining turn around time of a process
    @Test
    public void testGetProcessTurnAroundTime1() {
        assertEquals(24, obj.getProcessTurnAroundTime(2), 0);
    }
    
    @Test
    public void testGetProcessTurnAroundTime2() {
        assertEquals(5, obj.getProcessTurnAroundTime(4), 0);
    }
    
    // Negative test cases for obtaining turn around time of a process
    @Test
    public void testGetProcessTurnAroundTime3() {
        assertNotEquals(5, obj.getProcessTurnAroundTime(1), 0);
    }
    
    @Test
    public void testGetProcessTurnAroundTime4() {
        assertNotEquals(60, obj.getProcessTurnAroundTime(4), 0);
    }
    
    // Positive test cases for calculating average of processes
    @Test
    public void testGetAverageWaitingTime1() {
        assertEquals(1, obj.getAverageWaitingTime(), 0);
    }
   
    // Negative test cases for calculating average of processes
    @Test
    public void testGetAverageWaitingTime2() {
        assertNotEquals(5, obj.getAverageWaitingTime(), 0);
    }
    
    // Positive test cases for calculating maximum waiting time period of processes
    @Test
    public void testGetMaximumWaitingTimePeriod1() {
        assertEquals(4, obj.getMaximumWaitingTimePeriod(), 0);
    }
   
    // Negative test cases for calculating maximum waiting time period of processes
    @Test
    public void testGetMaximumWaitingTimePeriod2() {
        assertNotEquals(5, obj.getMaximumWaitingTimePeriod(), 0);
    }
}
