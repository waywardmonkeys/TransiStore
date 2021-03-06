package com.fasterxml.transistore.cmd;

import java.util.*;

import com.fasterxml.storemate.shared.IpAndPort;

/**
 * Skeletal copy of the service configuration, used by local command-line
 * tools to get basic information, but without requiring a dependency to
 * definition (plus ignoring stuff that is not relevant to client side).
 */
public class SkeletalServiceConfig
{
    public Wrapper ts = new Wrapper();
    
    public static class Wrapper {
        public Cluster cluster = new Cluster();
    }

    public static class Cluster
    {
        public int clusterKeyspaceSize;

        // // Static config:

        public List<Node> clusterNodes = new ArrayList<Node>();
        
        public void addNode(IpAndPort ip) {
            clusterNodes.add(new Node(ip));
        }
    }

    public static class Node
    {
        public IpAndPort ipAndPort;
        public int keyRangeStart;
        public int keyRangeLength;

        public Node() { }
        
        // Alternate constructor for "simple" end point defs:
        public Node(String str) {
            this(new IpAndPort(str));
        }
        public Node(IpAndPort ip) {
            ipAndPort = ip;
        }
    }
}
