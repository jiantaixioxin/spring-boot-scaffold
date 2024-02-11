package com.scaffold.service.db;

import com.scaffold.dao.db.SysUserMapper;
import com.scaffold.model.db.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    public SysUser queryById(String userId){
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    public void insert(SysUser user) {
        sysUserMapper.insert(user);
    }
}
