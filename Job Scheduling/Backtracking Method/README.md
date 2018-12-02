
# Job Scheduling 回溯法

## 使用DFS 

```java
import java.util.ArrayList;
import java.util.Scanner;

class scheduling {
	String job;
	int profit, deadline;

	public scheduling(String job, int profit, int deadline) {
		this.job = job;
		this.profit = profit;
		this.deadline = deadline;
	}
}

public class Main {
	static ArrayList<scheduling> list;
	static String solution[];
	static int arr[],total=0,max=0,N=0;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new ArrayList<>();
		N = Integer.parseInt(scn.nextLine());
		arr= new int[N];
		solution = new String[N];
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" ");
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		backtracking(0, 0);
		System.out.println(max);
	}

	public static void backtracking(int n, int size) {
		for (int i = 0; i < N; i++) {
			System.out.print(solution[i]+ " " );
			total+=arr[i];
			if(total>max)
				max=total;
		}
		
		System.out.print(" "+total);
		System.out.println();
		for (int i = 0; i < N; i++) {
			System.out.print(arr[i]+ " " );
		}
		System.out.println();
		total=0;
		int j;
			for (; n < N; n++) {
				for ( j = list.get(n).deadline - 1; j >= 0; j--) {
			        if (arr[j] == 0) {
			          arr[j] = list.get(n).profit;
			          break;
			        }
			      }
				solution[size] = list.get(n).job;
				backtracking(n + 1, size + 1);
				solution[size] = "=";
				if(j>=0)
				arr[j] = 0;
			}
	}
}
```

## DFS優化版
```java
package Scheduling;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Scanner;

class scheduling {
	String job;
	int profit, deadline;

	public scheduling(String job, int profit, int deadline) {
		this.job = job;
		this.profit = profit;
		this.deadline = deadline;
	}
}

public class Main {
	static ArrayList<scheduling> list;
	static String solution[];
	static int arr[],total=0,max=0,N=0;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new ArrayList<>();
		N = Integer.parseInt(scn.nextLine());
		arr= new int[N];
		solution = new String[N];
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" ");
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		backtracking(0, 0);
		System.out.println(max);
	}

	public static void backtracking(int n, int size) {
		if(n==N) {
			for (int i = 0; i < N; i++) {
				System.out.print(solution[i]+ " " );
				total+=arr[i];
				if(total>max)
					max=total;
			}
			
			System.out.print(" "+total);
			System.out.println();
			for (int i = 0; i < N; i++) {
				System.out.print(arr[i]+ " " );
			}
			System.out.println();
			total=0;
		}
		int j;
			for (; n < N; n++) {
				for ( j = list.get(n).deadline - 1; j >= 0; j--) {
			        if (arr[j] == 0) {
			          arr[j] = list.get(n).profit;
			          break;
			        }
			      }
				solution[size] = list.get(n).job;
				backtracking(n + 1, size + 1);
				solution[size] = "=";
				if(j>=0)
				arr[j] = 0;
			}
	}
}
```


## 有promising的DFS(有bug)
```java

import java.util.ArrayList;
import java.util.Scanner;

class scheduling {
	String job;
	int profit, deadline;

	public scheduling(String job, int profit, int deadline) {
		this.job = job;
		this.profit = profit;
		this.deadline = deadline;
	}
}

public class Main {
	static ArrayList<scheduling> list;
	static String solution[] = new String[7];
	static int arr[] = new int[7],total=0,max=0;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new ArrayList<>();
		int n = Integer.parseInt(scn.nextLine());
		for (int i = 0; i < n; i++) {
			String arr[] = scn.nextLine().split(" ");
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		backtracking(0, 0);
		System.out.println(max);
	}

	public static void backtracking(int n, int size) {
		for (int i = 0; i < 7; i++) {
			System.out.print(solution[i]+ " " );
			total+=arr[i];
			if(total>max)
				max=total;
		}
		
		System.out.print(" "+total);
		System.out.println();
		for (int i = 0; i < 7; i++) {
			System.out.print(arr[i]+ " " );
		}
		System.out.println();
		total=0;
		if(promising(n)) {
			int j;
			for (; n < 7; n++) {
				for ( j = list.get(n).deadline - 1; j >= 0; j--) {
			        if (arr[j] == 0) {
			          arr[j] = list.get(n).profit;
			          break;
			        }
			      }
				solution[size] = list.get(n).job;
				backtracking(n + 1, size + 1);
				solution[size] = "NULL";
				if(j>=0)
				arr[j] = 0;
			}	
		}
	}
	public static boolean promising(int n) {
		System.out.println(n);
		if(n==7)
			return false;
		for (int j = list.get(n).deadline - 1; j >= 0; j--) {
	        if (arr[j] == 0) {
	        	return true;
	        }
	      }	
		return false;
	}
}

```


## BFS版本(尚未加上格式)

```java
package Scheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS2 {
	static int nums[] = { 1, 2, 3 ,4};

	public static void main(String[] args) {
		List<List<Integer>> list = subsets();
		//System.out.println(list);
	}

	public static List<List<Integer>> subsets() {
        // List vs ArrayList （google）
        List<List<Integer>> results = new LinkedList<>();
        
        if (nums == null) {
            return results; // 空列表
        }
        
        Arrays.sort(nums);
        
        // BFS
        Queue<List<Integer>> queue = new LinkedList<>();
       queue.offer(new LinkedList<Integer>());
        int count=1,cost=0,upper=Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
        	List<Integer> subset = queue.poll();
        	//if(subset.indexOf(nums.length)>=0) {
        		results.add(subset);
        		
        	//}
            
            for (int i = 0; i < nums.length; i++) {
            	cost=0;
                if (subset.size() == 0 || subset.get(subset.size() - 1) < nums[i]) {
                	
                    List<Integer> nextSubset = new LinkedList<Integer>(subset);
                    nextSubset.add(nums[i]);
                    //演算法
                    for(int j=nums[i]-1;j>=0;j--) {
                    	if(nextSubset.indexOf(nums[j])<0) {
                    		cost+=nums[j];
                    	}
                    }
                    //寫入 queue
                    queue.offer(nextSubset);System.out.println(nextSubset+" "+nums[i]+"  "+cost );
                }
            }
            System.out.println(count++);
        }
        return results;
    }
}
```

站存
```java
package Scheduling;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Scanner;

class scheduling {
	int job;
	int profit, deadline;

	public scheduling(int job, int profit, int deadline) {
		this.job = job;
		this.profit = profit;
		this.deadline = deadline;
	}
}

public class Main {
	static ArrayList<scheduling> list;
	static String solution[];
	static int arr[],total=0,max=0,N=0;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new ArrayList<>();
		N = Integer.parseInt(scn.nextLine());
		arr= new int[N];
		solution = new String[N];
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" ");
			list.add(new scheduling(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		subsets();
		System.out.println(max);
	}

	public static void subsets() {
	       Queue<List<Integer>> queue = new LinkedList<>();
	       queue.offer(new LinkedList<Integer>());
	        while (!queue.isEmpty()) {
	        	List<Integer> subset = queue.poll();
	        	// 取得序列計算scheduling
	        	if(subset.indexOf(list.size())>=0) {
	        		System.out.println(subset);
	        	}
	            
	            for (int i = 0; i < list.size(); i++) {
	                if (subset.size() == 0 || subset.get(subset.size() - 1) < i+1) {
	                    List<Integer> nextSubset = new LinkedList<Integer>(subset);
	                    nextSubset.add( i+1);
	                    queue.offer(nextSubset);
	                }
	            }
	        }
	    }
}
```
