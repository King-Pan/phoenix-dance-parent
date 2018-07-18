package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.model.User;
import club.javalearn.fastsystem.service.PermissionService;
import club.javalearn.fastsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午6:07
 * Description: No Description
 */
@Slf4j
@Controller
@Api(tags = "LoginController",value = "登录处理",description = "登录处理")
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;


    @Autowired
    private PermissionService permissionService;

    /**
     * 进入登录页面
     * @return ModelAndView
     */
    @ApiOperation(value = "登录页面",notes = "进入登录页面")
    @GetMapping("/loginPage")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }

    /**
     * 系统首页
     * @return
     */
    @ApiOperation(value = "首页",notes = "首页")
    @GetMapping("/")
    public ModelAndView indexPage() {
        return new ModelAndView("/index");
    }


    /**
     * 登录处理
     *
     * @param userName
     * @param password
     * @param rememberMe
     * @return
     */
    @ApiOperation(value = "登录处理",notes = "登录处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "string"),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "string"),
            @ApiImplicitParam(name = "remember-me",value = "记住我",dataType = "boolean")
    })
    @PostMapping("/login")
    public String submitLogin(@RequestParam("username") String userName, @RequestParam("password") String password, @RequestParam(name = "remember-me", required = false, defaultValue = "false") boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, rememberMe);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);

            User user = (User) SecurityUtils.getSubject().getPrincipal();

            session.setAttribute("user", user);
            session.setAttribute("menus",permissionService.getList(user.getUserId()));

            userService.updateLastLoginTime(user);

            if (log.isDebugEnabled()) {
                log.debug("登录用户为{}", user.getUserName());
            }
            return "redirect:/";
        } catch (Exception e) {
            log.error("登录失败，用户名[{}]", userName, e);
            token.clear();
            return "redirect:/loginPage";
        }
    }
}
