package club.javalearn.fastsystem.parameter;

import club.javalearn.fastsystem.model.Permission;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/12
 * Time: 下午3:33
 * Description: No Description
 */
@Data
public class PermissionInfo {

    /**
     * 权限编码
     */
    private Long permissionId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限链接
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 资源表达式
     */
    private String expression;

    /**
     * 权限类型: 0: 目录 1：链接 2:按钮
     */
    private String permissionType;

    /**
     * 权限父节点编码
     */
    private Long parentId;

    /**
     * 上级资源名称
     */
    private String parentName;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private List<PermissionInfo> childPermission = new ArrayList<>();


    public Permission convertPermission(){
        Permission permission = new Permission();
        permission.setPermissionId(this.permissionId);
        permission.setPermissionName(this.permissionName);
        permission.setPermissionType(this.permissionType);
        permission.setDescription(this.description);
        permission.setOrderNum(this.orderNum);
        permission.setUrl(this.url);
        permission.setExpression(this.expression);
        permission.setIcon(this.icon);
        permission.setParentName(this.parentName);
        permission.setParentId(this.parentId);
        permission.setStatus(this.status);
        permission.setCreateTime(this.createTime);
        permission.setUpdateTime(this.updateTime);
        return permission;
    }
}
