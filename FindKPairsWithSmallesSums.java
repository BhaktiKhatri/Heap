package Heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 373. Find K Pairs with Smallest Sums
 * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
 * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
 * Example 1: Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
 * Output: [[1,2],[1,4],[1,6]] 
 * Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 * Example 2: Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
 * Output: [1,1],[1,1]
 * Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 * Example 3: Input: nums1 = [1,2], nums2 = [3], k = 3
 * Output: [1,3],[2,3]
 * Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
 * Explanation and Code from: https: @meganlee //leetcode.com/problems/find-k-pairs-with-smallest-sums/discuss/84551/simple-Java-O(KlogK)-solution-with-explanation
 * The run time complexity is O(kLogk) since que.size <= k and we do at most k loop
 */

public class FindKPairsWithSmallesSums {

	 public static List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
		 	System.out.println("nums1: "+Arrays.toString(nums1)+" nums2: "+Arrays.toString(nums2)+" k: "+k);
		 
	        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> (a[0] + a[1]) - (b[0] + b[1]));	// min queue, sorted by pair sum
	        List<int[]> res = new ArrayList<int[]>();
	        
	        int N1 = nums1.length;
	        int N2 = nums2.length;
	        System.out.println("N1: "+N1+" N2: "+N2);
	        
	        if(N1 == 0 || N2 == 0) {
	        	return res; // no pairs to form, just return an empty res list
	        }
	        System.out.println("Math.min(N1, k): "+Math.min(N1, k));
	        
	        for(int i=0; i < Math.min(N1, k); i++) {
	        	System.out.println("i: "+i+" nums1[i]: "+nums1[i]+" nums2[0]: "+nums2[0]);
	        	
	        	queue.offer(new int[]{nums1[i], nums2[0], 0});	// offer initial pairs {num1, num2, index_of_num2}
	        }
	        System.out.println("Math.min(N1 * N2, k): "+Math.min(N1 * N2, k));
	        
	        // get 1st k element into result, each time, offer potential better pairs into queue; if there r not enough pair, just return all pairs
	        for(int i=0; i<Math.min(N1 * N2, k); i++) {
	        	System.out.println("i: "+i);
	        	
	        	// get the best pair and put into res
	            int[] cur = queue.poll();		     
	            System.out.println("cur: "+Arrays.toString(cur));
	            
	            res.add(new int[]{cur[0], cur[1]});
	            
	            // next better pair could with be A: {after(num1), num2} or B: {num1. after(num2)}
	            // for A, we've already added top possible k into queue, so A is either in the queue already, or not qualified
	            // for B, it might be a better choice, so we offer it into queue
	            System.out.println("cur[2]: "+cur[2]+" N2: "+N2);
	            
	            if (cur[2] < N2 - 1 ) { // still at least one element after num2 in array nums2
	                int idx = cur[2] + 1;
	                System.out.println("idx: "+idx+" nums2[idx]: "+nums2[idx]);
	                queue.offer(new int[]{cur[0], nums2[idx], idx});
	            }
	        }
	        return res;
	    }

	
	public static void main(String[] args) {
		int[] nums1 = {1,7,11};
		int[] nums2 = {2,4,6};
		int k = 3;
		List<int[]> result = kSmallestPairs(nums1, nums2, k);
		for(int[] res: result) {
			System.out.print(" "+Arrays.toString(res));
		}
	}

}
