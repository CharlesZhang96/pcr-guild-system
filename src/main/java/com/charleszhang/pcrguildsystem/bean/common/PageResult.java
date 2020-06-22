package com.charleszhang.pcrguildsystem.bean.common;

import java.util.List;

/**
 * @author Charles Zhang
 */
public class PageResult<T> {

    /**
     * Total records
     */
    private Long total;

    /**
     * Page of records
     */
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
