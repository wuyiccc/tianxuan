package com.wuyiccc.tianxuan.auth.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyiccc.tianxuan.api.feign.WorkMicroServiceFeign;
import com.wuyiccc.tianxuan.auth.mapper.UserMapper;
import com.wuyiccc.tianxuan.auth.service.UserService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.constant.MQConstants;
import com.wuyiccc.tianxuan.common.enumeration.SexEnum;
import com.wuyiccc.tianxuan.common.enumeration.ShowWhichNameEnum;
import com.wuyiccc.tianxuan.common.enumeration.UserRoleEnum;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import com.wuyiccc.tianxuan.common.util.*;
import com.wuyiccc.tianxuan.pojo.User;
import com.wuyiccc.tianxuan.pojo.dto.SmsCodeDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/6/27 22:43
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    public RedisUtils redisUtils;

    @Resource
    private WorkMicroServiceFeign workMicroServiceFeign;


    @Resource
    private DingDingMsgUtils dingDingMsgUtils;

    private String defaultFaceImgUrl = "http://www.wuyiccc.com/imgs/avatar2.jpg";

    @Resource
    private DefaultMQProducer defaultMQProducer;

    @Override
    public User queryUserByMobile(String mobile) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile, mobile);
        return userMapper.selectOne(wrapper);
    }

    //@Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    @Override
    public User createUser(String mobile) {
        User user = new User();
        user.setMobile(mobile);
        user.setNickname("用户" + DesensitizationUtils.commonDisplay(mobile));
        user.setRealName("用户" + DesensitizationUtils.commonDisplay(mobile));
        user.setShowWhichName(ShowWhichNameEnum.NICKNAME.code);

        user.setSex(SexEnum.SECRET.code);
        user.setFace(defaultFaceImgUrl);
        user.setEmail("");

        user.setBirthday(LocalDateUtils.parseLocalDate("1980-01-01", LocalDateUtils.DATE_PATTERN));
        user.setCountry("中国");
        user.setProvince("");
        user.setCity("");
        user.setDistrict("");
        user.setDescription("这家伙很懒, 什么都没留下");

        // 默认使用注册当天的日期
        user.setStartWorkDate(LocalDate.now());
        user.setPosition("工程师");
        user.setRole(UserRoleEnum.CANDIDATE.code);
        user.setHrInWhichCompanyId("");
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        userMapper.insert(user);

        CommonResult<String> httpRes = workMicroServiceFeign.init(user.getId());
        //if (httpRes.getStatus() != 200) {
        //    throw new CustomException(ResponseStatusEnum.USER_REGISTER_FAILED);
            // 如果调用状态不是200, 则手动回滚全局事务
            // 从当前线程获得xid
            //String xid = RootContext.getXID();
            //if (StringUtils.isNotBlank(xid)) {
            //    try {
            //        GlobalTransactionContext.reload(xid).rollback();
            //    } catch (Exception e) {
            //        log.error("回滚全局事务失败", e);
            //    } finally {
            //        throw new CustomException(ResponseStatusEnum.USER_REGISTER_FAILED);
            //    }
            //}
        //}


        return user;
    }

    @Override
    public void getSMSCode(String mobile, HttpServletRequest request) {
        // 限制用户只能在60s以内获得一次验证码
        String requestIp = IPUtils.getRequestIp(request);
        redisUtils.setnx60s(BaseInfoProperties.MOBILE_SMSCODE + ":" + requestIp, mobile);

        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        log.info("手机号: {}, 当前验证码为: {}", mobile, code);


        SmsCodeDTO smsCodeDTO = new SmsCodeDTO(mobile, code, LocalDateTime.now().toInstant(ZoneOffset.of(ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now()).toString())).toEpochMilli());


        List<Message> messageList = new ArrayList<>(1);
        Message message = new Message(MQConstants.TOPIC_SMS_CODE
                , StringPool.EMPTY
                , StringPool.EMPTY
                , JSONUtil.toJsonStr(smsCodeDTO).getBytes());
        messageList.add(message);
        SendResult send = null;
        try {
            send = defaultMQProducer.send(messageList);
        } catch (Exception e) {
            throw new CustomException("短信验证码发送失败");
        }
        log.info("SendResult: {}", JSONUtil.toJsonStr(send));

        // 设置验证码过期时间为30min
        redisUtils.set(BaseInfoProperties.MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);

        //dingDingMsgUtils.sendSMSCode(code);
    }


}
