package club.javalearn.fastsystem.aspect;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午6:04
 * Description: No Description
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    //模块
    String module()  default "";
    //说明
    String operation()  default "";
}
