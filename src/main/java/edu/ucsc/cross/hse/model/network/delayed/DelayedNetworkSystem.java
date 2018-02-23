package edu.ucsc.cross.hse.model.network.delayed;

import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.lib.network.Connection;
import edu.ucsc.cross.hse.lib.network.Network;
import edu.ucsc.cross.hse.lib.network.Node;
import edu.ucsc.cross.hse.model.network.ideal.IdealConnectionFactory;

public class DelayedNetworkSystem extends HybridSystem<DelayedNetworkState> implements Network
{

	public DirectedPseudograph<Node, Connection> topology;

	DelayedNetworkParameters parameters;

	public HashMap<Node, Double> transmitTimes;

	public DelayedNetworkSystem(DelayedNetworkState state, DelayedNetworkParameters parameters)
	{
		super(state);
		this.parameters = parameters;
		transmitTimes = new HashMap<Node, Double>();
		topology = new DirectedPseudograph<Node, Connection>(new IdealConnectionFactory());
	}

	@Override
	public boolean C(DelayedNetworkState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void F(DelayedNetworkState x, DelayedNetworkState x_dot)
	{
		x_dot.timer = 1.0;
	}

	@Override
	public boolean D(DelayedNetworkState x)
	{
		boolean transmit = false;
		for (Node link : topology.vertexSet())
		{
			if (transmitTimes.containsKey(link))
			{
				if (transmitTimes.get(link).doubleValue() <= x.timer)
				{
					return true;
				}
			}
			if (link.getTransmittingBuffer().size() > 0 && !transmitTimes.containsKey(link))
			{
				transmit = true;
			}

		}
		return transmit;
	}

	@Override
	public void G(DelayedNetworkState x, DelayedNetworkState x_plus)
	{
		for (Node link : topology.vertexSet())
		{
			if (link.getTransmittingBuffer().size() > 0 && !transmitTimes.containsKey(link))
			{
				transmitTimes.put(link, parameters.getDelay() + x.timer);
			}
		}
		transmitMessages(x);
	}

	private void transmitMessages(DelayedNetworkState x)
	{
		ArrayList<Node> completed = new ArrayList<Node>();
		for (Node delivery : transmitTimes.keySet())
		{
			Double deliveryTime = transmitTimes.get(delivery);
			if (deliveryTime <= x.timer)
			{
				delivery.getNetwork().getTopology()
				.getEdge(delivery, (Node) delivery.getTransmittingBuffer().get(0).getHeader().getTarget())
				.initiateTransmit(delivery.getTransmittingBuffer().get(0));
				delivery.getTransmittingBuffer().remove(0);
				completed.add(delivery);
			}
		}
		for (Node delivery : completed)
		{
			transmitTimes.remove(delivery);
		}
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
