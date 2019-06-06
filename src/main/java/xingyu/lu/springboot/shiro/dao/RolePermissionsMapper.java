package xingyu.lu.springboot.shiro.dao;

import java.util.List;
import xingyu.lu.springboot.shiro.domain.RolePermissions;

public interface RolePermissionsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_permissions
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer rolePermissionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_permissions
     *
     * @mbggenerated
     */
    int insert(RolePermissions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_permissions
     *
     * @mbggenerated
     */
    RolePermissions selectByPrimaryKey(Integer rolePermissionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_permissions
     *
     * @mbggenerated
     */
    List<RolePermissions> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_permissions
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RolePermissions record);
}