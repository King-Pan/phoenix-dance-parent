package club.javalearn.fastsystem.parameter;

import club.javalearn.fastsystem.model.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/10
 * Time: 下午5:41
 * Description: No Description
 */

@Data
public class RoleInfo {
    /**
     * 角色编码
     */
    private Long roleId;

    /**
     * 角色名称
     */
    @NotEmpty
    private String roleName;



    /**
     * 角色编码
     */
    @NotEmpty
    private String roleCode;


    /**
     * 角色备注
     */
    private String remark;

    /**
     * 角色状态
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

    public Role convertRole(){
        Role role = new Role();
        role.setRoleId(this.roleId);
        role.setRoleCode(this.roleCode);
        role.setRoleName(this.roleName);
        role.setRemark(this.remark);
        role.setStatus(this.status);
        role.setCreateTime(this.createTime);
        role.setUpdateTime(this.updateTime);
        return role;
    }
}
