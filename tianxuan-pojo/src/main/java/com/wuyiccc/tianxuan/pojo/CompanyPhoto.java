package com.wuyiccc.tianxuan.pojo;

import lombok.Data;

/**
 * <p>
 * 企业相册表，本表只存企业上传的图片
 * </p>
 *
 * @author wuyiccc
 * @since 2023-06-22
 */
@Data
public class CompanyPhoto {

    /**
     * 企业id
     */
    private String companyId;

    private String photos;

}
