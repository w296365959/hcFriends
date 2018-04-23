package github.wzm.com.review_test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/3/7 14:29 </br>
 * Summary:
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    //String value() default "1";//只有一个参数的时候，最好把参数名称设置为value()
    String name() default "1";//只有一个参数的时候，最好把参数名称设置为value()

   /*
    Addressz getXX() default Addressz.Address1;
    public enum Addressz {
        Address1("杭州"),
        Address2("德清");
        private String age;
        private Addressz(String age0) {
            this.age = age0;
        }

    }  */
}
