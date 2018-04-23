package github.wzm.com.review_test;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void find(TreeNode node) throws Exception {
        if (node == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    // queue.add(current.left);
                    queue.offer(current.left);//offer要比add优，因为add在添加失败的时候会报异常

                }
                if (current.right != null) {
                    //queue.add(current.right);
                    queue.offer(current.right);

                }

                if (i != size - 1) {
                    //queue.element();//获取但不移除此队列的头。
                    current.next = queue.peek();//queue.peek() 获取但不移除此队列的头，如果此队列为空就返回null
                    //  System.out.println(queue.poll());//queue.poll()获取并移除此队列的头，如果此队列为空就返回null
                    //  queue.remove();//获取并移除此队列的头
                }

            }
        }
        level = level + 1;
    }

    public class TreeNode {
        TreeNode left;
        TreeNode right;
        TreeNode next;
        int level;
    }
}