# Job Scheduling 貪婪法


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

  public static void main(String[] args) {
    Scanner scn = new Scanner(System.in);
    list = new ArrayList<>();
    int n = Integer.parseInt(scn.nextLine());
    for (int i = 0; i < n; i++) {
      String arr[] = scn.nextLine().split(" ");
      list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
    }
    sort();
    for (int i = 0; i < list.size(); i++) {
      String tempJob = list.get(i).job;
      int tempProfit = list.get(i).profit, tempDeadline = list.get(i).deadline;
      System.out.println(tempJob + " " + tempProfit + " " + tempDeadline);
    }
    int arr[] = new int[n], total = 0;
    for (int i = 0; i < list.size(); i++) {

      for (int j = list.get(i).deadline - 1; j >= 0; j--) {
        if (arr[j] == 0) {
          arr[j] = list.get(i).profit;
          total += list.get(i).profit;
          break;
        }
      }
    }
    System.out.println(total);

  }

  // Bubble Sort
  public static void sort() {
    for (int i = 0; i < list.size() - 1; i++) {
      for (int j = i + 1; j < list.size(); j++) {
        if (list.get(i).profit < list.get(j).profit) {
          String tempJob = list.get(i).job;
          int tempProfit = list.get(i).profit, tempDeadline = list.get(i).deadline;
          list.set(i, list.get(j));
          list.set(j, new scheduling(tempJob, tempProfit, tempDeadline));
        }
      }
    }
  }

}
```
