package club.javalearn;

import club.javalearn.fastsystem.model.Role;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/11
 * Time: 下午12:20
 * Description: No Description
 */
public class BeanUtilsTest {

    @Test
    public void test(){
        Role r1 = new Role();
        r1.setRoleCode("zs");
        Role r2 = new Role();
        r2.setRemark("哈哈哈哈");
        BeanUtils.copyProperties(r1,r2);

        System.out.println(r2);
    }
}
