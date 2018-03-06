package edu.ucsc.cross.hse.model.network.bandwidth.v2;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class BandwidthNetworkState extends DataStructure
{

	public double packetsInTransmission;
	//public double nodeCount;
	//public double connectionCount;

	public BandwidthNetworkState()
	{
		packetsInTransmission = 0.0;
		// nodeCount = 0.0;
		// connectionCount = 0.0;
	}
}
