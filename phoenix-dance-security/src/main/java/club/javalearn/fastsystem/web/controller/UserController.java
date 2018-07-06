package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.aspect.SysLog;
import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.common.ServerResponse;
import club.javalearn.fastsystem.model.User;
import club.javalearn.fastsystem.parameter.UserInfo;
import club.javalearn.fastsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:55
 * Description: No Description
 */
@Slf4j
@Api(tags = "UserController", value = "用户管理", description = "用户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(
            value = "用户管理页面", notes = "进入用户管理页面"
    )
    @GetMapping("/user/")
    @SysLog(module = "用户模块", operation = "进入用户管理页面")
    public ModelAndView userPage() {
        return new ModelAndView("system/user");
    }


    @ApiOperation(
            value = "通过用户编号查询用户", notes = "通过用户编号查询用户"
    )
    @ApiImplicitParam(name = "userId", value = "用户编号", dataType = "Long", paramType = "path")
    @GetMapping("/user/{userId}")
    @SysLog(module = "用户模块", operation = "通过用户编号查询用户")
    public User userPage(@PathVariable("userId") Long userId) {
        return userService.findByUserId(userId);
    }

    @ApiOperation(
            value = "新增用户", notes = "新增用户"
    )
    @ApiImplicitParam(name = "userInfo", value = "用户编号", dataType = "UserInfo", paramType = "query")
    @PostMapping("user/")
    @SysLog(module = "用户模块", operation = "新增用户")
    public ServerResponse addUser(UserInfo userInfo) {
        userService.save(userInfo);
        return ServerResponse.createBySuccessMessage("新增用户成功");
    }

    @DeleteMapping("/user/")
    public ServerResponse batchDeleteUser(@RequestParam("userIds[]") Long[] userIds) {
        log.info("需要批量删除的用户ID： " + Arrays.toString(userIds));
        ServerResponse serverResponse;
        try {
            userService.deleteUsers(Arrays.asList(userIds));
            serverResponse = ServerResponse.createBySuccessMessage("批量删除用户成功");
        }catch (Exception e){
            log.error("批量删除用户失败\n",e);
            serverResponse = ServerResponse.createByErrorMessage("批量删除用户失败\n " + e.getMessage());
        }
        return serverResponse;
    }

    @DeleteMapping("/user/{userId}")
    public ServerResponse deleteUser(@PathVariable("userId") Long userId) {
        log.info("需要删除的用户ID： " + userId);
        ServerResponse serverResponse;
        try {
            userService.deleteUsers(Arrays.asList(userId));
            serverResponse = ServerResponse.createBySuccessMessage("删除用户成功");
        }catch (Exception e){
            log.error("删除用户失败\n",e);
            serverResponse = ServerResponse.createByErrorMessage("删除用户失败\n " + e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 分页查询
     *
     * @param userInfo 查询参数
     * @param pageable 分页参数
     * @return 分页信息
     */
    @ApiOperation(value = "登录处理", notes = "登录处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userInfo", value = "查询参数", dataType = "UserInfo", paramType = "query"),
            @ApiImplicitParam(name = "pageable", value = "分页参数", dataType = "Pageable", paramType = "query")
    })
    @GetMapping("/users/")
    public Message<User> getUserList(UserInfo userInfo, @PageableDefault Pageable pageable) {
        if (log.isDebugEnabled()) {
            log.debug("查询参数 {}", userInfo);
        }
        return userService.getPageList(userInfo, pageable);
    }

}
