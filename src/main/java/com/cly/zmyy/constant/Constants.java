package com.cly.zmyy.constant;

public class Constants {
    public static String RESOURCE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\";

    public static String USER_AGENT = "Mozilla/5.0 (Linux; Android 7.1.2; TAS-AN00 Build/N2G47H; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.70 Mobile Safari/537.36 MicroMessenger/7.0.12.1620(0x27000C34) Process/appbrand0 NetType/WIFI Language/zh_CN ABI/arm64";

    public static String REFER = "https://servicewechat.com/wx2c7f0f3c30d99445/72/page-frame.html";
    public static String BASE_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx";
    //某地区医院列表URL
    public static String CUSTOMER_LIST_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=CustomerList";
    //授权URL
    public static String AUTH_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=auth&code=061H55000QOs8L1yHN100Ba0N43H550I";
    //某医院内HPV疫苗情况URL
    public static String CUSTOMER_PRODUCT_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=CustomerProduct";
    //预约用户信息
    public static String USER_INFO_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=User";
    public static String CUST_SUBSCRIBE_DATE_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=GetCustSubscribeDateAll";
    public static String CAPTCHA_VERIFY_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=CaptchaVerify";
    public static String GET_CAPTCHA_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=GetCaptcha";
    public static String SAVE_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=Save20";
    //获取订单状态
    public static String ORDER_STATUS_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=GetOrderStatus";
    public static String CUST_SUBSCRIBE_DATE_DETAIL_URL = "https://cloud.cn2030.com/sc/wx/HandlerSubscribe.ashx?act=GetCustSubscribeDateDetail";

}
