package com.jkb.api.entity.baidu.map.webService;

import java.util.List;

/**
 * 百度地图Web服务的geocoding的逆向数据解析类
 * Created by JustKiddingBaby on 2016/8/13.
 */

public class ReverseGeocodingEntity {


    /**
     * status : 0
     * message : MCODE参数不存在，mobile类型mcode参数必需
     * result : {"location":{"lng":116.32298699999993,"lat":39.98342407140365},"formatted_address":"北京市海淀区中关村大街27号1101-08室","business":"中关村,人民大学,苏州街","addressComponent":{"adcode":"110108","city":"北京市","country":"中国","direction":"附近","distance":"7","district":"海淀区","province":"北京市","street":"中关村大街","street_number":"27号1101-08室","country_code":0},"pois":[{"addr":"北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1","cp":"NavInfo","direction":"内","distance":"0","name":"北京远景国际公寓(中关村店)","poiType":"房地产","point":{"x":116.32294589160055,"y":39.983610361549296},"tag":"房地产","tel":"","uid":"35a08504cb51b1138733049d","zip":""},{"addr":"海淀区中关村北大街27号","cp":" ","direction":"附近","distance":"21","name":"中关村大厦","poiType":"房地产","point":{"x":116.32290995938015,"y":39.98356198726214},"tag":"房地产;写字楼","tel":"","uid":"06d2dffdaef1b7ef88f15d04","zip":""},{"addr":"北京市海淀区中关村大街27号中关村大厦二层","cp":" ","direction":"附近","distance":"5","name":"眉州东坡酒楼","poiType":"美食","point":{"x":116.32293690854546,"y":39.98343759607948},"tag":"美食;中餐厅","tel":"","uid":"2c0bd6c57dbdd3b342ab9a8c","zip":""},{"addr":"北京市海淀区中关村大街29号（海淀黄庄路口）","cp":" ","direction":"东北","distance":"116","name":"海淀医院","poiType":"医疗","point":{"x":116.32234402690887,"y":39.98278799397245},"tag":"医疗;综合医院","tel":"","uid":"fa01e9371a040053774ff1ca","zip":""},{"addr":"中关村大街19号新中关大厦(近丹棱街)","cp":" ","direction":"东南","distance":"179","name":"新中关购物中心","poiType":"购物","point":{"x":116.32172419610698,"y":39.984190850302326},"tag":"购物;购物中心","tel":"","uid":"8d96925c0372e65cc1f1cf7f","zip":""},{"addr":"中关村大街与大泥湾路交叉口东北角","cp":" ","direction":"西","distance":"159","name":"必胜客","poiType":"美食","point":{"x":116.32440114652672,"y":39.983230276934485},"tag":"美食;外国餐厅","tel":"","uid":"c85cfc41edd6631cc5effb84","zip":""},{"addr":"北京市海淀区","cp":" ","direction":"东北","distance":"101","name":"北京大学第三医院海淀院区","poiType":"医疗","point":{"x":116.32249673884557,"y":39.98283636881199},"tag":"医疗;综合医院","tel":"","uid":"98ed26eff408a2e79cec2b8c","zip":""},{"addr":"北京市海淀区丹棱街","cp":" ","direction":"东南","distance":"132","name":"新中关大厦c座","poiType":"房地产","point":{"x":116.32237097607417,"y":39.98421158195154},"tag":"房地产;写字楼","tel":"","uid":"bb566acf61f5a07b1b11d59e","zip":""},{"addr":"北京市海淀区中关村大街28号","cp":" ","direction":"西北","distance":"229","name":"海淀剧院","poiType":"休闲娱乐","point":{"x":116.32476046873072,"y":39.98262213711767},"tag":"休闲娱乐;电影院","tel":"","uid":"edd64ce1a6d799913ee231b3","zip":""},{"addr":"中关村大街28-1号海淀文化艺术大厦A座","cp":" ","direction":"西北","distance":"311","name":"北京市海淀区博物馆","poiType":"旅游景点","point":{"x":116.32543419786319,"y":39.98238026181034},"tag":"旅游景点;博物馆","tel":"","uid":"5b25f446a72b99ea112935ad","zip":""}],"poiRegions":[],"sematic_description":"北京远景国际公寓(中关村店)内0米","cityCode":131}
     */

    private int status;
    private String message;
    /**
     * location : {"lng":116.32298699999993,"lat":39.98342407140365}
     * formatted_address : 北京市海淀区中关村大街27号1101-08室
     * business : 中关村,人民大学,苏州街
     * addressComponent : {"adcode":"110108","city":"北京市","country":"中国","direction":"附近","distance":"7","district":"海淀区","province":"北京市","street":"中关村大街","street_number":"27号1101-08室","country_code":0}
     * pois : [{"addr":"北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1","cp":"NavInfo","direction":"内","distance":"0","name":"北京远景国际公寓(中关村店)","poiType":"房地产","point":{"x":116.32294589160055,"y":39.983610361549296},"tag":"房地产","tel":"","uid":"35a08504cb51b1138733049d","zip":""},{"addr":"海淀区中关村北大街27号","cp":" ","direction":"附近","distance":"21","name":"中关村大厦","poiType":"房地产","point":{"x":116.32290995938015,"y":39.98356198726214},"tag":"房地产;写字楼","tel":"","uid":"06d2dffdaef1b7ef88f15d04","zip":""},{"addr":"北京市海淀区中关村大街27号中关村大厦二层","cp":" ","direction":"附近","distance":"5","name":"眉州东坡酒楼","poiType":"美食","point":{"x":116.32293690854546,"y":39.98343759607948},"tag":"美食;中餐厅","tel":"","uid":"2c0bd6c57dbdd3b342ab9a8c","zip":""},{"addr":"北京市海淀区中关村大街29号（海淀黄庄路口）","cp":" ","direction":"东北","distance":"116","name":"海淀医院","poiType":"医疗","point":{"x":116.32234402690887,"y":39.98278799397245},"tag":"医疗;综合医院","tel":"","uid":"fa01e9371a040053774ff1ca","zip":""},{"addr":"中关村大街19号新中关大厦(近丹棱街)","cp":" ","direction":"东南","distance":"179","name":"新中关购物中心","poiType":"购物","point":{"x":116.32172419610698,"y":39.984190850302326},"tag":"购物;购物中心","tel":"","uid":"8d96925c0372e65cc1f1cf7f","zip":""},{"addr":"中关村大街与大泥湾路交叉口东北角","cp":" ","direction":"西","distance":"159","name":"必胜客","poiType":"美食","point":{"x":116.32440114652672,"y":39.983230276934485},"tag":"美食;外国餐厅","tel":"","uid":"c85cfc41edd6631cc5effb84","zip":""},{"addr":"北京市海淀区","cp":" ","direction":"东北","distance":"101","name":"北京大学第三医院海淀院区","poiType":"医疗","point":{"x":116.32249673884557,"y":39.98283636881199},"tag":"医疗;综合医院","tel":"","uid":"98ed26eff408a2e79cec2b8c","zip":""},{"addr":"北京市海淀区丹棱街","cp":" ","direction":"东南","distance":"132","name":"新中关大厦c座","poiType":"房地产","point":{"x":116.32237097607417,"y":39.98421158195154},"tag":"房地产;写字楼","tel":"","uid":"bb566acf61f5a07b1b11d59e","zip":""},{"addr":"北京市海淀区中关村大街28号","cp":" ","direction":"西北","distance":"229","name":"海淀剧院","poiType":"休闲娱乐","point":{"x":116.32476046873072,"y":39.98262213711767},"tag":"休闲娱乐;电影院","tel":"","uid":"edd64ce1a6d799913ee231b3","zip":""},{"addr":"中关村大街28-1号海淀文化艺术大厦A座","cp":" ","direction":"西北","distance":"311","name":"北京市海淀区博物馆","poiType":"旅游景点","point":{"x":116.32543419786319,"y":39.98238026181034},"tag":"旅游景点;博物馆","tel":"","uid":"5b25f446a72b99ea112935ad","zip":""}]
     * poiRegions : []
     * sematic_description : 北京远景国际公寓(中关村店)内0米
     * cityCode : 131
     */

    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * lng : 116.32298699999993
         * lat : 39.98342407140365
         */

        private LocationBean location;
        private String formatted_address;
        private String business;
        /**
         * adcode : 110108
         * city : 北京市
         * country : 中国
         * direction : 附近
         * distance : 7
         * district : 海淀区
         * province : 北京市
         * street : 中关村大街
         * street_number : 27号1101-08室
         * country_code : 0
         */

        private AddressComponentBean addressComponent;
        private String sematic_description;
        private int cityCode;
        /**
         * addr : 北京北京海淀海淀区中关村大街27号（地铁海淀黄庄站A1
         * cp : NavInfo
         * direction : 内
         * distance : 0
         * name : 北京远景国际公寓(中关村店)
         * poiType : 房地产
         * point : {"x":116.32294589160055,"y":39.983610361549296}
         * tag : 房地产
         * tel :
         * uid : 35a08504cb51b1138733049d
         * zip :
         */

        private List<PoisBean> pois;
        private List<?> poiRegions;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public String getSematic_description() {
            return sematic_description;
        }

        public void setSematic_description(String sematic_description) {
            this.sematic_description = sematic_description;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public List<PoisBean> getPois() {
            return pois;
        }

        public void setPois(List<PoisBean> pois) {
            this.pois = pois;
        }

        public List<?> getPoiRegions() {
            return poiRegions;
        }

        public void setPoiRegions(List<?> poiRegions) {
            this.poiRegions = poiRegions;
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

        public static class AddressComponentBean {
            private String adcode;
            private String city;
            private String country;
            private String direction;
            private String distance;
            private String district;
            private String province;
            private String street;
            private String street_number;
            private int country_code;

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }

            public int getCountry_code() {
                return country_code;
            }

            public void setCountry_code(int country_code) {
                this.country_code = country_code;
            }
        }

        public static class PoisBean {
            private String addr;
            private String cp;
            private String direction;
            private String distance;
            private String name;
            private String poiType;
            /**
             * x : 116.32294589160055
             * y : 39.983610361549296
             */

            private PointBean point;
            private String tag;
            private String tel;
            private String uid;
            private String zip;

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getCp() {
                return cp;
            }

            public void setCp(String cp) {
                this.cp = cp;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPoiType() {
                return poiType;
            }

            public void setPoiType(String poiType) {
                this.poiType = poiType;
            }

            public PointBean getPoint() {
                return point;
            }

            public void setPoint(PointBean point) {
                this.point = point;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getZip() {
                return zip;
            }

            public void setZip(String zip) {
                this.zip = zip;
            }

            public static class PointBean {
                private double x;
                private double y;

                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }
            }
        }
    }
}
