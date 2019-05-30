package nz.ac.ara.kev38.testapp2.modelcode;

import java.util.Arrays;

public class Calculator {
	public static double calculatePercentageChange(double dataPointA, double dataPointB)
	{
		double difference = dataPointB - dataPointA;
		double percentageChange = (difference / dataPointA) * 100;
		
		return percentageChange;
	}
	
	public static double calculateAbsoluteChange(double dataPointA, double dataPointB)
	{
		double difference = dataPointB - dataPointA;
		
		return difference;
	}
	
	public static double calculateMaximum(Double[] dataPoints)
	{
		Arrays.sort(dataPoints);
		return dataPoints[dataPoints.length - 1];
	}
	
	public static double calculateMinimum(Double[] dataPoints)
	{
		Arrays.sort(dataPoints);
		return dataPoints[0];
	}
	
	public static double calculateMean(Double[] dataPoints)
	{
		double sum = 0.0d;
		
		for (double dataPoint : dataPoints)
		{
			sum += dataPoint;
		}
		
		double mean = sum / dataPoints.length;
		
		return mean;
	}
}
