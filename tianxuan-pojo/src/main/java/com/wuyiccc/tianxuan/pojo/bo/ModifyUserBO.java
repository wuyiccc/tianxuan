package com.wuyiccc.tianxuan.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taobao.api.internal.mapping.DingTalkErrorField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import java.time.LocalDate;

/**
 * @author wuyiccc
 * @date 2023/12/22 20:42
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ModifyUserBO {

    private String userId;

    private String face;

    private Integer sex;

    private String nickname;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Email
    private String email;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate startWorkDate;

    private String position;
}
