package edu.ucsc.cross.hse.model.network.ideal;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
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
public class IdealConnection extends DataStructure implements Connection
{

	public Node source; // source vertex
	public Node target; // target vertex

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
	public IdealConnection(Node sourceVertex, Node targetVertex)
	{
		this.source = sourceVertex;
		this.target = targetVertex;

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
		target.deliverPacket(packet);
		return true;
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
