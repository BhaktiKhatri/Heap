package Heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * 253. Meeting Rooms II
 * https://leetcode.com/problems/meeting-rooms-ii/description/
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
 * For example, Given [[0, 30],[5, 10],[15, 20]], return 2
 * Explanation and Code from: @jeantimex https://leetcode.com/problems/meeting-rooms-ii/discuss/67857/AC-Java-solution-using-min-heap
 * Medium
 * Google, Facebook, Snapchat
 * Average time complexity is O(nlogn)
 */

  // Definition for an interval.
  class Interval {
      int start;
      int end;
      
      Interval() { 
    	  start = 0; 
    	  end = 0; 
      }
      
      Interval(int s, int e) { 
    	  start = s; 
    	  end = e; 
      }
  }
 
public class MeetingRooms2 {

	/*
	 	Here is my thought. whenever there is a start meeting, we need to add one room. But before adding rooms, we check to see if any previous
	 	meeting ends, which is why we check start with the first end. When the start is bigger than end, it means at this time one of the previous
	 	meeting ends, and it can take and reuse that room. Then the next meeting need to compare with the second end because the first end's room
	 	is already taken. One thing is also good to know:
	 	meetings start is always smaller than end. Whenever we pass a end, one room is released.
	*/
	public static int minMeetingRooms(Interval[] intervals) {
	    if (intervals == null || intervals.length == 0)
	        return 0;
	        
	    for(Interval i: intervals)
	    	System.out.println(i.start+" , "+i.end);
	    
	    // Sort the intervals by start time
	    Arrays.sort(intervals, new Comparator<Interval>() {
	    	
	        public int compare(Interval a, Interval b) { 
	        	System.out.println("a: "+a.start+" , "+a.end+" b: "+b.start+" , "+b.end+" a.start - b.start: "+(a.start - b.start));
	        	return a.start - b.start; //positive -> b first then a
	        }
	    });
	    
	    System.out.println(Arrays.toString(intervals));
	    
	    // Use a min heap to track the minimum end time of merged intervals
	    PriorityQueue<Interval> heap = new PriorityQueue<Interval>(intervals.length, new Comparator<Interval>() {
	    	
	        public int compare(Interval a, Interval b) { 
	        	System.out.println("a: "+a.start+" , "+a.end+" b: "+b.start+" , "+b.end+" a.end - b.end: "+(a.end - b.end));
	        	return a.end - b.end; //positive -> b first then a; negative -> a first and then b
	        }
	    });
	    
	    System.out.println("heap: "+heap);
	    
	    // start with the first meeting, put it to a meeting room
	    heap.offer(intervals[0]);
	    
	    for(int i=1; i<intervals.length; i++) {
	    	System.out.println("heap: "+heap);
	    	
	        // get the meeting room that finishes earliest
	        Interval interval = heap.poll();
	        System.out.println("interval: "+interval);
	        
	        System.out.println("intervals[i].start: "+intervals[i].start+" interval.end: "+interval.end);
	        
	        if(intervals[i].start >= interval.end) {
	            // if the current meeting starts right after there's no need for a new room, merge the interval
	            interval.end = intervals[i].end;
	        } 
	        else {
	            // otherwise, this meeting needs a new room
	            heap.offer(intervals[i]);
	        }
	        System.out.println("heap: "+heap);
	        
	        // don't forget to put the meeting room back
	        heap.offer(interval);
	        System.out.println("heap: "+heap);
	    }
	    
	    return heap.size();
	}
	
	//Refer this: @czonzhu https://leetcode.com/problems/meeting-rooms-ii/discuss/67857/AC-Java-solution-using-min-heap
	/*
	 * Max tracks the maximum number of rooms that were required at any one time.The final queue size will be reduced for each reservation that 
	 * doesn't have a conflict. Suppose at one time, 3 rooms were required, then just because there are 3 reservations without conflict, the final
	 * queue size would reduce to zero. Zero is incorrect.
	 */
	public static int minMeetingRooms1(Interval[] intervals) {
        if(intervals == null || intervals.length == 0) {
        	return 0;
        }
        
        Arrays.sort(intervals, (a, b) -> (a.start - b.start));
        int max = 0;
        
        //during offer adds interval with small end value on top in queue and during pop removes interval with small end value first 
        PriorityQueue<Interval> queue = new PriorityQueue<>(intervals.length, (a, b) -> (a.end - b.end));	
        
        for(int i=0; i<intervals.length; i++) {
        	System.out.println("intervals[i].start: "+intervals[i].start+" intervals[i].end: "+intervals[i].end);
            
        	while(!queue.isEmpty() && intervals[i].start >= queue.peek().end) {
            	System.out.println("queue.peek().start: "+queue.peek().start+" queue.peek().end: "+queue.peek().end);
                queue.poll();
            }
            
        	queue.offer(intervals[i]);
            max = Math.max(max, queue.size());
        }
        return max;
    }
	
	public static void main(String[] args) {
		Interval interval1 = new Interval(0, 30);
		Interval interval2 = new Interval(5, 10);
		Interval interval3 = new Interval(15, 20);
		Interval interval4 = new Interval(40, 45);
		
		Interval[] intervals = {interval1, interval2, interval3, interval4};
		
		System.out.println(minMeetingRooms1(intervals));
	}

}
