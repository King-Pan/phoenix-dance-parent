package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.aspect.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/12
 * Time: 下午3:00
 * Description: No Description
 */
@Slf4j
@RestController
@RequestMapping("/")
@Api(tags = "PermissionController", value = "权限管理", description = "权限管理")
public class PermissionController {
    @ApiOperation(
            value = "用户管理页面", notes = "进入用户管理页面"
    )
    @GetMapping("permission/")
    @SysLog(module = "权限模块", operation = "进入权限管理页面")
    public ModelAndView permissionPage() {
        return new ModelAndView("security/permission");
    }


}
