package edu.ucsc.cross.hse.model.network.delayed;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class DelayedNetworkState extends DataStructure
{

	public double timer;

	public DelayedNetworkState()
	{
		timer = 0.0;
	}
}
