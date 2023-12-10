package com.wuyiccc.tianxuan.common.result;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/10 15:00
 */
@Data
public class PagedGridResult {

    /**
     * 当前页
     */
    private int page;

    /**
     * 总页数
     */
    private long total;

    /**
     * 总记录数
     */
    private long records;

    /**
     * 当前页记录数据
     */
    private List<?> rows;



    public static PagedGridResult build(List<?> list
                                    , Integer page) {

        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setRows(pageList.getList());
        pagedGridResult.setPage(page);
        pagedGridResult.setRecords(pageList.getTotal());
        pagedGridResult.setTotal(pageList.getPages());
        return pagedGridResult;
    }


}
