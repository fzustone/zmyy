package com.cly.zmyy.model.resp;

import lombok.Data;

@Data
public class SubTimeDetail {
    private String customer;

    private int customerid;

    private String StartTime;

    private String EndTime;

    private String mxid;

    private int qty;
}
