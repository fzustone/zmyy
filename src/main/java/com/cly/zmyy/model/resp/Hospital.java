package com.cly.zmyy.model.resp;

import lombok.Data;

@Data
public class Hospital {
    private int id;

    private String cname;

    private String addr;

    private String SmallPic;

    private double lat;

    private double lng;

    private String tel;

    private String addr2;

    private int province;

    private int city;

    private int county;

    private int sort;

    private double distance;
}
