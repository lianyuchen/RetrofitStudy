package com.demo.lianyuchen.retrofitstudy.model;

/**
 * Created by lianyuchen on 16/9/5.
 */
public class BaseBean {

    /**
     * errNum : 0
     * errMsg : success
     * retData : [{"province_cn":"北京","district_cn":"北京","name_cn":"朝阳","name_en":"chaoyang","area_id":"101010300"},{"province_cn":"辽宁","district_cn":"朝阳","name_cn":"朝阳","name_en":"chaoyang","area_id":"101071201"},{"province_cn":"辽宁","district_cn":"朝阳","name_cn":"凌源","name_en":"lingyuan","area_id":"101071203"},{"province_cn":"辽宁","district_cn":"朝阳","name_cn":"喀左","name_en":"kazuo","area_id":"101071204"},{"province_cn":"辽宁","district_cn":"朝阳","name_cn":"北票","name_en":"beipiao","area_id":"101071205"},{"province_cn":"辽宁","district_cn":"朝阳","name_cn":"建平县","name_en":"jianpingxian","area_id":"101071207"}]
     */

    private int errNum;
    private String msg;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return msg;
    }

    public void setErrMsg(String msg) {
        this.msg = msg;
    }
}
