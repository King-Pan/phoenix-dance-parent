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
     * 状态删除权限
     * @param permissionIds 权限ID
     * @param status 状态
     */
    void updateStatus(List<Long> permissionIds,String status);
}
