package com.wuyiccc.tianxuan.user.service.impl;

import com.wuyiccc.tianxuan.pojo.test.Stu;
import com.wuyiccc.tianxuan.user.mapper.StuMapper;
import com.wuyiccc.tianxuan.user.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuyiccc
 * @date 2023/6/22 20:41
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveStu(Stu stu) {
        stuMapper.insert(stu);
    }
}
