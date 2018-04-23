package github.wzm.com.review_test;

import org.junit.Test;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/3/22 17:28 </br>
 * Summary:
 */

public class I extends My {
    class I2 implements You {
        @Override
        public void run() {

        }

        @Override
        public void yP() {

        }
    }

    public I2 getMethed() {
        return new I2();
    }

    public static void main(String[] args) {
        I i = new I();
        i.run();
        /* 当接口和类 具有的方法名重名时，需要使用一个内部类来实现接口， 直接继承和实现 是不能完成多实现的  */
        I2 i2 = i.getMethed();
        i2.yP();
        i2.run();

    }

    @Override
    public void run() {

    }
}
