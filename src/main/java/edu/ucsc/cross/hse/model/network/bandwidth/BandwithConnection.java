package edu.ucsc.cross.hse.model.network.bandwidth;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.lib.network.Connection;
import edu.ucsc.cross.hse.lib.network.Node;
import edu.ucsc.cross.hse.model.data.packet.Packet;

/**
 * The ideal connection is a very simple graph edge that directly connects two
 * objects of class N. If you are connecting different types of objects you can
 * either use the Object class as the N argument or build a connection
 * interface. The mixed connectivity example demonstrates this.
 * 
 * @author Brendan Short
 *
 * @param <N>
 *            The class of the objects that are to be connected
 */
public class BandwithConnection extends DataStructure implements Connection
{

	public double maxBandwith; // maximum bandwith
	public Node source; // source vertex
	public Node target; // target vertex

	private HashMap<Packet, Double> transferBuffer;
	private HashMap<Packet, Double> startTime = new HashMap<Packet, Double>();
	private ArrayList<Packet> successfulTransfers = new ArrayList<Packet>();
	private ArrayList<Packet> failedTransfers = new ArrayList<Packet>();

	/**
	 * Construct connection from specified source to destination
	 * 
	 * @param sourceVertex
	 *            start vertex
	 * @param targetVertex
	 *            end vertex
	 */
	public BandwithConnection(Node sourceVertex, Node targetVertex, double bandwidth)
	{
		this.source = sourceVertex;
		this.target = targetVertex;
		maxBandwith = bandwidth;
		transferBuffer = new HashMap<Packet, Double>();
	}

	@Override
	public Node getSource()
	{
		return source;
	}

	@Override
	public Node getTarget()
	{
		return target;
	}

	@Override
	public boolean initiateTransmit(Packet packet)
	{
		decrementTimes();
		newPacket(packet);
		return true;
	}

	public void decrementTimes()
	{
		double time = HybridTime.getEnvironmentTime();

		for (Packet pack : transferBuffer.keySet())
		{
			Double start = startTime.get(pack);
			Double elapsed = time - start;
			Double transferred = elapsed * (maxBandwith / transferBuffer.size());
			Double remaining = transferBuffer.get(pack) - transferred;
			startTime.put(pack, time);
			transferBuffer.put(pack, remaining);
		}
	}

	public void newPacket(Packet packet)
	{
		double time = HybridTime.getEnvironmentTime();
		Double transmitTimeFull = packet.getTotalSize();
		startTime.put(packet, time);
		transferBuffer.put(packet, transmitTimeFull);

	}

	public boolean checkForCompletion()
	{
		decrementTimes();
		if (!transferBuffer.isEmpty())
		{
			for (Packet pack : transferBuffer.keySet())
			{
				if (0 > transferBuffer.get(pack))
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean handleCompletion()
	{
		decrementTimes();
		boolean completion = false;
		ArrayList<Packet> remove = new ArrayList<Packet>();
		for (Packet pack : transferBuffer.keySet())
		{
			if (0 >= transferBuffer.get(pack))
			{
				remove.add(pack);
				completion = true;
			}
		}
		for (Packet pack : remove)
		{
			successfulTransfers.add(pack);
			transferBuffer.remove(pack);
			startTime.remove(pack);
		}
		return completion;
	}

	@Override
	public ArrayList<Packet> getSuccessfulTransmissions()
	{
		// TODO Auto-generated method stub
		return successfulTransfers;
	}

	@Override
	public ArrayList<Packet> getFailedTransmissions()
	{
		// TODO Auto-generated method stub
		return failedTransfers;
	}
}
