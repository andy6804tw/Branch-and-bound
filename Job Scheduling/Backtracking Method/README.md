
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


## BFS版本(BEST)

```java
package Scheduling;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
	static LinkedList<scheduling> list;
	static int N=0,maxProfit=Integer.MIN_VALUE;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new LinkedList<>();
		N = Integer.parseInt(scn.nextLine()); // job count
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" "); // (job name, profit, deadline)
			list.add(new scheduling(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		Scheduling();
		System.out.println(maxProfit);
	}

	public static void Scheduling() {
		// initial variable
		int cost = 0, upperBound = Integer.MAX_VALUE, bound = 0, profit = 0, arr[], checked[];
		int count = 1;
		Queue<List<Integer>> queue = new LinkedList<>();
		queue.offer(new LinkedList<Integer>()); // offer()方法用來在佇列後端加入物件
		while (!queue.isEmpty()) {
			List<Integer> subset = queue.poll(); // poll()方法用來取出佇列前端物件
			// Best first search(最佳優先搜尋)
			for (int i = 0; i < N; i++) {
				cost = 0;
				bound = 0;
				profit = 0;
				arr = new int[N];
				checked = new int[N];
				if (subset.size() == 0 || subset.get(subset.size() - 1) < i + 1) {
					List<Integer> nextSubset = new LinkedList<Integer>(subset);
					nextSubset.add(i + 1);
					// 加總profit(並檢查是否可以執行工作)
					for (int j = 0; j < nextSubset.size(); j++) {
						for (int k = list.get(nextSubset.get(j) - 1).deadline - 1; k >= 0; k--) {
							if (arr[k] == 0) {
								arr[k] = list.get(nextSubset.get(j) - 1).profit; // slot sign
								profit += list.get(nextSubset.get(j) - 1).profit;
								checked[nextSubset.get(j) - 1] = 1; // 目前工作被指派(標記1)
								break;
							}
						}
					}
					// 取得目前最大profit
					if (profit > maxProfit) {
						maxProfit = profit;
					}
					// 計算 cost (成本)
					for (int j = i - 1; j >= 0; j--) {
						if (checked[j] == 0) {
							cost += list.get(j).profit;
						}
					}
					// 計算 bound (上限)
					for (int j = N - 1; j >= 0; j--) {
						if (j != i && checked[j] == 0) {
							bound += list.get(j).profit;
						}
					}
					System.out.println(nextSubset + " " + list.get(i).job + "  cost: " + cost + "  upper:" + bound
							+ " total:" + profit + " count: " + (count++) + " arr: " + arr[0] + " " + arr[1] + " "
							+ arr[2]);
					// 成本不能大於最大上限
					if (cost > upperBound)
						continue;
					// 最大上限為每次 bound 最小值
					if (bound < upperBound) {
						upperBound = bound;
					}
					// 寫入 queue
					queue.offer(nextSubset);
				}
			}
		}
	}
}
```
