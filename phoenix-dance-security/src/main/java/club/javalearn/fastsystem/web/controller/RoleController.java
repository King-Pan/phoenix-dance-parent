package club.javalearn.fastsystem.web.controller;

import club.javalearn.fastsystem.aspect.SysLog;
import club.javalearn.fastsystem.common.Message;
import club.javalearn.fastsystem.common.ServerResponse;
import club.javalearn.fastsystem.model.Role;
import club.javalearn.fastsystem.model.User;
import club.javalearn.fastsystem.parameter.RoleInfo;
import club.javalearn.fastsystem.parameter.UserInfo;
import club.javalearn.fastsystem.service.RoleService;
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

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/7/10
 * Time: 下午5:40
 * Description: No Description
 */
@Slf4j
@RestController
@RequestMapping("/")
@Api(tags = "RoleController", value = "角色管理", description = "角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(
            value = "角色管理页面", notes = "进入角色管理页面"
    )
    @GetMapping("role/")
    @SysLog(module = "角色模块", operation = "进入角色管理页面")
    public ModelAndView rolePage() {
        return new ModelAndView("security/role");
    }


    /**
     * 分页查询
     *
     * @param roleInfo 查询参数
     * @param pageable 分页参数
     * @return 分页信息
     */
    @ApiOperation(value = "角色分页查询", notes = "角色分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleInfo", value = "查询参数", dataType = "RoleInfo", paramType = "query"),
            @ApiImplicitParam(name = "pageable", value = "分页参数", dataType = "Pageable", paramType = "query")
    })

    @GetMapping("roles/")
    public Message<Role> getUserList(RoleInfo roleInfo, @PageableDefault Pageable pageable) {
        if (log.isDebugEnabled()) {
            log.debug("查询参数 {}", roleInfo);
        }
        return roleService.getPageList(roleInfo, pageable);
    }

    /**
     * 分页查询未分配的角色信息
     *
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 分页信息
     */
    @ApiOperation(value = "未分配的角色信息", notes = "未分配的角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageable", value = "分页参数", dataType = "Pageable", paramType = "query")
    })
    @GetMapping("role/{userId}")
    @SysLog(module = "角色模块", operation = "分页查询未分配的角色信息")
    public Object getNoSelectRole(@PathVariable("userId")Long userId,@RequestParam("name")String name, @PageableDefault Pageable pageable) {
        if (log.isDebugEnabled()) {
            log.debug("查询参数 {}", userId);
        }
        return roleService.getNoSelectRole(userId, name,pageable);
    }

    @ApiOperation(
            value = "删除角色", notes = "删除角色"
    )
    @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "Long", paramType = "query")
    @DeleteMapping("role/{roleId}")
    @SysLog(module = "角色模块", operation = "删除角色")
    public ServerResponse deleteUser(@PathVariable("roleId") Long roleId) {
        log.info("需要的角色ID： " + roleId);
        ServerResponse serverResponse;
        try {
            roleService.deleteRoles(Arrays.asList(roleId));
            serverResponse = ServerResponse.createBySuccessMessage("删除角色成功");
        } catch (Exception e) {
            log.error("删除角色失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("删除角色失败\n " + e.getMessage());
        }
        return serverResponse;
    }



    @ApiOperation(
            value = "批量删除角色", notes = "批量删除角色"
    )
    @ApiImplicitParam(name = "roleIds", value = "角色ID集合", dataType = "Long[]", paramType = "query")
    @DeleteMapping("role/")
    @SysLog(module = "角色模块", operation = "批量删除角色")
    public ServerResponse batchDeleteUser(@RequestParam("roleIds[]") Long[] roleIds) {
        log.info("需要批量删除的角色ID： " + Arrays.toString(roleIds));
        ServerResponse serverResponse;
        try {
            roleService.deleteRoles(Arrays.asList(roleIds));
            serverResponse = ServerResponse.createBySuccessMessage("批量删除角色成功");
        } catch (Exception e) {
            log.error("批量删除角色失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("批量删除角色失败\n " + e.getMessage());
        }
        return serverResponse;
    }


    @ApiOperation(
            value = "新增角色", notes = "新增角色"
    )
    @ApiImplicitParam(name = "roleInfo", value = "角色信息", dataType = "RoleInfo", paramType = "query")
    @PostMapping("role/")
    @SysLog(module = "角色模块", operation = "新增角色")
    public ServerResponse addUser(RoleInfo roleInfo) {
        log.info("需要新增的角色信息 ： " + roleInfo);
        ServerResponse serverResponse;
        try {
            roleService.save(roleInfo);
            serverResponse = ServerResponse.createBySuccessMessage("新增角色成功");
        } catch (Exception e) {
            log.error("新增角色失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("新增角色失败\n " + e.getMessage());
        }
        return serverResponse;
    }

    @ApiOperation(
            value = "修改角色", notes = "修改角色"
    )
    @ApiImplicitParam(name = "roleInfo", value = "角色信息", dataType = "RoleInfo", paramType = "query")
    @PutMapping("role/")
    @SysLog(module = "角色模块", operation = "修改角色")
    public ServerResponse modifyRole(RoleInfo roleInfo) {
        log.info("需要修改的角色信息: " + roleInfo);
        ServerResponse serverResponse;
        try {
            roleService.save(roleInfo);
            serverResponse = ServerResponse.createBySuccessMessage("修改角色成功");
        } catch (Exception e) {
            log.error("修改角色失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("修改角色失败\n " + e.getMessage());
        }
        return serverResponse;
    }

    @ApiOperation(
            value = "修改状态", notes = "修改状态"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "status", value = "状态0:禁用 ,1: 启用 2: 删除", dataType = "String", paramType = "path")
    })
    @PostMapping("role/{roleId}/{status}")
    @SysLog(module = "角色模块", operation = "修改角色")
    public ServerResponse modifyStatus(@PathVariable("roleId") Long roleId, @PathVariable("status") String status) {
        log.info("修改角色状态: roleId = {},status = {}", roleId, status);
        ServerResponse serverResponse;
        try {
            roleService.modifyStatus(roleId,status);
            serverResponse = ServerResponse.createBySuccessMessage("修改状态成功");
        } catch (Exception e) {
            log.error("修改状态失败\n", e);
            serverResponse = ServerResponse.createByErrorMessage("修改状态失败\n " + e.getMessage());
        }
        return serverResponse;
    }
}
