## BFS版本(BEST)

```java
package Scheduling;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
	static LinkedList<scheduling> list;
	static int [] solution;
	static int N=0,maxProfit=Integer.MIN_VALUE;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new LinkedList<>();
		solution = new int [N];
		N = Integer.parseInt(scn.nextLine()); // job count
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" "); // (job name, profit, deadline)
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		Scheduling();
		System.out.println("max profit: "+maxProfit);
		// print selected job sequence
		for(int i=0;i<solution.length;i++) {
			if(solution[i]!=0) {
				System.out.print(list.get(solution[i]-1).job+" ");
			}
		}
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
							//	arr[k] = list.get(nextSubset.get(j) - 1).profit; // slot sign
								arr[k] = nextSubset.get(j); // slot sign
								profit += list.get(nextSubset.get(j) - 1).profit;
								checked[nextSubset.get(j) - 1] = 1; // 目前工作被指派(標記1)
								break;
							}
						}
					}
					// 取得目前最大profit
					if (profit > maxProfit) {
						maxProfit = profit;
						solution=arr;
					}
					
//					for (int j = i - 1; j >= 0; j--) {
//						if (checked[j] == 0) {
//							cost += list.get(j).profit;
//						}
//					}
					
					for (int j = N - 1; j >= 0; j--) {
						// 計算 cost (成本)
						if (checked[j] == 0&&j<i) {
							cost += list.get(j).profit;
						}
						// 計算 bound (上限)
						if (j != i && checked[j] == 0) {
							bound += list.get(j).profit;
						}
					}
//					System.out.println(nextSubset + " " + list.get(i).job + "  cost: " + cost + "  upper:" + bound
//							+ " total:" + profit + " count: " + (count++) + " arr: " + arr[0] + " " + arr[1] + " "
//							+ arr[2]);
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


## BST 有 Node 結點
```java
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

class Node {
	int bound;
	int cost;
	int profit;
	List<Integer> list;
	int arr[];
	int checked[];

	public Node(int bound, int cost, int profit, List<Integer> list, int arr[], int checked[]) {
		this.bound = bound;
		this.cost = cost;
		this.profit = profit;
		this.list = list;
		this.arr = arr;
		this.checked = checked;
	}
}

public class Main {
	static LinkedList<scheduling> list;
	static int[] solution;
	static int N = 0, maxProfit = Integer.MIN_VALUE;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new LinkedList<>();
		solution = new int[N];
		N = Integer.parseInt(scn.nextLine()); // job count
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" "); // (job name, profit, deadline)
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		Scheduling();
		System.out.println("max profit: " + maxProfit);
		// print selected job sequence
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] != 0) {
				System.out.print(list.get(solution[i] - 1).job + " ");
			}
		}
	}

	public static void Scheduling() {
		// initial variable
		int cost = 0, upperBound = Integer.MAX_VALUE, bound = 0, profit = 0, level = 0, arr[], checked[];
		int count = 1;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(new Node(0, 0, 0, new LinkedList<>(), new int[N], new int[N])); // offer()方法用來在佇列後端加入物件
		while (!queue.isEmpty()) {
			Node subNode = queue.poll(); // poll()方法用來取出佇列前端物件
			List<Integer> sublist = subNode.list; // 取得目前等待工作序列名單
			// Best first search(最佳優先搜尋)
			for (int i = 0; i < N; i++) {
				cost = 0;
				bound = 0;
				profit = subNode.profit;
				arr = subNode.arr.clone();
				checked = subNode.checked.clone();
				if (sublist.size() == 0 || sublist.get(sublist.size() - 1) < i + 1) {
					List<Integer> nextSubset = new LinkedList<Integer>(sublist);
					nextSubset.add(i + 1);
					for (int k = list.get(i).deadline - 1; k >= 0; k--) {
						if (arr[k] == 0) {
							arr[k] = i + 1; // slot sign
							profit += list.get(i).profit;
							checked[i] = 1; // 目前工作被指派(標記1)
							break;
						}
					}

					if (profit > maxProfit) {
						maxProfit = profit;
						solution = arr;
					}
					for (int j = N - 1; j >= 0; j--) {
						// 計算 cost (成本)
						if (checked[j] == 0 && j < i) {
							cost += list.get(j).profit;
						}
						// 計算 bound (上限)
						if (j != i && checked[j] == 0) {
							bound += list.get(j).profit;
						}
					}
					System.out.println(nextSubset + " " + list.get(i).job + "  cost: " + cost + "  upper:" + bound
							+ " profit: " + profit + " count: " + (count++) + " arr: " + arr[0] + " " + arr[1] + " "
							+ arr[2] + " " + arr[3] + "  check: " + checked[0] + " " + checked[1] + " " + checked[2]
							+ " " + checked[3]);
					// 成本不能大於最大上限
					if (cost > upperBound)
						continue;
					// 最大上限為每次 bound 最小值
					if (bound < upperBound) {
						upperBound = bound;
					}
					// 寫入 queue
					queue.offer(new Node(bound, cost, profit, nextSubset, arr, checked));
				}
			}
		}
	}
}
```

## 此方法跟同學討論(跟原方法類似)
profit計算目前成本
bound計算目前成本+未來可行的前k大個

```java
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

class Node {
	int bound;
	int cost;
	int profit;
	List<Integer> list;
	int arr[];
	int checked[];

	public Node(int bound, int cost, int profit, List<Integer> list, int arr[], int checked[]) {
		this.bound = bound;
		this.cost = cost;
		this.profit = profit;
		this.list = list;
		this.arr = arr;
		this.checked = checked;
	}
}

public class Main2 {
	static LinkedList<scheduling> list;
	static int[] solution;
	static int N = 0, maxProfit = Integer.MIN_VALUE,maxDeadline=Integer.MIN_VALUE;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new LinkedList<>();
		solution = new int[N];
		N = Integer.parseInt(scn.nextLine()); // job count
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" "); // (job name, profit, deadline)
			if(Integer.parseInt(arr[2])>maxDeadline)
				maxDeadline=Integer.parseInt(arr[2]);
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		System.out.println(maxDeadline);
		mergeSort(list, 0, list.size() - 1);// (key要被排序資料,最左邊索引值,最右邊索引值,value值)
		Scheduling();
		System.out.println("Max Profit: " + maxProfit);
		// print selected job sequence
		System.out.print("Job Sequence: ");
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] != 0) {
				System.out.print(list.get(solution[i] - 1).job + " ");
			}
		}
	}

	public static void Scheduling() {
		// initial variable
		int cost = 0, upperBound = 0, bound = 0, profit = 0, arr[], checked[];
		int count = 1;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(new Node(0, 0, 0, new LinkedList<>(), new int[N], new int[N])); // offer()方法用來在佇列後端加入物件
		while (!queue.isEmpty()) {
			Node subNode = queue.poll(); // poll()方法用來取出佇列前端物件
			List<Integer> sublist = subNode.list; // 取得目前等待工作序列名單
//			if(subNode.bound>upperBound)
//				continue;
			// Best first search(最佳優先搜尋)
			for (int i = 0; i < N; i++) {
				cost = 0;
				bound = 0;
				profit = subNode.profit;
				arr = subNode.arr.clone();
				checked = subNode.checked.clone();
				if (sublist.size() == 0 || sublist.get(sublist.size() - 1) < i + 1) {
					List<Integer> nextSubset = new LinkedList<Integer>(sublist);
					nextSubset.add(i + 1);
					for (int k = list.get(i).deadline - 1; k >= 0; k--) {
						if (arr[k] == 0) {
							arr[k] = i + 1; // slot sign
							profit += list.get(i).profit;
							//bound+=list.get(i).profit;
							checked[i] = 1; // 目前工作被指派(標記1)
							break;
						}
					}

					if (profit > maxProfit) {
						maxProfit = profit;
						solution = arr;
					}
					for (int j = N - 1; j >= 0; j--) {
						// 計算 cost (成本)
						if (checked[j] == 0 && j < i) {
							cost += list.get(j).profit;
						}
//						// 計算 bound (上限)
//						if (j != i && checked[j] == 0) {
//							bound += list.get(j).profit;
//						}
					}
//					int c=sublist.size()+1;
//					bound=profit;
//					for(int j=i+1;j<N;j++) {
//						if(c==3)
//							break;
//						// 計算 bound (上限)
//						if (checked[j] == 0) {
//							System.out.print("j: "+j+" "+list.get(j).profit+" ");
//							bound += list.get(j).profit;
//							c++;
//						}
//					}
					int c=0;
					for(int j=0;j<N;j++) {
						if(c==maxDeadline)
							break;
						if(j<=i&&checked[j]==1) {
							bound += list.get(j).profit;
							c++;
						}
						else if(checked[j]==0&&j>i) {
							bound += list.get(j).profit;
							c++;
						}
					}
					System.out.println();
					System.out.println(nextSubset + " " + list.get(i).job + "  cost: " + cost + "  upper:" + bound
							+ " profit: " + profit + " count: " + (count++) + " arr: " + arr[0] + " " + arr[1] + " "
							+ arr[2] + " " + arr[3] + "  check: " + checked[0] + " " + checked[1] + " " + checked[2]
							+ " " + checked[3]+" i= "+i);
					// 成本不能大於最大上限
					if (bound < upperBound)
						continue;
					// 最大上限為每次 bound 最小值
					if (profit > upperBound) {
						upperBound = profit;
					}
					// 寫入 queue
					queue.offer(new Node(bound, cost, profit, nextSubset, arr, checked));
				}
			}
		}
	}
	public static void mergeSort(LinkedList<scheduling> list, int left, int right) {
		if (left < right) { // 當左邊大於右邊時代表只剩一個元素了
			int mid = (left + right) / 2; // 每次對切，切到只剩一個為止
			mergeSort(list, left, mid); // 左邊等份
			mergeSort(list, mid + 1, right); // 右邊等份
			Merge(list, left, mid + 1, right); // 排序且合併
		}
	}

	public static void Merge(LinkedList<scheduling> list, int left, int mid, int right) {
		//int[] temp = new int[right + 1]; // 建立一個temp陣列存放排序後的值
		LinkedList<scheduling> temp =new LinkedList<>();
		int left_end = mid - 1; // 左邊最後一個位置
		int index = left; // 位移起始點
		int origin_left = left; // 將最左邊的變數儲存起來(最後搬移元素會用到)
		for(int i=0;i<right + 1;i++)
			temp.add(new scheduling("", 0, 0));

		while ((left <= left_end) && (mid <= right)) { // 左右兩串列比大小依序放入temp陣列中儲存
			if (list.get(left).profit >= list.get(mid).profit)
				temp.add(index++,list.get(left++));
			else
				temp.add(index++,list.get(mid++));
		}

		if (left <= left_end) { // 若左邊的串列尚未走完將剩餘的數值依序放入temp陣列中
			while (left <= left_end) {
				temp.add(index++,list.get(left++));
			}
		} else { // 反之若右邊的串列尚未走完將剩餘的數值依序放入temp陣列中
			while (mid <= right) {
				temp.add(index++,list.get(mid++));
			}
		}
		// 最後將排序好的temp陣列複製到list串列中
		for (int i = origin_left; i <= right; i++) {
			list.set(i, temp.get(i));
		}

	}
}

/**

10
J8 30 1
J1 60 5
J10 20 3
J3 52 5
J4 50 4
J2 55 4
J9 25 1
J6 40 2
J7 33 4
J5 45 2

5
J2 15 2 
J3 10 1 
J5 1 3 
J4 5 3 
J1 20 2

4
J1 5 1
J2 10 3
J3 6 2
J4 3 1

4
J1 30 3
J2 25 1
J3 20 1
J4 15 2
**/
```

ref: 
- [Queue tutorial](https://openhome.cc/Gossip/Java/Queue.html)
- [BFS Queue subset](https://www.jiuzhang.com/solution/subsets/#tag-highlight-lang-java)
- [另一種方法](https://stackoverflow.com/questions/53620072/how-to-use-branch-and-bound-algorithm-solving-job-sequencing-problem-with-deadli)
