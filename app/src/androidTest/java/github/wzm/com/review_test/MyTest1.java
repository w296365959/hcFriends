package github.wzm.com.review_test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/3/5 10:25 </br>
 * Summary:
 */

public class MyTest1 {
    @Test
    public void testA(){
        List<? extends myself> list2=new ArrayList<>();
        list2.add(null);//  OK，不能放入任何类型，因为编译器只知道list2中应该放myself的某个子类，但是具体哪个子类是不知道的，因此 除了null 以外，
        //不能放入任何类型
    //    list2.add(new superA());//error   报错
    //    list2.add(new son());//error   报错
        myself myself = list2.get(0); //
        son s= (son) list2.get(0);
        System.out.println(s.a);


    }
    static class  superA
    {
        int a=0;
    }
    static class myself extends superA
    {
        int a=0;
    }

    static class son extends myself
    {
        int a=0;
    }
}
