package club.javalearn.fastsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:42
 * Description: No Description
 */
@Table(name = "sys_user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"roles"})
@ApiModel(value = "User",description = "用户信息")
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
public class User implements Serializable {

    public static Long serialVersionUID = 1L;

    /**
     * 用戶编码-自增长策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 登录用户名
     */
    @Size(min = 6, max = 56)
    @Column(nullable = false,length = 56,unique = true)
    private String userName;

    /**
     * 加密盐值
     */
    @Column(nullable = false,length = 128)
    private String salt;

    /**
     * 用户邮箱
     */
    @Size(max = 50)
    @Email(message = "邮箱格式不对")
    private String email;

    /**
     * 用户电话号码
     */
    private String phoneNum;

    /**
     * 用户昵称
     */
    @Size(min = 2, max = 20)
    @Column(length = 20, nullable = false)
    private String nickName;

    /**
     * 用户密码
     */
    @Size(min = 8, max = 100)
    @Column(length = 100)
    private String password;

    /**
     * 用户头像地址
     */
    @Column(length = 256)
    private String imgUrl;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 修改日期
     */
    private Date updateTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户状态 0: 禁用 1: 启用 2: 删除
     */
    @Column(length = 10)
    private String status;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")})
    @JsonIgnore

    private Set<Role> roles = new HashSet<>();

    @Transient
    private List<Long> roleIds = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User user = (User)obj;
            return user.getUserId().equals(this.getUserId());
        }
        return false;
    }


    /**
     * 证书凭证: 加密盐值: 用户名+盐值
     * @return
     */
    public String getCredentialsSalt() {
        return userName + salt;
    }

    @Override
    public int hashCode() {
        return this.getUserId()!=null?this.getUserId().hashCode():0;
    }
}
