package com.wuyiccc.tianxuan.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wuyiccc
 * @date 2024/1/1 10:13
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataDictionaryBO {

    private String id;


    /**
     * 数据字典的类别代码code，根据本code可以找到对应的所有下属的value列表，例如：benefits（同一类型item所对应的code都是一致的）
     */
    private String typeCode;

    /**
     * 数据字典的类别名称，例如：福利待遇（同一类型item所对应的name都是一致的）
     */
    private String typeName;

    /**
     * 字典项，数据字典类别下的所有key，每个key对应一个value，可以根据类型key和字典key同时找到具体某一项字典value，例如：travel-旅游
     */
    private String itemKey;

    /**
     * 每个单个的数据字典的值，唯一key所对应的值value，例如：旅游
     */
    private String itemValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 对应的字体图标，可以为空
     */
    private String icon;

    /**
     * 是否开启，1：启用，0：停用
     */
    private Boolean enable;
}
