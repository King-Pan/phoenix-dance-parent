package club.javalearn.fastsystem.security.shiro;

import club.javalearn.fastsystem.model.Permission;
import club.javalearn.fastsystem.model.Role;
import club.javalearn.fastsystem.model.User;
import club.javalearn.fastsystem.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:40
 * Description: No Description
 */
public class DefaultAuthorizingRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;


    /**
     * 认证.登录
     *
     * @param token 用户信息
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户输入的token
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        String username = utoken.getUsername();
        User user = userService.findByUserName(username);
        //放入shiro.调用CredentialsMatcher检验密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), this.getClass().getName());
    }

    /**
     * 授权
     *
     * @param principal 用户登录信息
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        //获取session中的用户
        User user = (User) principal.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissions = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles.size() > 0) {
            for (Role role : roles) {
                Set<Permission> permissionList = role.getPermissions();
                if (CollectionUtils.isNotEmpty(permissionList)) {
                    for (Permission permission : permissionList) {
                        permissions.add(permission.getUrl());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //将权限放入shiro中.
        info.addStringPermissions(permissions);
        return info;
    }


    @Override
    public String getName() {
        return "DefaultAuthorizingRealm";
    }
}
