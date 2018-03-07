package edu.ucsc.cross.hse.model.network.bandwidth.v2;

import java.util.ArrayList;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.lib.network.Connection;
import edu.ucsc.cross.hse.lib.network.Network;
import edu.ucsc.cross.hse.lib.network.Node;
import edu.ucsc.cross.hse.model.data.packet.Packet;

/**
 * The ideal network is defined as an extension of the DirectedMultigraph
 * because it allows me to add additional features if needed. In addition, it
 * simplifies the naming scheme.
 * 
 * @author Brendan Short
 *
 * @param <V>
 *            the class of the objects that are connected in this network
 */

public class BandwidthNetwork extends HybridSystem<BandwidthNetworkState> implements Network
{

	public DirectedPseudograph<Node, Connection> topology;
	public BandwidthParameters params;

	public BandwidthNetwork(BandwidthNetworkState state, BandwidthParameters params)
	{
		super(state, params);
		topology = new DirectedPseudograph<Node, Connection>(new BandwithConnectionFactory(params));
	}

	@Override
	public boolean C(BandwidthNetworkState arg0)
	{

		return false;
	}

	@Override
	public boolean D(BandwidthNetworkState arg0)
	{
		return tasksPending();
	}

	@Override
	public void F(BandwidthNetworkState arg0, BandwidthNetworkState arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void G(BandwidthNetworkState arg0, BandwidthNetworkState arg1)
	{
		//performOrganizationalTasks();
		performCleanupTasks();
		performOrganizationalTasks();
		for (Connection conn : topology.edgeSet())
		{
			BandwithConnection con = (BandwithConnection) conn;
			if (con.handleCompletion())//(conn.successfulTransfers().size() > 0 || conn.failedTransfers().size() > 0)
			{
			}

		}
		//performCleanupTasks();
	}

	public boolean tasksPending()
	{
		for (Node node : topology.vertexSet())
		{
			if (node.getTransmittingBuffer().size() > 0)
			{
				return true;
			}
		}
		for (Connection conn : topology.edgeSet())
		{
			BandwithConnection con = (BandwithConnection) conn;
			if (con.checkForCompletion())//(conn.successfulTransfers().size() > 0 || conn.failedTransfers().size() > 0)
			{
				return true;
			}

		}
		return false;
	}

	public boolean performOrganizationalTasks()
	{
		for (Node node : topology.vertexSet())
		{
			if (node.getTransmittingBuffer().size() > 0)
			{
				ArrayList<Packet> outgoing = new ArrayList<Packet>(node.getTransmittingBuffer());
				for (Packet packet : outgoing)
				{
					Node target = (Node) packet.getHeader().getTarget();
					if (topology.containsEdge(node, target))
					{
						Connection conn = topology.getEdge(node, target);

						if (conn.initiateTransmit(packet))
						{

							node.getTransmittingBuffer().remove(packet);
						} else
						{

							//node.getReceivingBuffer().add(packet);
						}
					}
				}
			}
		}
		return false;
	}

	public boolean performCleanupTasks()
	{

		for (Connection conn : topology.edgeSet())
		{
			if (conn.getSuccessfulTransmissions().size() > 0)
			{
				conn.getTarget().getReceivingBuffer().addAll(conn.getSuccessfulTransmissions());
			}
			if (conn.getFailedTransmissions().size() > 0)
			{
				conn.getSource().getReceivingBuffer().addAll(conn.getFailedTransmissions());
			}

			conn.getSuccessfulTransmissions().clear();
			conn.getFailedTransmissions().clear();
		}
		return false;
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