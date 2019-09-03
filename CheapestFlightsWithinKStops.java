package Heap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * 787. Cheapest Flights Within K Stops
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
 * Now given all the cities and flights, together with starting city src and the destination dst, your task is to find the cheapest price from
 * src to dst with up to k stops. If there is no such route, output -1.
 * Code from: @lee215 https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/115541/JavaPython-Priority-Queue-Solution
 */

public class CheapestFlightsWithinKStops {

	/*
	 * It happen to be the same idea of Dijkstra's algorithm, but we need to keep the path.
	 * 
	 * He is constructing graph node object to be used in the following BFS. (0, src, k + 1) stands for (price, station, numOfStops + 1). 
	 * During BFS, he uses price to keep track of the current price, and use station to determine if the current node we are visiting is
	 * the destination,
	 * and also use the numOfStops to keep track of how many stops are there in order to reach to this station
	 */
    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        System.out.println("n: "+n+" src: "+src+" dst: "+dst+" k: "+k);
        
        for(int[] f: flights) {
        	System.out.println("f: "+Arrays.toString(f)+" prices: "+prices);
        	
            if(!prices.containsKey(f[0])) { 
            	prices.put(f[0], new HashMap<>());
            }
            prices.get(f[0]).put(f[1], f[2]);
        }
        
        System.out.println("prices: "+prices);
        
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));
        pq.add(new int[] {0, src, k + 1});
        System.out.println("pq: "+pq);
        
        while(!pq.isEmpty()) {
        	System.out.println("pq: "+pq);
        	
            int[] top = pq.remove();
            System.out.println("top: "+Arrays.toString(top));
            
            int price = top[0];
            int source = top[1];
            int stops = top[2];
        
            System.out.println("price: "+price+" source: "+source+" stops: "+stops+" dst: "+dst);
            
            if(source == dst) 
            	return price;
            
            if(stops > 0) {
                Map<Integer, Integer> adj = prices.getOrDefault(source, new HashMap<>());
                System.out.println("adj: "+adj);
                
                for(int a : adj.keySet()) {
                	System.out.println("a: "+a+" adj.get(a): "+adj.get(a)+" price: "+price);
                    pq.add(new int[] {price + adj.get(a), a, stops - 1});
                }
            }
        }
        return -1;
    }

	
	public static void main(String[] args) {
		int n = 3;
		int[][] flights = {{0,1,100},{1,2,100},{0,2,500}};
		int src = 0;
		int dst = 2;
		int k = 0;
		
		System.out.println(findCheapestPrice(n, flights, src, dst, k));
	}

}
