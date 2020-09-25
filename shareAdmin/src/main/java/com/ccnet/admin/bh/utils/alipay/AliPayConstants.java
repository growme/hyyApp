package com.ccnet.admin.bh.utils.alipay;

/**
 * @Author jingjie
 * @Date 2020/9/24 0024 17:09
 * @Description 支付宝常量
 **/
public class AliPayConstants {

    /**
     * 支付宝公钥
     */
    public static final String RAS_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiD3D0P8ri+HFVvFdFxLt7Zh3erAldI496JGZDoGKAu6SJJOuOc/57S+PSzFgLxehd3Y9NxOLzmT2ScLy0bB2Rk0EY0R6XlzfQY9qqkAVtWFIEh2KrCVfXT0aAIV5KRPEihmjslK9yLqZwafXpaZzIzgeZ0UH5uIF4YKm7K9xiwULlGDakEgYlOOEsxSjsYaiG0fzDgUKXmZRSo9uQ9E+zbIslKeSKiO5CLFGeufxi/kKGgsyKrtJTxAX6aT/f0H/+gswO+NC3FRSNdgOt/bqr5McM1wEpB64bZk+W8tMYmIpXyjOQDPKd6Iac8DmrOuTJjwa4LdwIskMlgg665mB4wIDAQAB";

    /**
     * 商户私钥
     */
    public static final String BUS_RAS_PRIVATE_KET = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIPcPQ/yuL4cVW8V0XEu3tmHd6sCV0jj3okZkOgYoC7pIkk645z/ntL49LMWAvF6F3dj03E4vOZPZJwvLRsHZGTQRjRHpeXN9Bj2qqQBW1YUgSHYqsJV9dPRoAhXkpE8SKGaOyUr3IupnBp9elpnMjOB5nRQfm4gXhgqbsr3GLBQuUYNqQSBiU44SzFKOxhqIbR/MOBQpeZlFKj25D0T7NsiyUp5IqI7kIsUZ65/GL+QoaCzIqu0lPEBfppP9/Qf/6CzA740LcVFI12A639uqvkxwzXASkHrhtmT5by0xiYilfKM5AM8p3ohpzwOas65MmPBrgt3AiyQyWCDrrmYHjAgMBAAECggEAB+wnjei4vIqlchfZBPV51n4uPHkvxGZdHUASOXC4+NCsuOnJkN0SOIOyfnnhkPt7zTmX97LwR1FBrSpj+mObjRz/a1JZF1GpPeO8HSha2tBMY5lKLhaCRCbAc1VPpV7b5/fzt/Pty1VmiYdcF+rk+SBTTEgMXJW6J9H0KQFOxfmwXec3CB+G4PI/3uysA0o8au6D8jRYE7ZvaB5iQ4X1Ra5P7j6BOX3GIJqxcvW+NUdmQ7KbKD4IXPkjwo87zmyOK3M42DmpGGLxZHkrFJAFtV3JgPzFeQ1Chf6gFS3rZ7WV1bJoimxFnfHkmS1hirLgqKIPceOxbkUIUQMP2gZAmQKBgQDHHiq/wtfSAGVNSiNumfv8lG/X6+164B1yAUsMbMTmkZHeXRyNHNIymx8zpxLrlHuYjWvSieqDKs5jcmU3XC6zbTx7BEwKGT/OT0heqC5ixPNgd8kRiQmlCVG1+pZUKtjTh/BunEueMn81IxEYe8EjX/W6TGtHOMLuTk/p1NgyXwKBgQCvKVNDN/BgV9OKKBU3g0Wcg5/SiXQKSJQIKG7gq7TDeAGQ4iK7943feayJWHzhOJuipZ62KI+1e2opXmUsUP+CjrqrlMtTw0dyrRcBj89dTVYej458OED4COmvfPMZj2aR4BIiuYyt5rj+O2TbsExEroOievaxagbAKpu1ta6G/QKBgDVZQMzmEFsEnh+qKHoeqaQPxTON06/jUiBUdl4a2lqIUBPK0LaCLVM/uollTy1tN/w0j9n1964zDt8YLEQ734tVTioLia/++dh154bs+pid+jMzhNy84aelKo5eWF6uqC9ssZ+BIsV89BYxkggESEgJEtZaWTfY3mlo9AJLc61zAoGATptqHVlmS6Y+bdrtIpuLyuCbAd3jsJW/sSWMFNGZ7rrVYRLK6bX/+lFdZ8nJST2hQs22uyLa02QOUYBmQQwcjpfFGClPUkjpsIITOAU01zdUtaRRGlgYd/dv/+xAILFlI4P+P40roANR8+3tplWo9I2oz2sFeLsPSoAUnuho6XUCgYEAr7pNOGaC5kHs+ry18m2q5n+mu/BeR4odGy3+qgtNs0fqy7oyoCxOAwQtwd4Sh7HQeuE3Skscv4zgPRIXjZeq7LAP/YaMTpYlZ1HfMej/E2+yIzUaskTyvvC6/gqBljcp6l0NNHZvtqekJlmSuHUzkXnlCsvksgHvB5xnkjpHJ7E=";

    /**
     * APPID
     */
    public static final String OPEN_ID = "test";

    /**
     * 支付宝沙箱测试环境，应用未上线，沙箱环境测试有效
     */
    public static final String REQUEST_URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 编码格式
     */
    public static String CHARSET = "UTF-8";

    /**
     * 参数格式
     */
    public static String FORMAT = "json";

    /**
     * 加密方式
     */
    public static String SIGNTYPE = "RSA2";

    /**
     * 支付类型-提现(固定)
     */
    public static String PAY_TYPE = "ALIPAY_LOGONID";

    /**
     * 支付宝回调-交易成功、不能对订单信息进行操作
     */
    public static String TRADE_FINISHED = "TRADE_FINISHED";

    /**
     * 支付宝回调-交易成功、处理后续逻辑如退款
     */
    public static String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 平台和支付宝签约属性-固定值
     */
    public static String PRODUCT_CODE = "QUICK_WAP_WAY";

    /**
     * 支付宝提现成功状态
     */
    public static String SUCCESS_CODE = "10000";
}
