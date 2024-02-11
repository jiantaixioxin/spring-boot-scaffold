package com.scaffold.manager;

import com.scaffold.model.db.SysUser;
import com.scaffold.model.web.SysUserView;
import com.scaffold.service.db.SysUserService;
import com.scaffold.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Component
public class SysUserManager {
    private static final Logger logger= LoggerFactory.getLogger(SysUserManager.class);
    @Resource
    private SysUserService sysUserService;

    public SysUser query(String userId) {
        return sysUserService.queryById(userId);
    }

    public String add(SysUserView userView) {
        SysUser user=new SysUser();
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setUserName(userView.getUserName());
        user.setPhoneNo(userView.getPhoneNo());
        user.setPassword(userView.getPassword());
        user.setCreateId("123");
        user.setCreateTime(DateUtils.getNowDate());
        sysUserService.insert(user);
        return userId;
    }
}
