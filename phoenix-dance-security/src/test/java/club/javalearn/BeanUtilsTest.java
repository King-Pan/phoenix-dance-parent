package club.javalearn;

import club.javalearn.fastsystem.model.Role;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.sql.*;

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
    public void test() {
        Role r1 = new Role();
        r1.setRoleCode("zs");
        Role r2 = new Role();
        r2.setRemark("哈哈哈哈");
        BeanUtils.copyProperties(r1, r2);

        System.out.println(r2);
    }

    @Test
    public void jdbc() {
        String sql = "select user_name as name,role_id as rId from (select * from (select u.user_name,ur.role_id from sys_user u, sys_user_role ur where ur.user_id=u.user_id) a)b";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crm", "root", "123456");
            Statement st = connection.createStatement();
            ResultSet rst = st.executeQuery(sql);
            ResultSetMetaData metaData = rst.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.println(metaData.getColumnName(i)+"----" + metaData.getColumnLabel(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
