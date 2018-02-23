package edu.ucsc.cross.hse.model.network.delayed;

public class DelayedNetworkParameters
{

	public double delayMin;
	public double delayMax;

	public DelayedNetworkParameters(double delay_min, double delay_max)
	{
		delayMin = delay_min;
		delayMax = delay_max;
	}

	public double getDelay()
	{
		return delayMin + Math.random() * (delayMax - delayMin);
	}
}
