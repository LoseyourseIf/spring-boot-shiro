package xingyu.lu.springboot.shiro.service;

import xingyu.lu.springboot.shiro.domain.UserExt;

/**
 * @author xingyu.lu
 * @create 2019-06-04 11:40
 **/
public interface UserService {
    UserExt getUserById(Integer userId);
}
