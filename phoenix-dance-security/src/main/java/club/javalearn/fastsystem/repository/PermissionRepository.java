package club.javalearn.fastsystem.repository;

import club.javalearn.fastsystem.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/25
 * Time: 上午10:13
 * Description: No Description
 */
public interface PermissionRepository extends JpaRepository<Permission,Long>,QuerydslPredicateExecutor<Permission> {
}
