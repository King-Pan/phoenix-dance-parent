package club.javalearn.fastsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:43
 * Description: No Description
 */
@Table(name = "sys_permission")
@Entity
@Setter
@Getter
@ToString(exclude = {"roles"})
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Permission",description = "权限信息")
public class Permission implements Serializable {

    public static Long serialVersionUID = 1L;

    /**
     * 权限编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    /**
     * 权限名称
     */
    @Column(length = 100)
    private String permissionName;

    /**
     * 权限描述
     */
    @Column(length = 2000)
    private String description;

    /**
     * 权限链接
     */
    @Column(length = 256)
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
    @Column(length = 1)
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


    @ManyToMany(mappedBy = "permissions",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Permission){
            Permission resource = (Permission)obj;
            return  resource.getPermissionId().equals(this.getPermissionId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getPermissionId().hashCode();
    }
}
