package club.javalearn.fastsystem.repository;

import club.javalearn.fastsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:50
 * Description: No Description
 */
@Transactional(rollbackFor = RuntimeException.class)
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    /**
     * 通过用户名查找用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @QueryHints({@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")})
    User findByUserName(String userName);


    /**
     * 通过用户编号查询用户信息
     *
     * @param userId 用户编号
     * @return 用户信息
     */
    User findByUserId(Long userId);

    @Query(value = "delete from User u where u.userId in (:userIds) ")
    int deleteUserByUserIds(@Param("userIds") List<Long> userIds);
}
