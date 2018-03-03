package com.javarush.task.task39.task3913;

import java.util.Date;

public class QueryObject {
    private String field1;
    private String field2;
    private Object value;
    private Date after;
    private Date before;

    public QueryObject(String field1, String field2, Object value, Date after, Date before) {
        this.field1 = field1;
        this.field2 = field2;
        this.value = value;
        this.after = after;
        this.before = before;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public Object getValue() {
        return value;
    }

    public Date getAfter() {
        return after;
    }

    public Date getBefore() {
        return before;
    }

    public boolean hasFilterData() {
        return after != null && before != null;
    }

    public boolean hasFilterValue() {
        return field2 != null && value != null;
    }

    public boolean hasFilterField() {
        return field1 != null;
    }

    public QueryType getQueryType() {
        if (hasFilterValue()) {
            return hasFilterData()
                    ? QueryType.QL_HARD
                    : QueryType.QL_MEDIUM;
        } else {
            return hasFilterField()
                    ? QueryType.QL_SIMPLE
                    : QueryType.NO;
        }
    }

    public enum QueryType {
        NO,
        QL_SIMPLE,
        QL_MEDIUM,
        QL_HARD
    }
}
