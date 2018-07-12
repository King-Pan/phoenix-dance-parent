package club.javalearn.fastsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:42
 * Description: No Description
 */
@Table(name = "sys_role")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users","permissions"})
@ApiModel(value = "Role",description = "角色信息")
public class Role implements Serializable {

    public static Long serialVersionUID = 1L;

    /**
     * 角色编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /**
     * 角色名称
     */
    @NotEmpty
    @Column(length = 256)
    private String roleName;



    /**
     * 角色编码
     */
    @NotEmpty
    @Column(length = 56)
    private String roleCode;


    /**
     * 角色备注
     */
    @Column(length = 2000)
    private String remark;

    /**
     * 角色状态
     */
    @Column(length = 10)
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_permission",joinColumns ={@JoinColumn(name = "role_id", referencedColumnName = "roleId")},
            inverseJoinColumns =  {@JoinColumn(name = "permission_id", referencedColumnName = "permissionId")})
    @JsonIgnore
    private Set<Permission> permissions = new HashSet<>();

    @Transient
    private List<Long> permissionIdList;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Role){
            Role role =(Role)obj;
            return role.getRoleId().equals(this.getRoleId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  this.getRoleId()!=null?this.getRoleId().hashCode():0;
    }

}
