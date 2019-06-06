package xingyu.lu.springboot.shiro.api;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xingyu.lu.springboot.shiro.domain.UserExt;
import xingyu.lu.springboot.shiro.utils.result.ResultModel;

/**
 * @author xingyu.lu
 * @create 2019-06-06 11:42
 **/
@RestController
public class Example {

    @RequestMapping("/login")
    public ResultModel userLogin(@RequestBody UserExt userExt) {
        return ResultModel.success("Login Succeed!");
    }

    @GetMapping("/auth/require")
    @RequiresAuthentication
    public ResultModel requireAuth() {
        return ResultModel.success("Authenticated!");
    }

    @GetMapping("/role/require")
    @RequiresRoles("admin")
    public ResultModel requireRole() {
        return ResultModel.success("Role Required!");
    }

    @GetMapping("/permission/require")
    @RequiresPermissions(logical = Logical.AND, value = {"delete", "edit"})
    public ResultModel requirePermission() {
        return ResultModel.success("Permission Required!");
    }

    @RequestMapping("/401")
    public ResultModel unAuthorized() {
        return ResultModel.customError(
                String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
