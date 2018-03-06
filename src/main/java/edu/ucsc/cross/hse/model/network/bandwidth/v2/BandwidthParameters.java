package edu.ucsc.cross.hse.model.network.bandwidth.v2;

import edu.ucsc.cross.hse.core.variable.RandomVariable;

public class BandwidthParameters
{

	public double constantBandwidth = 10000;

	public RandomVariable randomBandwidth = new RandomVariable(100, 10000);

	public BandwidthConfiguration configuration = BandwidthConfiguration.CONSTANT;

	public BandwidthParameters(double constant, BandwidthConfiguration conf)
	{
		constantBandwidth = constant;
		configuration = conf;
	}

	public double getBandwidth()
	{
		double bandwidth = this.constantBandwidth;
		switch (configuration)
		{
		case CONSTANT:
			bandwidth = this.constantBandwidth;
			break;
		case RANDOM:
			bandwidth = this.randomBandwidth.generateRandom();
			break;
		default:
			break;
		}
		return bandwidth;
	}

	public static enum BandwidthConfiguration
	{
		CONSTANT(
			"The constant bandwidth will be used"),
		RANDOM(
			"The bandwidth for each connection will be chosen at random from the range specified");

		public final String description;

		private BandwidthConfiguration(String desc)
		{
			this.description = desc;
		}
	}

}
