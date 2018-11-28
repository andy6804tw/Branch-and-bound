# BFS & DFS


## Breadth-first search (BFS)
廣度優先搜尋法，是一種圖形(graph)搜索演算法。從圖的某一節點(vertex, node)開始走訪，接著走訪此一節點所有相鄰且未拜訪過的節點，由走訪過的節點繼續進行先廣後深的搜尋。以樹(tree)來說即把同一深度(level)的節點走訪完，再繼續向下一個深度搜尋，直到找到目的節點或遍尋全部節點。

廣度優先搜尋法屬於盲目搜索(uninformed search)是利用佇列(Queue)來處理，通常以迴圈的方式呈現。

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
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
        int count=1;
        while (!queue.isEmpty()) {
        	List<Integer> subset = queue.poll();
        	if(subset.indexOf(nums.length)>=0) {
        		results.add(subset);
        		System.out.println(subset);
        	}
            
            for (int i = 0; i < nums.length; i++) {
                if (subset.size() == 0 || subset.get(subset.size() - 1) < nums[i]) {
                    List<Integer> nextSubset = new LinkedList<Integer>(subset);
                    nextSubset.add(nums[i]);
                    queue.offer(nextSubset);
                }
            }
        }
        return results;
    }
}
```

```
[4]
[1, 4]
[2, 4]
[3, 4]
[1, 2, 4]
[1, 3, 4]
[2, 3, 4]
[1, 2, 3, 4]
```

![](https://freefeast.info/wp-content/uploads//2014/04/BFS-traversal.jpg)


## Depth-first search (DFS)
深度優先搜尋法，是一種用來遍尋一個樹(tree)或圖(graph)的演算法。由樹的根(或圖的某一點當成 根)來開始探尋，先探尋邊(edge)上未搜尋的一節點(vertex or node)，並儘可能深的搜索，直到該節點的所有邊上節點都已探尋；就回溯(backtracking)到前一個節點，重覆探尋未搜尋的節點，直到找到目 的節點或遍尋全部節點。

深度優先搜尋法屬於盲目搜索(uninformed search)是利用堆疊(Stack)來處理，通常以遞迴的方式呈現。

```java
public class DFS {
	
	static int num[]= {1,2,3,4};
	static int solution[]=new int [num.length];

	public static void main(String[] args) {
		backtracking(0,0);
	}
	
	public static void backtracking(int n,int size) {
			if(n==num.length) {
				for(int i=0;i<num.length;i++) {
					System.out.print(solution[i]+" ");
				}
				System.out.println();
			}
			for(;n<num.length;n++) {
				solution[size]=num[n];
				backtracking(n+1,size+1);
				solution[size]=0;
			}
	}
}
```

```
1 2 3 4 
1 2 4 0 
1 3 4 0 
1 4 0 0 
2 3 4 0 
2 4 0 0 
3 4 0 0 
4 0 0 0 
```


![](https://freefeast.info/wp-content/uploads//2014/04/DFS-Traversal.jpg)



ref: https://www.jiuzhang.com/solution/subsets/#tag-highlight-lang-java
