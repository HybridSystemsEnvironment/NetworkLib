package edu.ucsc.cross.hse.lib.network;

import java.util.ArrayList;

import edu.ucsc.cross.hse.model.data.packet.Packet;

public interface Node
{

	public Network getNetwork();

	public Object getAddress();

	public ArrayList<Packet> getTransmittingBuffer();

	public ArrayList<Packet> getReceivingBuffer();

	public boolean deliverPacket(Packet packet);

	public boolean sendPacket(Packet packet);

}
