package com.jkb.api.entity.baidu.map.webService;

/**
 * 百度地图Web服务geocoder的地址解析接口
 * Created by JustKiddingBaby on 2016/8/13.
 */

public class GeocoderEntity {


    /**
     * status : 0
     * result : {"location":{"lng":116.30775539540981,"lat":40.05685561073758},"precise":1,"confidence":80,"level":"商务大厦"}
     */

    private int status;
    /**
     * location : {"lng":116.30775539540981,"lat":40.05685561073758}
     * precise : 1
     * confidence : 80
     * level : 商务大厦
     */

    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * lng : 116.30775539540981
         * lat : 40.05685561073758
         */

        private LocationBean location;
        private int precise;
        private int confidence;
        private String level;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public int getPrecise() {
            return precise;
        }

        public void setPrecise(int precise) {
            this.precise = precise;
        }

        public int getConfidence() {
            return confidence;
        }

        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public static class LocationBean {
            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }
    }
}
