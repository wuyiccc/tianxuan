package com.wuyiccc.tianxuan.search.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.wuyiccc.tianxuan.common.enumeration.AggUserTypeEnum;
import com.wuyiccc.tianxuan.search.mapper.es.AggUserMapper;
import com.wuyiccc.tianxuan.search.pojo.es.AggUserEsEntity;
import com.wuyiccc.tianxuan.search.service.AggUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wuyiccc
 * @date 2024/6/22 20:13
 */
@Service
@Slf4j
public class AggUserServiceImpl implements AggUserService {

    private static final Random random = new Random();

    @Resource
    private AggUserMapper aggUserMapper;



    @Override
    public void mockData() {

        List<AggUserEsEntity> dataList = generateTestData(50);

        aggUserMapper.insertBatch(dataList);
    }

    public static List<AggUserEsEntity> generateTestData(int numberOfRecords) {
        List<AggUserEsEntity> testData = new ArrayList<>();
        for (int i = 0; i < numberOfRecords; i++) {
            AggUserEsEntity entity = new AggUserEsEntity();
            entity.setType(getRandomType());
            entity.setUserId("user" + i);
            entity.setCompanyId("company" + i);
            entity.setResumeId("resume" + i);
            entity.setFace("face" + i);
            entity.setNickname("nickname" + i);
            entity.setSex(RandomUtil.randomInt(0, 3)); // Assuming 0 for male, 1 for female
            entity.setProvince(getProvince());
            entity.setIndustry(getIndustry());
            entity.setCompanyName("companyName" + i);
            entity.setCreateDate(LocalDate.now().minusDays(RandomUtil.randomInt(0, 7)));
            testData.add(entity);
        }
        return testData;
    }

    private static int getRandomType() {
        AggUserTypeEnum[] types = AggUserTypeEnum.values();
        return types[random.nextInt(types.length)].type;
    }

    private static String getProvince() {

        String[] s = new String[] {"北京", "上海", "江苏", "广东"};

        int res = RandomUtil.randomInt(0, 3);
        return s[res];
    }

    private static String getIndustry() {

        String[] s = new String[] {"IT", "医疗医药", "电子游戏", "房地产", "影视媒体", "电子通信"};

        int res = RandomUtil.randomInt(0, 3);
        return s[res];
    }
}
