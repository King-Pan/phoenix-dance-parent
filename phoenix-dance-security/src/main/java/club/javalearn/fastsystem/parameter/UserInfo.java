package club.javalearn.fastsystem.parameter;

import club.javalearn.fastsystem.model.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/25
 * Time: 上午9:59
 * Description: No Description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"roles"})
@ApiModel(value = "UserInfo", description = "用户信息")
public class UserInfo implements Serializable {

    public static Long serialVersionUID = 1l;

    /**
     * 用戶编码-自增长策略
     */
    private Long userId;

    /**
     * 登录用户名
     */

    private String userName;


    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户电话号码
     */
    private String phoneNum;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户头像地址
     */
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
     * 用户状态
     */
    private String status;

    public User convertUser() {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNum(phoneNum);
        user.setImgUrl(imgUrl);
        user.setCreateTime(createTime);
        user.setUpdateTime(updateTime);
        user.setLastLoginTime(lastLoginTime);

        return user;
    }

}
