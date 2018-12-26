package Branch;

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
	int profit;
	List<Integer> list;
	int arr[];
	int checked[];

	public Node(int bound, int profit, List<Integer> list, int arr[], int checked[]) {
		this.bound = bound;
		this.profit = profit;
		this.list = list;
		this.arr = arr;
		this.checked = checked;
	}
}

public class Main {
	static LinkedList<scheduling> list;
	static int[] solution;
	static int N = 0, maxProfit = Integer.MIN_VALUE, maxDeadline = Integer.MIN_VALUE;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new LinkedList<>();
		solution = new int[N];
		System.out.print("請輸入工作數量: ");
		N = Integer.parseInt(scn.nextLine()); // job count
		System.out.println("請依序輸入Job Profit Deadline (以空白隔開):");
		for (int i = 0; i < N; i++) {
			String arr[] = scn.nextLine().split(" "); // (job name, profit, deadline)
			if (Integer.parseInt(arr[2]) > maxDeadline)
				maxDeadline = Integer.parseInt(arr[2]);
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
		mergeSort(list, 0, list.size() - 1);// (key要被排序資料,最左邊索引值,最右邊索引值,value值)
		Scheduling();
		System.out.println("\n|  Job  |  Profit  | Deadline |");
		System.out.println("-------------------------------");
		for (int i = 0; i < list.size(); i++) {
			System.out.printf("%5s %8d %10d\n", list.get(i).job, list.get(i).profit, list.get(i).deadline);
		}
		System.out.println("\nMax Profit: " + maxProfit);
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
		int upperBound = 0, bound = 0, profit = 0, arr[], checked[];
		// int count = 1;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(new Node(0, 0, new LinkedList<>(), new int[N], new int[N])); // offer()方法用來在佇列後端加入物件
		while (!queue.isEmpty()) {
			Node subNode = queue.poll(); // poll()方法用來取出佇列前端物件
			List<Integer> sublist = subNode.list; // 取得目前等待工作序列名單
			for (int i = 0; i < N; i++) {
				bound = 0;
				profit = subNode.profit;
				arr = subNode.arr.clone();
				checked = subNode.checked.clone();
				if (sublist.size() == 0 || sublist.get(sublist.size() - 1) < i + 1) {
					List<Integer> nextSubset = new LinkedList<Integer>(sublist);
					nextSubset.add(i + 1);
					// 計算目前節點內可執行的工作 Profit 加總(檢查 Deadline)
					for (int k = list.get(i).deadline - 1; k >= 0; k--) {
						if (arr[k] == 0) {
							arr[k] = i + 1; // slot sign
							profit += list.get(i).profit;
							checked[i] = 1; // 目前工作被指派(標記1)
							break;
						}
					}
					// 計算 bound 包含目前節點內可執行的工作尋找前k大的工作 Profit
					int k = 0;
					for (int j = 0; j < N; j++) {
						if (k == maxDeadline)
							break;
						if (j <= i && checked[j] == 1) {
							bound += list.get(j).profit;
							k++;
						} else if (checked[j] == 0 && j > i) {
							bound += list.get(j).profit;
							k++;
						}
					}
					// System.out.println(nextSubset + " upper:" + bound
					// + " profit: " + profit + " count: " + (count++) + " arr: " + arr[0] + " " +
					// arr[1] + " "
					// + arr[2] + " " + arr[3] + " check: " + checked[0] + " " + checked[1] + " " +
					// checked[2]
					// + " " + checked[3]);
					// 判斷是否promising
					if (profit > upperBound) {
						upperBound = profit;// upperBound為每個節點profit的最大值
						maxProfit = profit;
						solution = arr;
						// if(bound==profit)
						// continue;
						// 寫入 queue (promising)
						queue.offer(new Node(bound, profit, nextSubset, arr, checked));
					}
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
		LinkedList<scheduling> temp = new LinkedList<>();// 建立一個temp串列存放排序後的值
		int left_end = mid - 1; // 左邊最後一個位置
		int index = left; // 位移起始點
		int origin_left = left; // 將最左邊的變數儲存起來(最後搬移元素會用到)
		for (int i = 0; i < right + 1; i++)
			temp.add(new scheduling("", 0, 0));

		while ((left <= left_end) && (mid <= right)) { // 左右兩串列比大小依序放入temp串列中儲存
			if (list.get(left).profit >= list.get(mid).profit)
				temp.add(index++, list.get(left++));
			else
				temp.add(index++, list.get(mid++));
		}

		if (left <= left_end) { // 若左邊的串列尚未走完將剩餘的數值依序放入temp串列中
			while (left <= left_end) {
				temp.add(index++, list.get(left++));
			}
		} else { // 反之若右邊的串列尚未走完將剩餘的數值依序放入temp串列中
			while (mid <= right) {
				temp.add(index++, list.get(mid++));
			}
		}
		// 最後將排序好的temp串列複製到list串列中
		for (int i = origin_left; i <= right; i++) {
			list.set(i, temp.get(i));
		}

	}
}

/*
// demo
5
J1 55 1 
J2 30 2
J3 10 1
J4 5 3
J5 1 4


10
J1 80 1
J2 45 2
J3 40 3
J4 30 3
J5 30 2
J6 20 4
J7 10 2
J8 5 3
J9 4 1
J10 2 5

10
J1 80 1
J2 2 5
J3 40 3
J4 10 2
J5 45 2
J6 20 4
J7 30 3
J8 5 3
J9 4 1
J10 30 2
*/
