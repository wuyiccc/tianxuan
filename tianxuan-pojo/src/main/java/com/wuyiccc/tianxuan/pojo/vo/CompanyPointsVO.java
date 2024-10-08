package com.wuyiccc.tianxuan.pojo.vo;

import com.wuyiccc.tianxuan.pojo.DataDictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/1/15 20:16
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPointsVO {

    private List<DataDictionary> advantageList;

    private List<DataDictionary> benefitsList;

    private List<DataDictionary> bonusList;

    private List<DataDictionary> subsidyList;

}
