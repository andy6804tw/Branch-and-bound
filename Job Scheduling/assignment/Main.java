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
		int cost = 0, upperBound = Integer.MAX_VALUE, bound = 0, profit = 0, arr[], checked[];
		// int count = 1;
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
//					System.out.println(nextSubset + " " + list.get(i).job + "  cost: " + cost + "  upper:" + bound
//							+ " profit: " + profit + " count: " + (count++) + " arr: " + arr[0] + " " + arr[1] + " "
//							+ arr[2] + " " + arr[3] + "  check: " + checked[0] + " " + checked[1] + " " + checked[2]
//							+ " " + checked[3]);
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
**/