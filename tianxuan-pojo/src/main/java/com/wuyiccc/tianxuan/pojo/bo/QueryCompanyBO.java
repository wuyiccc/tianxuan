package com.wuyiccc.tianxuan.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author wuyiccc
 * @date 2024/1/8 22:06
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryCompanyBO {

    private String companyName;

    private String commitUser;

    private Integer reviewStatus;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate commitDateStart;


    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate commitDateEnd;


}
