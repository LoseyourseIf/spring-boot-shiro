package xingyu.lu.springboot.shiro.api;

import org.apache.shiro.SecurityUtils;
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
 * <p>
 * RequiresAuthentication:
 * 使用该注解标注的类，实例，方法在访问或调用时，当前Subject必须已经过认证。
 * </p><p>
 * RequiresGuest:
 * 使用该注解标注的类，实例，方法在访问或调用时，当前Subject可以是“gust”身份，不需要经过认证。
 * </p><p>
 * RequiresPermissions:
 * 当前Subject需要拥有某些特定的权限时，才能执行被该注解标注的方法。如果当前Subject不具有这样的权限，则方法不会被执行。
 * </p><p>
 * RequiresRoles:
 * 当前Subject必须拥有所有指定的角色时，才能访问被该注解标注的方法。如果当天Subject不同时拥有所有指定角色，则方法不会执行还会抛出AuthorizationException异常。
 * </p><p>
 * RequiresUser
 * 当前Subject必须是应用的用户，才能访问或调用被该注解标注的类，实例，方法。
 * </p>
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
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        return ResultModel.customError(
                String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
