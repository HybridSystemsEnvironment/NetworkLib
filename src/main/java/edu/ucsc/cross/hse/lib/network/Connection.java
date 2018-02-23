package edu.ucsc.cross.hse.lib.network;

import java.util.ArrayList;

import edu.ucsc.cross.hse.model.data.packet.Packet;

public interface Connection
{

	public boolean initiateTransmit(Packet packet);

	public Node getSource();

	public Node getTarget();

	public ArrayList<Packet> getSuccessfulTransmissions();

	public ArrayList<Packet> getFailedTransmissions();
}
