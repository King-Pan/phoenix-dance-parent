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
public interface PermissionRepository extends JpaRepository<Permission, Long>, QuerydslPredicateExecutor<Permission>, JpaSpecificationExecutor<Permission> {

    /**
     * 通过ID获取权限信息
     *
     * @param permissionId 权限信息Id
     * @return 权限信息
     */
    Permission findByPermissionId(Long permissionId);

    /**
     * 通过parentId 统计该节点下的字节点是否为空
     *
     * @param parentId 父节点ID
     * @return 子节点个数
     */
    int countByParentId(Long parentId);

    /**
     * 通过用户ID查询用户菜单
     * @param userId 用户ID
     * @return 菜单集合
     */
    @Query(value = "SELECT p.* FROM sys_permission p WHERE p.permission_id " +
            "IN (SELECT rp.permission_id FROM sys_role_permission rp " +
            "WHERE rp.role_id in " +
            "(select ur.role_id from sys_user_role ur where ur.user_id=:userId)) order by p.permission_id asc",nativeQuery = true)
    List<Permission> getListByUserId(@Param("userId") Long userId);

    /**
     * 更新状态
     *
     * @param permissionIds
     * @param status
     */
    @Modifying
    @Query(value = "update Permission p set p.status = :status where permissionId in (:permissionIds)")
    void updateStatus(@Param("permissionIds") List<Long> permissionIds, @Param("status") String status);

}
