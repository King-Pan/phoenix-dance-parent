package club.javalearn.fastsystem.service;

import club.javalearn.fastsystem.model.Permission;
import club.javalearn.fastsystem.parameter.PermissionInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/12
 * Time: 下午3:31
 * Description: No Description
 */
public interface PermissionService {


    /**
     * 查询所有的权限
     * @return 权限集合
     */
    List<Permission> getList();


    /**
     * 查询用户所有的权限
     * @param userId 状态
     * @return 权限集合
     */
    List<Permission> getList(Long userId);


    /**
     * 过滤查询所有的权限
     * @param permissionName 权限名称
     * @param status 状态
     * @return 权限集合
     */
    List<Permission> getList(String permissionName,String status);

    /**
     * 新增修改权限
     * @param permissionInfo 权限信息
     * @return 修改后的权限信息
     */
    Permission save(PermissionInfo permissionInfo);


    /**
     * 通过父类ID获取子节点个数
     * @param parentId 父节点ID
     * @return 个数
     */
    int countChildNum(Long parentId);


    /**
     * 通过ID获取权限信息
     * @param permissionId 权限信息ID
     * @return 权限信息
     */
    Permission get(Long permissionId);
    /**
     * 状态删除权限
     * @param permissionIds 权限ID
     * @param status 状态
     */
    void updateStatus(List<Long> permissionIds,String status);
}
