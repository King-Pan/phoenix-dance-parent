package club.javalearn.fastsystem.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author king-pan
 * Date: 2018/6/22
 * Time: 下午5:21
 * Description: No Description
 */
@Api(value="/test", tags="测试接口模块")
@RestController
@RequestMapping("/test")
public class TestSwaggerController {
    @ApiOperation(value="展示首页信息", notes = "展示首页信息")
    @GetMapping("/show")
    public Object showInfo(){
        return "hello world";
    }

    @ApiOperation(value="添加用户信息", notes = "添加用户信息")
    @ApiImplicitParam(name="user", value="User", required = true, dataType = "string")
    @PostMapping("/addUser")
    public Object addUser(@RequestBody String user){
        return "success";
    }
}
