package com.jkb.api.entity.user;

import java.util.List;

/**
 * 获取用户关注、喜欢等接口返回的数据实体类
 * 有关圈子的数据
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class UserActionCircleEntity {


    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 1
     * data : [{"id":160,"name":"Rex Kshlerin DVM","type":"日志","picture":"http://lorempixel.com/640/480/?79945","introduction":"Doloremque et voluptatem minus...","longitude":150.892711,"latitude":31.801155,"created_at":"2016-08-03 16:24:12","user":{"id":72,"UID":"8630257","nickname":"nikita.zboncak","avatar":"http://lorempixel.com/640/480/?69469","sex":"男","name":"William Schimmel的菌菌","bref_introduction":"Ipsa vel qui quia sit amet...","background":"http://lorempixel.com/640/480/?29115","longitude":51.5067,"latitude":-55.979465,"created_at":"2016-08-03 16:23:35"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":118.905518,"latitude":31.912587}}]
     */

    private CircleBean circle;
    /**
     * circle : {"total":1,"per_page":10,"current_page":1,"last_page":1,"next_page_url":"","prev_page_url":"","from":1,"to":1,"data":[{"id":160,"name":"Rex Kshlerin DVM","type":"日志","picture":"http://lorempixel.com/640/480/?79945","introduction":"Doloremque et voluptatem minus...","longitude":150.892711,"latitude":31.801155,"created_at":"2016-08-03 16:24:12","user":{"id":72,"UID":"8630257","nickname":"nikita.zboncak","avatar":"http://lorempixel.com/640/480/?69469","sex":"男","name":"William Schimmel的菌菌","bref_introduction":"Ipsa vel qui quia sit amet...","background":"http://lorempixel.com/640/480/?29115","longitude":51.5067,"latitude":-55.979465,"created_at":"2016-08-03 16:23:35"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":118.905518,"latitude":31.912587}}]}
     * error : ["action不在取值范围内."]
     */

    private List<String> error;

    public CircleBean getCircle() {
        return circle;
    }

    public void setCircle(CircleBean circle) {
        this.circle = circle;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public static class CircleBean {
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
         * name : Rex Kshlerin DVM
         * type : 日志
         * picture : http://lorempixel.com/640/480/?79945
         * introduction : Doloremque et voluptatem minus...
         * longitude : 150.892711
         * latitude : 31.801155
         * created_at : 2016-08-03 16:24:12
         * user : {"id":72,"UID":"8630257","nickname":"nikita.zboncak","avatar":"http://lorempixel.com/640/480/?69469","sex":"男","name":"William Schimmel的菌菌","bref_introduction":"Ipsa vel qui quia sit amet...","background":"http://lorempixel.com/640/480/?29115","longitude":51.5067,"latitude":-55.979465,"created_at":"2016-08-03 16:23:35"}
         * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":118.905518,"latitude":31.912587}
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
            private String name;
            private String type;
            private String picture;
            private String introduction;
            private double longitude;
            private double latitude;
            private String created_at;
            /**
             * id : 72
             * UID : 8630257
             * nickname : nikita.zboncak
             * avatar : http://lorempixel.com/640/480/?69469
             * sex : 男
             * name : William Schimmel的菌菌
             * bref_introduction : Ipsa vel qui quia sit amet...
             * background : http://lorempixel.com/640/480/?29115
             * longitude : 51.5067
             * latitude : -55.979465
             * created_at : 2016-08-03 16:23:35
             */

            private UserBean user;
            /**
             * id : 1
             * sname : 金陵科技学院
             * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
             * summary : jlkj
             * longitude : 118.905518
             * latitude : 31.912587
             */

            private SchoolBean school;

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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public SchoolBean getSchool() {
                return school;
            }

            public void setSchool(SchoolBean school) {
                this.school = school;
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

            public static class SchoolBean {
                private int id;
                private String sname;
                private String badge;
                private String summary;
                private double longitude;
                private double latitude;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }

                public String getBadge() {
                    return badge;
                }

                public void setBadge(String badge) {
                    this.badge = badge;
                }

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
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
            }
        }
    }
}
