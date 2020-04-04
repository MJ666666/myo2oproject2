package com.lmj.o2o.oauth2server.service;


import com.lmj.o2o.entity.LocalAuth;
import com.lmj.o2o.entity.TbPermission;
import com.lmj.o2o.oauth2server.dao.TbPermissionDao;
import com.lmj.o2o.oauth2server.dao.TbUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: UsersDetailServiceImpl
 * Description:
 * date: 2020/4/3 17:52
 *
 * @author MJ
 */
public class UsersDetailServiceImpl implements UserDetailsService {

    @Autowired
    private TbUserDao userDao;

    @Autowired
    private TbPermissionDao permissionDao;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LocalAuth tbUser = new LocalAuth();
        tbUser.setUserName(s);
        LocalAuth userQuery = userDao.selectUserByUserName(tbUser);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (userQuery != null) {
            List<TbPermission> tbPermissions = permissionDao.selectPermissionByUserId(userQuery);
            tbPermissions.forEach((permission)->{
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getEnname());
                grantedAuthorities.add(grantedAuthority);
            });
            return new User(userQuery.getUserName(), userQuery.getPassword(), grantedAuthorities);
        }
        return null;
    }
}
