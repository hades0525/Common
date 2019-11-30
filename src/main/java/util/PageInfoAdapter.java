package com.citycloud.ccuap.ybhw.util;

import com.github.pagehelper.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class PageInfoAdapter<T> extends PageImpl<T> {

    public PageInfoAdapter(Page<T> page) {
        super(page.getResult(), PageRequest.of(page.getPageNum() - 1, page.getPageSize()), page.getTotal());
    }

    public <E> PageInfoAdapter(Page<E> page, List<T> result) {

        super(result, PageRequest.of(page.getPageNum() - 1, page.getPageSize()), page.getTotal());
    }

    public PageInfoAdapter(List<T> result) {

        super(result, PageRequest.of(1, result != null && !result.isEmpty() ? result.size() : 10), result != null ? result.size() : 0);
    }


    public PageInfoAdapter(int page, int size, int total, List<T> result) {

        super(result, PageRequest.of(page - 1, size), total);
    }

}
