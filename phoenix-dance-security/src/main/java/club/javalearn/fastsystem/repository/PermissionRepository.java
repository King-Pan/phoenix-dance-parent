package club.javalearn.fastsystem.repository;

import club.javalearn.fastsystem.model.Permission;
import club.javalearn.fastsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/25
 * Time: 上午10:13
 * Description: No Description
 */
@Transactional(rollbackFor = RuntimeException.class)
public interface PermissionRepository extends JpaRepository<Permission,Long>,QuerydslPredicateExecutor<Permission>,JpaSpecificationExecutor<Permission> {

    /**
     * 通过parentId 统计该节点下的字节点是否为空
     * @param parentId 父节点ID
     * @return 子节点个数
     */
    int countByParentId(Long parentId);

    /**
     * 更新状态
     * @param permissionIds
     * @param status
     */
    @Modifying
    @Query(value = "update Permission p set p.status = :status where permissionId in (:permissionIds)")
    void updateStatus(@Param("permissionIds") List<Long> permissionIds, @Param("status") String status);

}
