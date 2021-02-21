package com.cly.zmyy;

import com.cly.zmyy.service.ZmyyService;

public class Main {
    public static void main(String[] args) {

        ZmyyService zmyyService = new ZmyyService();
        zmyyService.init();
        zmyyService.run();

    }
}
