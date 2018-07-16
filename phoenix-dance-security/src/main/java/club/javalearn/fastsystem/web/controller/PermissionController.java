package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.aspect.SysLog;
import club.javalearn.fastsystem.common.ServerResponse;
import club.javalearn.fastsystem.model.Permission;
import club.javalearn.fastsystem.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @Autowired
    private PermissionService permissionService;
    @ApiOperation(
            value = "用户管理页面", notes = "进入用户管理页面"
    )
    @GetMapping("permission/")
    @SysLog(module = "权限模块", operation = "进入权限管理页面")
    public ModelAndView permissionPage() {
        return new ModelAndView("security/permission");
    }

    @GetMapping("permissions/")
    @ApiOperation(value = "资源查询服务")
    public List<Permission> query(@RequestParam(required = false,name = "permissionName") String permissionName,
                                  @RequestParam(required = false,name = "status",defaultValue = "1")String status) {
        return permissionService.getList(permissionName,status);
    }


    @GetMapping(value = "permission/select")
    @ApiOperation(value = "选择上级资源")
    public ServerResponse<List<Permission>> select() {
        ServerResponse<List<Permission>> response;
        try {
            response = ServerResponse.createBySuccess(permissionService.getList());
        }catch (Exception e){
            log.error("选择上级资源失败",e);
            response = ServerResponse.createByErrorMessage("选择上级资源失败:"+e.getMessage());
        }
        return response;
    }
}
