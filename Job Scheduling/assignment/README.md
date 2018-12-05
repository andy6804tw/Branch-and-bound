## 介紹
branch and bound(分支定界)目標是找出滿足條件的一個解，所謂分支就是採用廣度優先的策略，以Queue佇列方式下去實作，在每個節點中拋棄不滿足約束條件的節點，故不繼續擴展該節點以下的子樹。以下會以 branch and bound 並使用最佳優先搜尋(Best-First Search)來實作 scheduling with deadlines 排程的問題。

## 建立scheduling類別
為了資料方便處理這邊建立一個scheduling的類別專門放置每一個工作內容的利益和截止時間。該列別建立三個變數分別為字串型態的工作名稱(job)，以及兩個整數型態的變數分別為利益(proft)與截止時間(deadline)。最後在建立建構子來初始化每個變數值。

```java=
class scheduling {
	String job;
	int profit, deadline;

	public scheduling(String job, int profit, int deadline) {
		this.job = job;
		this.profit = profit;
		this.deadline = deadline;
	}
}
```

## main()函式
在主函式中主要目地是讀取使用者所輸入的測試資料首先要輸入整數N代表以下會有N個工作。接下來會要求使用者輸入N筆工作資料分別為(job name、profit、deadline)。
下圖程式第二行建立一個自訂義 scheduling 型態的 LinkedList 取名為 list 專門來儲存所有的工作項目與內容。第三行建立一個整數型態的陣列 `solution[]` 專門來儲存最佳解的工作順序。第四行有多個整數變數第一個 `N` 為工作數量。第二個 `maxProfit` 為所有工作序列中最大的利益值，並將它初始值為最小值。
程式第十五行進入Scheduling()函式使用branch and bound 並使用最佳優先搜尋(Best-First Search)來尋找最佳工作排程以及計算最大利益。
最後並印出結果第一行為滿足條件的最大利益，第二行為該最大利益的一組解(工作序列)。

```java=
public class Main {
    static LinkedList < scheduling > list;
    static int[] solution;
    static int N = 0, maxProfit = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        list = new LinkedList < > ();
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
}
```

## Scheduling()函式 

```java=
public static void Scheduling() {
    // initial variable
    int cost = 0, upperBound = Integer.MAX_VALUE, bound = 0, profit = 0, arr[], checked[];
    int count = 1;
    Queue < List < Integer >> queue = new LinkedList < > ();
    queue.offer(new LinkedList < Integer > ()); // offer()方法用來在佇列後端加入物件
    while (!queue.isEmpty()) {
        List < Integer > subset = queue.poll(); // poll()方法用來取出佇列前端物件
        // Best first search(最佳優先搜尋)
        for (int i = 0; i < N; i++) {
            cost = 0;
            bound = 0;
            profit = 0;
            arr = new int[N];
            checked = new int[N];
            if (subset.size() == 0 || subset.get(subset.size() - 1) < i + 1) {
                List < Integer > nextSubset = new LinkedList < Integer > (subset);
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
                    solution = arr;
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
```


## 執行與測試
輸入說明:
第一行為工作數量N，接下來會等待輸入N筆工作資料分別為 (job name、profit、deadline)。
輸出說明:
第一行為滿足條件的最大利益，第二行為該最大利益的一組解(工作序列)。


- 測試一
總共十個工作

測資:
```
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
```

45+55+50+52+60=262

![](https://i.imgur.com/A99VnzR.png)

- 測試二

測資:
```
5
J2 15 2 
J3 10 1 
J5 1 3 
J4 5 3 
J1 20 2
```

20+15+5=40

![](https://i.imgur.com/75gQEmG.png)

- 測試三

測資:
```
4
J1 5 1
J2 10 3
J3 6 2
J4 3 1
```

5+6+10=21

![](https://i.imgur.com/Pa9hrbh.png)
