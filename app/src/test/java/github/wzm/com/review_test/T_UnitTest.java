package github.wzm.com.review_test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/2/24 16:04 </br>
 * Summary:
 */

public class T_UnitTest {
    @Test
    public void ttt() throws Exception {//父类引用指向子类对象
        A a = new A();
        a.find();//A
        B b = new B();
        b.find();//B

        A aa = new B();//父类应用指向子类对象，
        // 调用成员方法的时候编译看左边（父），运行也看右边（子）
        aa.find();//B
        // 调用成员属性的时候 编译看左边（父），运行也看左边（父）；
        System.out.println(aa.name);//A

    }

    @Test
    public void javaTest() throws Exception {//char数组的定义方式
        char[] aar;
        aar = new char[]{1, 2, 3, 'a'}; //静态初始化
        char[] bbr = new char[]{'w', 1, 1};
        char[] ccr = {1, 'a', 3};//静态初始化，给出初始化值，由系统决定长度
        char[] ddr = new char[6];//动态初始化，指定长度，由系统给出初始化值
        char[] eer = new char[6];//{1,2,2,3,4,4};   //不允许动静结合。前面定义长度后就不能出现{}，后面有了{}前面就不能定义长度


        char[] ffr;
        ffr = new char[]{2, 3, 4, 4, 's', 'd', 'g', '啊'};//数字会由空格代替
        System.out.print("ffr==");
        System.out.println(ffr);//ffr输出的不是地址值而是ffr数组的所有元素，想输出地址值需要使用数组和字符串来做并指（+）运算
        System.out.print("“”+ffr==");
        System.out.println("" + ffr);//这输出的就是地址值
        System.out.print("ffr+“”==");
        System.out.println(ffr + "");
        ffr = null;//这是赋值ffr数组的引用地址为null，在调用数组元素就会出现空指针异常
    }

    @Test
    public void CollectionTest() throws Exception {//map集合的遍历方法
        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        map.put("c", "C");
        map.put("d", "D");
        //方法一
        for (String key : map.keySet()) {
            System.out.println("key:" + key + "   value:" + map.get(key));
        }
        //方法二
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        //方法三
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + "==" + entry.getValue());
        }
    }

    class A {
        String name = "A";
        int age = 2;
        String sex = "nan";

        public void find() {
            System.out.println(name);

        }
    }

    class B extends A {
        String name = "B";

        public void find() {
            System.out.println(name);
        }

    }

    @Test
    public void testB() {
        List<? super Myself> list = new ArrayList<>();//限定下界 Myself
        list.add(new son());//子类可以
        list.add(new Myself());//本身类型也可以
        list.add(null);//null也可以
      //  list.add(new superA());//超类不可以
        System.out.println(list.get(0));
    }

    @Test
    public void testA() {
        List<? extends Myself> list2 = new ArrayList<>();
        list2.add(null);//  OK，不能放入任何类型，因为编译器只知道list2中应该放myself的某个子类，但是具体哪个子类是不知道的，因此 除了null 以外，
        //不能放入任何类型
      //     list2.add(new Myself());//error  报错   ？？？为什么不能是T本省？
        //    list2.add(new superA());//error   报错
        //    list2.add(new son());//error   报错
        Myself myself = list2.get(0); //
        System.out.println(myself == null);
        son s = (son) list2.get(0);
        System.out.println(s == null);


    }

    static class superA {
        int a = 0;
    }

    static class Myself extends superA {
        int a = 0;
    }

    static class son extends Myself {
        int a = 0;
    }
}
