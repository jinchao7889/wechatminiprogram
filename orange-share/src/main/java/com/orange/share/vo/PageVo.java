package com.orange.share.vo;

import lombok.Data;

@Data
public class PageVo {
    private Integer pages;
    private Integer nowPage;
    private Long totalRecord;
    private Object dataList;

    public PageVo(){

    }
    public PageVo( Integer pages,
            Integer nowPage,
            Long totalRecord,
            Object dataList){
        this.pages=pages;
        this.dataList=dataList;
        this.nowPage=nowPage;
        this.totalRecord=totalRecord;
    }
}
