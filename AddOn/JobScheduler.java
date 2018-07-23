package assignment1;

class JobScheduler
{
    int number_of_processes;
    double[][] process_info;

    JobScheduler(int num, double array[][])
    {
        number_of_processes = num;
        process_info = array;
    }

    /**
     * To find arrival time of a process 
     * @param process_no, process number or id for identity of a process
     * @return arrival time of process 'process_no'
     */
    public double getArrivalTime(int process_no)
    {
        return process_info[process_no-1][0];
    }

    /**
     * To find burst time of a process 
     * @param process_no, process number or id for identity of a process
     * @return burst time of process 'process_no'
     */
    public double getBurstTime(int process_no)
    {
        return process_info[process_no-1][1];
    }

    /**
     * To find completion time time of a process 
     * @param process_no, process number or id for identity of a process
     * @return total completion time of process 'process_no'
     */
    public double getProcessCompletionTime(int process_no)
    {
        int first_process = 1;
        double initial_time = getArrivalTime(first_process);
        double final_time = getBurstTime(first_process);
        
        for(int i=2 ; i <= process_no ; i++ )
        {
            if(getArrivalTime(i) > final_time)
            {
                final_time = getArrivalTime(i);
            }
            final_time += getBurstTime(i);
        }
        final_time = final_time - initial_time;
        return final_time;
    }
    
    /**
     * To find waiting time of a process 
     * @param process_no, process number or id for identity of a process
     * @return arrival time of process 'process_no'
     */
    public double getProcessWaitingTime(int process_no)
    {
        int n = process_no; 
        double waiting_time = 0.0;
        
        if(n>1)
        {
            if(getArrivalTime(n) < getProcessCompletionTime(n-1))
            {
                waiting_time = getProcessTurnAroundTime(n) - getBurstTime(n);
            }
        }
        return waiting_time;
    }
    
    /**
     * To find turn around time of a process 
     * @param process_no, process number or id for identity of a process
     * @return turn around time of process 'process_no'
     */
    public double getProcessTurnAroundTime(int process_no)
    {
        double completion_time,arrival_time,turnaround_time;

        completion_time = getProcessCompletionTime(process_no);
        arrival_time = getArrivalTime(process_no);
        turnaround_time = completion_time - arrival_time;

        return turnaround_time;   
    }
    
    /**
     * To find average waiting time of processes 
     * @return average waiting time of processes
     */
    public double getAverageWaitingTime()
    {
            double sum = 0.0;
            double average_wait_time;
            
            for(int i=0; i < number_of_processes; i++)
            {
                sum += getProcessWaitingTime(i);
            }
            average_wait_time = sum/number_of_processes;
            
            return average_wait_time;
    }
    
    /**
     * To find maximum waiting time period among all the processes 
     * @return maximum waiting time period among all the processes
     */
    public double getMaximumWaitingTimePeriod()
    {
        double waiting_time[] = new double[number_of_processes];
        int max_index;
        
        for(int i=1; i<=number_of_processes; i++)
        {
            waiting_time[i-1] = getProcessWaitingTime(i);
        }
        max_index = 0;
        for(int i=1 ; i<number_of_processes ; i++)
        {
            if(waiting_time[i] > max_index)
                max_index = i;
        }
        
        return waiting_time[max_index];
    }
    
    /**
     * To set information of a process.
     * @param process_num, process number to set data.
     * @param time, arrival time if equal 0 or burst time if equal to one.
     * @param data, either arrival time or burst time of process 'process_num'
     */
    public void setProcessInfo(int process_num, int time, double data)
    {
        if(time == 0)
            process_info[process_num][time] = data;
        if(time == 1)
            process_info[process_num][time] = data;
    }
    
    /**
     * To get information of a process.
     * @param process_num, process number to set data.
     * @param time, arrival time if equal 0 or burst time if equal to one.
     * @return data, either arrival time or burst time of process 'process_num'
     */
    public double getProcessInfo(int process_num, int time)
    {
        double info = 0.0;
        if( time == 0)
            info = process_info[ process_num ][time];  
        if(time == 1)
            info = process_info[process_num][time];
        return info;  
    }
}