# Job Scheduling 貪婪演算法


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

/** Test
  
5 
J2 15 2 
J3 10 1 
J5 1 3 
J4 5 3 
J1 20 2
  
7 
J2 30 4 
J3 25 4 
J1 35 3 
J4 20 2 
J7 5 2 
J5 15 3 
J6 12 1
  
7
1 40 3
1 35 1
3 30 1
4 25 3
5 20 1
6 15 3
7 10 2
  
  
 **/
```
