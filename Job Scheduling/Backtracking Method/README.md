
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
	static String solution[] = new String[5];
	static int arr[] = new int[5],total=0,max=0;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		list = new ArrayList<>();
		int n = Integer.parseInt(scn.nextLine());
		for (int i = 0; i < n; i++) {
			String arr[] = scn.nextLine().split(" ");
			list.add(new scheduling(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
		}
//    int arr[] = new int[n], total = 0;
//    for (int i = 0; i < list.size(); i++) {
//
//      for (int j = list.get(i).deadline - 1; j >= 0; j--) {
//        if (arr[j] == 0) {
//          arr[j] = list.get(i).profit;
//          total += list.get(i).profit;
//          break;
//        }
//      }
//    }
//    System.out.println(total);
		backtracking(0, 0);
		System.out.println(max);
	}

	public static void backtracking(int n, int size) {
		for (int i = 0; i < 4; i++) {
			System.out.print(solution[i]+ " " );
			total+=arr[i];
			if(total>max)
				max=total;
		}
		
		System.out.print(" "+total);
		System.out.println();
		for (int i = 0; i < 4; i++) {
			System.out.print(arr[i]+ " " );
		}
		System.out.println();
		total=0;
		//arr = new int[5];
		//solution = new String[5];
		//if(promising()==1) {
		int j;
			for (; n < 4; n++) {
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
				//arr[size]=0;
				//total=0;
			}
		//}
	}
	public static int promising() {
		
		return 1;
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
