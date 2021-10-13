package com.orange.share.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 排序工具类
 */
public class SortUtil {
    public static PageRequest buildDESC(int page, int size,Sort.Direction direction,String field) {
        Sort sort = new Sort(direction, field);
        return new PageRequest(page, size, sort);
    }

}
