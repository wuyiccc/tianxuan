package com.wuyiccc.tianxuan.search.pojo.es;

import cn.hutool.extra.tokenizer.engine.ikanalyzer.IKAnalyzerWord;
import lombok.Data;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.Analyzer;
import org.dromara.easyes.annotation.rely.FieldType;
import org.dromara.easyes.annotation.rely.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wuyiccc
 * @date 2024/6/22 17:00
 */
@Data
@IndexName(value = "agg_user", aliasName = "agg_user_alias")
public class AggUserEsEntity {

    @IndexId(type = IdType.UUID)
    private String id;

    @IndexField(fieldType = FieldType.INTEGER)
    private Integer type;


    @IndexField(fieldType = FieldType.KEYWORD)
    private String userId;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String companyId;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String resumeId;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String face;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String nickname;


    @IndexField(fieldType = FieldType.INTEGER)
    private Integer sex;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String province;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String industry;

    @IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_MAX_WORD)
    private String companyName;


    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd")
    private LocalDate createDate;
}
