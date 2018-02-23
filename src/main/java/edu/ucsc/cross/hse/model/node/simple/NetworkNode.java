package edu.ucsc.cross.hse.model.node.simple;

import java.util.ArrayList;

import edu.ucsc.cross.hse.lib.network.Network;
import edu.ucsc.cross.hse.lib.network.Node;
import edu.ucsc.cross.hse.model.data.packet.Packet;

public class NetworkNode implements Node
{

	private Network network;
	private ArrayList<Packet> transmit;
	private ArrayList<Packet> receive;

	public NetworkNode(Network network)
	{
		this.network = network;
		transmit = new ArrayList<Packet>();
		receive = new ArrayList<Packet>();
	}

	@Override
	public Network getNetwork()
	{
		// TODO Auto-generated method stub
		return network;
	}

	@Override
	public Object getAddress()
	{
		// TODO Auto-generated method stub
		return this.toString();
	}

	@Override
	public ArrayList<Packet> getTransmittingBuffer()
	{
		// TODO Auto-generated method stub
		return transmit;
	}

	@Override
	public ArrayList<Packet> getReceivingBuffer()
	{
		// TODO Auto-generated method stub
		return receive;
	}

	@Override
	public boolean deliverPacket(Packet packet)
	{
		receive.add(packet);
		return true;
	}

	@Override
	public boolean sendPacket(Packet packet)
	{
		return false;
	}

}
