package edu.ucsc.cross.hse.model.network.ideal;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class IdealNetworkState extends DataStructure
{

	public double packetsTransmitted;

	public IdealNetworkState()
	{
		packetsTransmitted = 0.0;
	}
}
