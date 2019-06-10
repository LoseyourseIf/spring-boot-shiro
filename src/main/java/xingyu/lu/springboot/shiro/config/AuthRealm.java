package xingyu.lu.springboot.shiro.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xingyu.lu.springboot.shiro.domain.UserExt;
import xingyu.lu.springboot.shiro.service.UserService;
import xingyu.lu.springboot.shiro.utils.jwt.JwtClaimGetter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRealm.class);

    @Resource
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 支持自己的 JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtAuthToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如RequiresRoles,RequiresPermissions
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = principals.toString();
        Integer userId = JwtClaimGetter.getClaimValue(token, JwtClaimGetter.UID, Integer.class);
        //Token验证
        if (null == userId) {
            throw new AuthenticationException("Invalid Token");
        }
        UserExt userExt = userService.getUserById(userId);
        if (userExt == null) {
            throw new AuthenticationException("User Didn't Existed!");
        }
        //角色
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (StringUtils.isNotBlank(userExt.getRoleNames())) {
            Set<String> roles = new HashSet<>(Arrays.asList(userExt.getRoleNames().split(",")));
            simpleAuthorizationInfo.addRoles(roles);
        }
        //权限
        if (StringUtils.isNotBlank(userExt.getPermissions())) {
            Set<String> permission = new HashSet<>(Arrays.asList(userExt.getPermissions().split(",")));
            simpleAuthorizationInfo.addRoles(permission);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 使用此方法进行登录Token验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        Integer userId = JwtClaimGetter.getClaimValue(token, JwtClaimGetter.UID, Integer.class);
        if (null == userId) {
            throw new AuthenticationException("Invalid Token");
        }
        UserExt userExt = userService.getUserById(userId);
        if (userExt == null) {
            throw new AuthenticationException("User Didn't Existed!");
        }
        return new SimpleAuthenticationInfo(token, token, "auth_realm");
    }
}
