package com.jkb.api.entity.user;

import java.util.List;

/**
 * 获取用户关注、喜欢等接口返回的数据实体类
 * 有关动态的数据
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class UserActionDynamicEntity {

    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 1
     * data : [{"id":160,"dtype":"article","title":"Esse aut sed amet.","content":"Deserunt ea omnis ea magni...","tag":"eos","is_original":1,"created_at":"2016-08-06 09:29:08","user":{"id":125,"UID":"1907695","nickname":"sophie.nitzsche","avatar":"http://lorempixel.com/640/480/?82484","sex":"男","name":"Madge Moen的菌菌","bref_introduction":"Harum voluptas...","background":"http://lorempixel.com/640/480/?73729","longitude":-125.720474,"latitude":-72.571623,"created_at":"2016-08-03 16:23:41"},"circle":{"id":223,"name":"Dandre O'Keefe","type":"娱乐","picture":"http://lorempixel.com/640/480/?59155","introduction":"Quos sed dolorem ut...","longitude":-74.947835,"latitude":-22.163822,"created_at":"2016-08-03 16:24:13"}}]
     */

    private DynamicBean dynamic;
    /**
     * dynamic : {"total":1,"per_page":10,"current_page":1,"last_page":1,"next_page_url":"","prev_page_url":"","from":1,"to":1,"data":[{"id":160,"dtype":"article","title":"Esse aut sed amet.","content":"Deserunt ea omnis ea magni...","tag":"eos","is_original":1,"created_at":"2016-08-06 09:29:08","user":{"id":125,"UID":"1907695","nickname":"sophie.nitzsche","avatar":"http://lorempixel.com/640/480/?82484","sex":"男","name":"Madge Moen的菌菌","bref_introduction":"Harum voluptas...","background":"http://lorempixel.com/640/480/?73729","longitude":-125.720474,"latitude":-72.571623,"created_at":"2016-08-03 16:23:41"},"circle":{"id":223,"name":"Dandre O'Keefe","type":"娱乐","picture":"http://lorempixel.com/640/480/?59155","introduction":"Quos sed dolorem ut...","longitude":-74.947835,"latitude":-22.163822,"created_at":"2016-08-03 16:24:13"}}]}
     * error : ["action不在取值范围内."]
     */

    private List<String> error;

    public DynamicBean getDynamic() {
        return dynamic;
    }

    public void setDynamic(DynamicBean dynamic) {
        this.dynamic = dynamic;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public static class DynamicBean {
        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private String next_page_url;
        private String prev_page_url;
        private int from;
        private int to;
        /**
         * id : 160
         * dtype : article
         * title : Esse aut sed amet.
         * content : Deserunt ea omnis ea magni...
         * tag : eos
         * is_original : 1
         * created_at : 2016-08-06 09:29:08
         * user : {"id":125,"UID":"1907695","nickname":"sophie.nitzsche","avatar":"http://lorempixel.com/640/480/?82484","sex":"男","name":"Madge Moen的菌菌","bref_introduction":"Harum voluptas...","background":"http://lorempixel.com/640/480/?73729","longitude":-125.720474,"latitude":-72.571623,"created_at":"2016-08-03 16:23:41"}
         * circle : {"id":223,"name":"Dandre O'Keefe","type":"娱乐","picture":"http://lorempixel.com/640/480/?59155","introduction":"Quos sed dolorem ut...","longitude":-74.947835,"latitude":-22.163822,"created_at":"2016-08-03 16:24:13"}
         */

        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private int id;
            private String dtype;
            private String title;
            private String content;
            private String tag;
            private int is_original;
            private String created_at;
            /**
             * id : 125
             * UID : 1907695
             * nickname : sophie.nitzsche
             * avatar : http://lorempixel.com/640/480/?82484
             * sex : 男
             * name : Madge Moen的菌菌
             * bref_introduction : Harum voluptas...
             * background : http://lorempixel.com/640/480/?73729
             * longitude : -125.720474
             * latitude : -72.571623
             * created_at : 2016-08-03 16:23:41
             */

            private UserBean user;
            /**
             * id : 223
             * name : Dandre O'Keefe
             * type : 娱乐
             * picture : http://lorempixel.com/640/480/?59155
             * introduction : Quos sed dolorem ut...
             * longitude : -74.947835
             * latitude : -22.163822
             * created_at : 2016-08-03 16:24:13
             */

            private CircleBean circle;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDtype() {
                return dtype;
            }

            public void setDtype(String dtype) {
                this.dtype = dtype;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getIs_original() {
                return is_original;
            }

            public void setIs_original(int is_original) {
                this.is_original = is_original;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public CircleBean getCircle() {
                return circle;
            }

            public void setCircle(CircleBean circle) {
                this.circle = circle;
            }

            public static class UserBean {
                private int id;
                private String UID;
                private String nickname;
                private String avatar;
                private String sex;
                private String name;
                private String bref_introduction;
                private String background;
                private double longitude;
                private double latitude;
                private String created_at;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getUID() {
                    return UID;
                }

                public void setUID(String UID) {
                    this.UID = UID;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getBref_introduction() {
                    return bref_introduction;
                }

                public void setBref_introduction(String bref_introduction) {
                    this.bref_introduction = bref_introduction;
                }

                public String getBackground() {
                    return background;
                }

                public void setBackground(String background) {
                    this.background = background;
                }

                public double getLongitude() {
                    return longitude;
                }

                public void setLongitude(double longitude) {
                    this.longitude = longitude;
                }

                public double getLatitude() {
                    return latitude;
                }

                public void setLatitude(double latitude) {
                    this.latitude = latitude;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }
            }

            public static class CircleBean {
                private int id;
                private String name;
                private String type;
                private String picture;
                private String introduction;
                private double longitude;
                private double latitude;
                private String created_at;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getPicture() {
                    return picture;
                }

                public void setPicture(String picture) {
                    this.picture = picture;
                }

                public String getIntroduction() {
                    return introduction;
                }

                public void setIntroduction(String introduction) {
                    this.introduction = introduction;
                }

                public double getLongitude() {
                    return longitude;
                }

                public void setLongitude(double longitude) {
                    this.longitude = longitude;
                }

                public double getLatitude() {
                    return latitude;
                }

                public void setLatitude(double latitude) {
                    this.latitude = latitude;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }
            }
        }
    }
}
