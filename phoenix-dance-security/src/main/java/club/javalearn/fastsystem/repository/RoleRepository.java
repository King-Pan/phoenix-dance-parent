package club.javalearn.fastsystem.repository;

import club.javalearn.fastsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface RoleRepository extends JpaRepository<Role,Long>,QuerydslPredicateExecutor<Role> ,JpaSpecificationExecutor<Role> {


    /**
     * 通过角色ID修改角色状态
     * @param roleIds 角色ID
     * @return 修改角色成功数
     */
    @Query(value = "update  Role r set r.status=2  where r.roleId in (:roleIds)")
    int deleteRoleByIds(@Param("roleIds") List<Long> roleIds);
}
