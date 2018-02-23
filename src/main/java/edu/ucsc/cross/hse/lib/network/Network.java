package edu.ucsc.cross.hse.lib.network;

import java.util.ArrayList;

import org.jgrapht.Graph;

public interface Network
{

	public Graph<Node, Connection> getTopology();

	public boolean establishConnection(Node self, Node target);

	public static ArrayList<Connection> getConnections(Node self)
	{
		return new ArrayList<Connection>(self.getNetwork().getTopology().edgesOf(self));
	}

	public static ArrayList<Connection> getDirectionalConnections(Node self, boolean incoming)
	{
		ArrayList<Connection> conns = new ArrayList<Connection>();
		for (Connection conn : self.getNetwork().getTopology().edgesOf(self))
		{
			if (!incoming && !conn.getTarget().equals(self))
			{
				conns.add(conn);
			} else if (incoming && conn.getTarget().equals(self))
			{
				conns.add(conn);
			}
		}
		return conns;
	}

}
