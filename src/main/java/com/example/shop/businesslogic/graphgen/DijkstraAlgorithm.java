package com.example.shop.businesslogic.graphgen;

import java.util.ArrayList;

public class DijkstraAlgorithm
{
    private Graph g;
    private Node startNode;
    private boolean[] visited;
    private int[] dist;
    private int[] pred;

    public DijkstraAlgorithm(Graph g, Node startNode)
    {
        this.g = g;
        this.startNode = startNode;
        init();
    }

    // Initialisierung
    private void init()
    {
        dist = new int[g.getNodes().size()+1];
        visited = new boolean[g.getNodes().size()+1];
        pred = new int[g.getNodes().size()+1];

        for (int i = 0; i < dist.length; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        visited[0] = true;
        dist[startNode.getObjectId()] = 0;
    }

    // Mainloop
    public int[] execute()
    {
        for (int i = 0; i < dist.length; i++)
        {
            int next = minVertex(dist, visited);
            if (next != -1)
            {
                Node nextNode = g.getNodeById(next);
				/*
				System.out.println("next: " + next);
				System.out.println("nextNode: " + nextNode);
				System.out.println(nextNode.getObjectId());
				System.out.println(visited.length);
				*/
                visited[nextNode.getObjectId()] = true;
                int[] reachable = new int[nextNode.getNeightbours().size()];
                Node[] reachableNodes = new Node[nextNode.getNeightbours().size()];
                ArrayList<Edge> edgesFromNext = nextNode.getEdges();
                for (int x = 0; x < nextNode.getNeightbours().size(); x++)
                {
                    reachable[x] = nextNode.getNeightbours().get(x).getObjectId();
                    reachableNodes[x] = nextNode.getNeightbours().get(x);
                }
                for (int j = 0; j < reachable.length; j++)
                {
                    int edgeWeight = 0;
                    int v = reachable[j];
                    Node v2 = reachableNodes[j];
                    for (Edge edge : edgesFromNext)
                    {
                        if (edge.containsNode(v2))
                        {
                            edgeWeight = edge.getWeight();
                        }
                    }
                    int d = dist[next] + edgeWeight;
                    if (dist[v] > d)
                    {
                        dist[v] = d;
                        pred[v] = next;
                    }
                }
            }
        }
		/*
		System.out.println("pred");
		for(int i = 0; i < pred.length; i++)
		{
			System.out.println(pred[i]);
		}
		*/
        startNode.setShortestPaths(pred);
        startNode.setShortestPathsLengths(dist);
        return dist;
    }

    // NextNode selection
    private static int minVertex(int[] dist, boolean[] v)
    {
		/*
		for (int i = 0; i < dist.length; i++)
		{
			System.out.println("dist: " + dist[i] + ", visited: " + v[i]);
		}
		*/
        int x = Integer.MAX_VALUE;
        int y = -1;
        for (int i = 0; i < dist.length; i++)
        {
            if (!v[i] && dist[i] < x)
            {
                y = i;
                x = dist[i];
            }
        }
        return y;
    }
    //Reinitialisiert den Algorithmus auf gleichem Graphen, wenn die startnode geändert wird, damit
    //der Algorithmus direkt wieder ausgeführt werden kann.
    public void setStartNode(Node startnode)
    {
        this.startNode = startnode;
        init();
    }
}
