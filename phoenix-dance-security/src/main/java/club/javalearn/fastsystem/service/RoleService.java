package club.javalearn.fastsystem.service;

import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.model.Role;
import club.javalearn.fastsystem.parameter.RoleInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/10
 * Time: 下午5:45
 * Description: No Description
 */
public interface RoleService {

    /**
     * 分页查询用户信息
     *
     * @param roleInfo 查询参数
     * @param pageable 分页参数
     * @return 用户信息列表
     */
    Message<Role> getPageList(final RoleInfo roleInfo, Pageable pageable);

    /**
     * 分页查询用户未选择的角色信息
     *
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 用户信息列表
     */
    Message<Role> getNoSelectRole(final Long userId, Pageable pageable);

    /**
     * 批量删除用户
     *
     * @param roleIds 角色id集合
     * @return 成功个数
     */
    int deleteRoles(List<Long> roleIds);

    /**
     * 新增或者修改角色
     *
     * @param roleInfo 角色信息
     * @return 修改后的角色信息
     */
    Role save(RoleInfo roleInfo);

    /**
     * 通过角色ID修改角色状态
     * @param roleId 角色ID
     * @param status 状态
     */
    void modifyStatus(Long roleId,String status);
}
