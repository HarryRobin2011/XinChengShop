package com.banksoft.XinChengShop.utils.alipay;

/**
 * Created by harry_robin on 15/9/16.
 */
public class Config {
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPSvhx7AW1jUnh4/" +
            "yzTSUU/Gt9xtsVkYCkhpQdb6mpcUIkedMolXuYop7huaIbDCLG2cpZVrF0h675Wz" +
            "tTaYnUur3asc3WhF5HF9gNsJW8jrh49bC3n8GdHy9a+qYS7y/PaDitXUd7Ch3vBG" +
            "Lex5mPlU34D+Y9SeRPUT3zO76MxHAgMBAAECgYEA1rwwvfkccaHuvidIQ6wL9hI0" +
            "i3us976UT6YoRO2bu9jMmMLJdiUJzMotkAW9aW2PJgcifjYEsfRJzLxw3WAkMWxw" +
            "si+1m0et1xX9Z7xfK4TDhrSS5kh+VHjBApTeOtzZmK8ycFSSNzBzWNT/54rdCrPL" +
            "GT0DjBHbSWOFqnYEx5ECQQD65HZffO89tZh2wSqBw9xHHLgEQnue8SXCHPaFtl36" +
            "GXbknavAb69MZRmqFM9vmAXAYmzwqi9QwIKe44jvX/ppAkEA+aq38MF8ssWgehzG" +
            "Wp3LtsXrjzwQ96aa86ANKmD1F4J0hVmw6ajeze5p3ZLcDQX6hjud/XCb0gJTOZ1/" +
            "R0LbLwJBAPnc1Va7obefRrveHGsnmBTqMQls/JiKKMRs+8amnGkbOyDrNcVsdPRs" +
            "K2cLWpsUl4XFgCEeHAkb7/o1OIbM2yECQEv+NrbFbhtMJCiHEakLVrJvU9/pPJDk" +
            "2U1Gbjtz/6YEQ0tDnaHBkO/Fndufk/My2qp5AwHdgNBPfN2Qnukl5GkCQDUA0Tr1" +
            "GkSdL+8GINmiPVacN/KqQpf+LknlmA5R/zeMXfNOdWE593plq9R8L6tTuXuavzEY" +
            "7KGxEnzJ0IudKMU=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    public static final int SDK_PAY_FLAG = 1;

    public static final int SDK_CHECK_FLAG = 2;


    public  static void main(String[] args){
        System.out.println("支付宝私钥=="+RSA_PRIVATE);

    }

}