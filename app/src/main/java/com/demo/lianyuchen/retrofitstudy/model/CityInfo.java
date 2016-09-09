package com.demo.lianyuchen.retrofitstudy.model;

/**
 * Created by lianyuchen on 16/9/5.
 */
public class CityInfo extends BaseBean {
    /**
     * cityName : 北京
     * provinceName : 北京
     * cityCode : 101010100
     * zipCode : 100000
     * telAreaCode : 010
     */

    private RetDataBean retData;

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String cityName;
        private String provinceName;
        private String cityCode;
        private String zipCode;
        private String telAreaCode;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getTelAreaCode() {
            return telAreaCode;
        }

        public void setTelAreaCode(String telAreaCode) {
            this.telAreaCode = telAreaCode;
        }
    }
}
