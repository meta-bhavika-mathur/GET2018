package assignment2;

import java.util.*;

class JobScheduler
{
	int number_of_processes;
	double[][] process_info;

	JobScheduler(int num)
	{
		number_of_processes = num;
		process_info = new double[number_of_processes][2];
	}

	public double getArrivalTime(int process_no)
	{
		return process_info[process_no-1][0];
	}

	public double getBurstTime(int process_no)
	{
		return process_info[process_no-1][1];
	}

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
	
	public double getProcessWaitingTime(int process_no)
	{
		int n=process_no; 
		double waiting_time=0.0;
		
		if(n>1)
		{
			if(getArrivalTime(n) < getProcessCompletionTime(n-1))
			{
				waiting_time=getProcessTurnAroundTime(n) - getBurstTime(n);
			}
		}
		return waiting_time;
	}
	
	public double getProcessTurnAroundTime(int process_no)
	{
		double completion_time,arrival_time,turnaround_time;

		completion_time = getProcessCompletionTime(process_no);
		arrival_time = getArrivalTime(process_no);
		turnaround_time = completion_time - arrival_time;

		return turnaround_time; 
		
	}
	public double getAverageWaitingTime()
	{
			double sum = 0.0;
			double average_wait_time;
			for(int i=0; i<number_of_processes; i++)
			{
				sum += getProcessWaitingTime(i);
			}
			average_wait_time = sum/number_of_processes;
			
			return average_wait_time;
	}
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
	public void setAllProcessInfo()
	{
		
		Scanner in=new Scanner(System.in);

		for(int i=0 ; i<number_of_processes ; i++)
		{
			for(int j=0 ; j<2 ; j++)
			{
				if(j==0)
				{
					System.out.println("Enter arrival time(sec) for process "+(i+1)+":");  
					process_info[i][j]=in.nextDouble();
				}
				if(j==1)
				{
					System.out.println("Enter burst time(sec) for process "+(i+1)+":");  
					process_info[i][j]=in.nextDouble();
				}
			}
		}
	}
	public void getAllProcessInfo()
	{
		
		for(int i=0 ; i<number_of_processes ; i++)
		{
			for(int j=0 ; j<2 ; j++)
			{ 
				if(j==0)
				{
					System.out.println("Arrival Time of process "+ (i+1) + ":"+ process_info[i][j]);  
				}
				if(j==1)
				{
					System.out.println("Burst Time of process "+ (i+1) + ":"+ process_info[i][j]);  
				}
			}
		}
	}
}

class JobSchedulerDemo
{
	public static void main(String args[])
	{

		JobScheduler scheduler;
		Scanner in = new Scanner(System.in);
		int choice,n;
		System.out.println("Enter number of process:");
		int process = in.nextInt();
		scheduler = new JobScheduler(process);
		System.out.println("Choose an operation to perform:");
		do
		{
				System.out.println("1.Set all process info.");
				System.out.println("2.Get all process info.");
				System.out.println("3.Get Arrival Time of a process");
				System.out.println("4.Get Burst Time of a proces");
				System.out.println("5.Get Completion Time of a proces");
				System.out.println("6.Get Waiting Time of a proces");
				System.out.println("7.Get Turnaround Time of a proces");
				System.out.println("8.Get Average Waiting Time of a proces");
				System.out.println("9.Get Maximum Waiting Time Period of a proces");
				
				choice=in.nextInt();
				switch(choice)
				{
					case 1: scheduler.setAllProcessInfo();
						   	break;   	
					case 2: scheduler.getAllProcessInfo();
				   		    break;
				   	case 3: System.out.println("Enter process number:");
							n=in.nextInt();
							System.out.println(scheduler.getArrivalTime(n));
							break;
					case 4: System.out.println("Enter process number:");
							n=in.nextInt();
							System.out.println(scheduler.getBurstTime(n));
							break;
					case 5: System.out.println("Enter process number:");
							n=in.nextInt();
							System.out.println(scheduler.getProcessCompletionTime(n));
							break;
					case 6: System.out.println("Enter process number:");
							n=in.nextInt();
							System.out.println(scheduler.getProcessWaitingTime(n));
							break;
					case 7: System.out.println("Enter process number:");
							n=in.nextInt();
							System.out.println(scheduler.getProcessTurnAroundTime(n));
							break;
					case 8: System.out.println(scheduler.getAverageWaitingTime());
							break;
					case 9: System.out.println(scheduler.getMaximumWaitingTimePeriod());
							break; 
					default:System.out.println("Wrong choice!Try again");
				}
				System.out.println("Do you want to continue??:Type 1 for yes and 0 for NoList");
				choice=in.nextInt();
			}while(choice!=0); 	
	}
}