package edu.ucsc.cross.hse.model.network.ideal;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.lib.network.Connection;
import edu.ucsc.cross.hse.lib.network.Network;
import edu.ucsc.cross.hse.lib.network.Node;
import edu.ucsc.cross.hse.model.data.packet.Packet;
import edu.ucsc.cross.hse.model.data.packet.PacketStatus;

/**
 * This hybrid system implements an ideal network where all transmitted packets
 * are delivered instantaneously.
 * 
 * @author beshort
 *
 */
public class IdealNetwork extends HybridSystem<IdealNetworkState> implements Network
{

	public DirectedPseudograph<Node, Connection> topology;

	public IdealNetwork()
	{
		super(new IdealNetworkState());
		topology = new DirectedPseudograph<Node, Connection>(new IdealConnectionFactory());
	}

	@Override
	public boolean C(IdealNetworkState x)
	{
		// TODO Auto-generated method stub
		return true && !D(x);
	}

	@Override
	public void F(IdealNetworkState x, IdealNetworkState x_dot)
	{
		x_dot.packetsTransmitted = 0.0;
	}

	@Override
	public boolean D(IdealNetworkState x)
	{
		for (Node node : topology.vertexSet())
		{
			if (node.getTransmittingBuffer().size() > 0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void G(IdealNetworkState x, IdealNetworkState x_plus)
	{
		Integer packets = 0;
		for (Node node : topology.vertexSet())
		{
			for (Packet packet : node.getTransmittingBuffer())
			{
				Node target = (Node) packet.getHeader().getTarget();
				System.out.println(packet);
				if (!topology.getEdge(node, target).initiateTransmit(packet))
				{
					packet.updateStatus(PacketStatus.FAILED);
					node.deliverPacket(packet);
				} else
				{
					packets++;
				}

			}
			node.getTransmittingBuffer().clear();
		}
		x_plus.packetsTransmitted += packets;
		System.out.println("Transmitted: " + x_plus.packetsTransmitted);
	}

	@Override
	public Graph<Node, Connection> getTopology()
	{
		// TODO Auto-generated method stub
		return topology;
	}

	@Override
	public boolean establishConnection(Node self, Node target)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
