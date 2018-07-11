package club.javalearn.fastsystem.repository;

import club.javalearn.fastsystem.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role>, JpaSpecificationExecutor<Role> {


    /**
     * 通过角色ID修改角色状态
     *
     * @param roleIds 角色ID
     * @return 修改角色成功数
     */
    @Modifying
    @Query(value = "update  Role r set r.status=2  where r.roleId in (:roleIds)")
    int deleteRoleByIds(@Param("roleIds") List<Long> roleIds);


    /**
     * 通过角色ID修改角色状态
     *
     * @param roleId 角色ID
     * @param status 状态
     */
    @Modifying
    @Query(value = "update  Role r set r.status=:status  where r.roleId = :roleId")
    void modifyStatus(@Param("roleId") Long roleId, @Param("status") String status);


    /**
     * 用户未选择的角色信息
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 角色列表
     */
    @Query(
            value = "select r.* from sys_role r WHERE not exists(select ur.role_id from sys_user_role ur WHERE ur.user_id=:userId and ur.role_id=r.role_id)",
            countQuery = "select count(1) from sys_role r WHERE not exists(select ur.role_id from sys_user_role ur WHERE ur.user_id=:userId and ur.role_id=r.role_id)",
            nativeQuery = true
    )
    Page<Role> getNoSelectRoleList(@Param("userId") Long userId, Pageable pageable);

}
