package com.cly.zmyy.model;

import lombok.Data;

import java.util.List;

@Data
public class CityInfo {
    private String name;
    private String value;
    private List<CityInfo> children;
}
