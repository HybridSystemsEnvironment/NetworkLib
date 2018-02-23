package edu.ucsc.cross.hse.model.network.bandwidth;

import org.jgrapht.EdgeFactory;

import edu.ucsc.cross.hse.lib.network.Connection;
import edu.ucsc.cross.hse.lib.network.Node;

/**
 * The connection factory provides instructions for creating a connection. This
 * is an ideal network so there are no conditions for creating a connection, but
 * this could be implemented as part of a hybrid system where connections have
 * dynamic conditions.
 * 
 * @author beshort
 *
 * @param <V>
 */
public class BandwithConnectionFactory implements EdgeFactory<Node, Connection>
{

	public BandwidthParameters parameters;

	/**
	 * Create a connection between the two vertices with bandwidth
	 * 
	 * @param source
	 *            start vertex
	 * 
	 * @param destination
	 *            end vertex
	 */
	@Override
	public BandwithConnection createEdge(Node sourceVertex, Node targetVertex)
	{

		return new BandwithConnection(sourceVertex, targetVertex, parameters.getBandwidth());
	}

	public BandwithConnectionFactory(BandwidthParameters parameters)
	{
		this.parameters = parameters;
	}
}
