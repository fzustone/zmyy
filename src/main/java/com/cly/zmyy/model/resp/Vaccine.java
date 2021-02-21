package com.cly.zmyy.model.resp;

import lombok.Data;

import java.util.List;

@Data
public class Vaccine {
    private int id;

    private String text;

    private int price;

    private String descript;

    private String warn;

    private List<String> tags;

    private int questionnaireId;

    private String remarks;

    private List<NumbersVaccine> numbersVaccine;

    private String date;

    private boolean enable;

    private String BtnLable;

    @Data
    public static class NumbersVaccine {
        private String cname;

        private int value;
    }
}
