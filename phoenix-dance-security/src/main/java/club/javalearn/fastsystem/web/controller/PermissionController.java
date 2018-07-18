package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.aspect.SysLog;
import club.javalearn.fastsystem.common.ServerResponse;
import club.javalearn.fastsystem.model.Permission;
import club.javalearn.fastsystem.parameter.PermissionInfo;
import club.javalearn.fastsystem.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
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
    public List<Permission> query(@RequestParam(required = false, name = "permissionName") String permissionName,
                                  @RequestParam(required = false, name = "status", defaultValue = "1") String status) {
        return permissionService.getList(permissionName, status);
    }


    @GetMapping(value = "permission/select")
    @ApiOperation(value = "选择上级资源")
    public ServerResponse<List<Permission>> select() {
        ServerResponse<List<Permission>> response;
        try {
            response = ServerResponse.createBySuccess(permissionService.getList());
        } catch (Exception e) {
            log.error("选择上级资源失败", e);
            response = ServerResponse.createByErrorMessage("选择上级资源失败:" + e.getMessage());
        }
        return response;
    }

    @ApiOperation(
            value = "新增资源", notes = "新增资源"
    )
    @ApiImplicitParam(name = "permissionInfo", value = "资源信息", dataType = "PermissionInfo", paramType = "query")
    @PostMapping("permission/")
    @SysLog(module = "资源模块", operation = "新增资源")
    public ServerResponse addPermission(PermissionInfo permissionInfo) {
        log.info("需要新增的资源信息 ： " + permissionInfo);
        ServerResponse serverResponse;
        try {
            permissionService.save(permissionInfo);
            serverResponse = ServerResponse.createBySuccessMessage("新增资源成功");
        } catch (Exception e) {
            log.error("新增资源失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("新增资源失败\n " + e.getMessage());
        }
        return serverResponse;
    }

    @ApiOperation(
            value = "修改资源", notes = "修改资源"
    )
    @ApiImplicitParam(name = "permissionInfo", value = "资源信息", dataType = "PermissionInfo", paramType = "query")
    @PutMapping("permission/")
    @SysLog(module = "资源模块", operation = "修改资源")
    public ServerResponse modifyPermission(PermissionInfo permissionInfo) {
        log.info("需要修改的资源信息 ： " + permissionInfo);
        ServerResponse serverResponse;
        try {
            permissionService.save(permissionInfo);
            serverResponse = ServerResponse.createBySuccessMessage("修改资源成功");
        } catch (Exception e) {
            log.error("修改资源失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("修改资源失败\n " + e.getMessage());
        }
        return serverResponse;
    }

    @ApiOperation(
            value = "删除资源", notes = "删除资源"
    )
    @ApiImplicitParam(name = "permissionIds", value = "资源信息ID集合", dataType = "Long[]", paramType = "query")
    @DeleteMapping("permission/{permissionId}")
    @SysLog(module = "资源模块", operation = "删除资源")
    public ServerResponse deletePermission(@PathVariable("permissionId") Long permissionId) {
        log.info("需要删除的资源信息ID： " + permissionId);
        ServerResponse serverResponse;
        try {
            if(permissionService.countChildNum(permissionId)>0){
                serverResponse = ServerResponse.createByErrorMessage("删除资源失败,该资源下有子节点\n请先删除子节点");
            }else{
                permissionService.updateStatus(Arrays.asList(permissionId), "2");
                serverResponse = ServerResponse.createBySuccessMessage("删除资源成功");
            }

        } catch (Exception e) {
            log.error("删除资源失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("删除资源失败\n " + e.getMessage());
        }
        return serverResponse;
    }

    @ApiOperation(
            value = "获取资源", notes = "获取资源"
    )
    @ApiImplicitParam(name = "permissionId", value = "资源信息ID", dataType = "Long", paramType = "query")
    @GetMapping("permission/{permissionId}")
    @SysLog(module = "资源模块", operation = "获取资源")
    public ServerResponse getPermission(@PathVariable("permissionId") Long permissionId) {
        log.info("需要获取的资源信息ID： " + permissionId);
        ServerResponse<Permission> serverResponse;
        try {
            serverResponse = ServerResponse.createBySuccess("获取资源成功",permissionService.get(permissionId));
        } catch (Exception e) {
            log.error("获取资源失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("获取资源失败\n " + e.getMessage());
        }
        return serverResponse;
    }


}
