package com.jkb.api.entity.circle;

import java.util.List;

/**
 * 获取特定圈子信息的数据实体类
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleInfoEntity {

    /**
     * id : 1
     * name : test
     * type : 学生会
     * picture : http://lorempixel.com/640/480/?58413
     * introduction : Veniam iure sequi perferendis. Molestias voluptatem ab molestiae ut vitae neque quia. Temporibus aut rerum porro pariatur autem possimus. Quam quia voluptas velit vel fuga autem voluptatem.
     * longitude : 53.888736
     * latitude : -59.623723
     * created_at : 2016-08-03 16:24:08
     * hasSubscribe : 0
     * hasInCommonUse : 0
     * subscribe_count : 28
     * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}
     * user : {"id":245,"UID":"8958226","nickname":"zstiedemann","avatar":"http://lorempixel.com/640/480/?61017","sex":"女","name":"Abbigail Streich的菌菌","bref_introduction":"Harum beatae adipisci eum commodi consequuntur qui. Corporis tenetur aut quia. Optio iure qui tenetur facilis ut.","background":"http://lorempixel.com/640/480/?32104","longitude":-67.332543,"latitude":18.948517,"created_at":"2016-08-03 16:23:55","email":"efeil@kunde.com","phone":null,"attentionCount":14,"attentionUserCount":22,"fansCount":27,"visitorCount":2,"schoolInfo":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}}
     * dynamic : {"total":2,"per_page":10,"current_page":1,"last_page":1,"next_page_url":"","prev_page_url":"","from":1,"to":2,"data":[{"id":108,"dtype":"normal","title":"Natus vitae deleniti sed.","content":"Totam blanditiis laborum dolores. Nihil laboriosam dolor veniam blanditiis saepe. Aut fuga veritatis fugiat voluptatem sed dolorem ut ea. Quos inventore voluptatem iure quas omnis maxime.","tag":"harum","is_original":1,"created_at":"2016-08-06 09:29:06"},{"id":333,"dtype":"article","title":"hello world","content":"hihihihihihihihihihi","tag":null,"is_original":1,"created_at":"2016-08-10 17:43:19"}]}
     */

    private CircleBean circle;

    public CircleBean getCircle() {
        return circle;
    }

    public void setCircle(CircleBean circle) {
        this.circle = circle;
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
        private int hasSubscribe;
        private int hasInCommonUse;
        private int subscribe_count;
        /**
         * id : 1
         * sname : 金陵科技学院
         * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
         * summary : jlkj
         * longitude : 118.905518
         * latitude : 31.912587
         */

        private SchoolBean school;
        /**
         * id : 245
         * UID : 8958226
         * nickname : zstiedemann
         * avatar : http://lorempixel.com/640/480/?61017
         * sex : 女
         * name : Abbigail Streich的菌菌
         * bref_introduction : Harum beatae adipisci eum commodi consequuntur qui. Corporis tenetur aut quia. Optio iure qui tenetur facilis ut.
         * background : http://lorempixel.com/640/480/?32104
         * longitude : -67.332543
         * latitude : 18.948517
         * created_at : 2016-08-03 16:23:55
         * email : efeil@kunde.com
         * phone : null
         * attentionCount : 14
         * attentionUserCount : 22
         * fansCount : 27
         * visitorCount : 2
         * schoolInfo : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}
         */

        private UserBean user;
        /**
         * total : 2
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * next_page_url :
         * prev_page_url :
         * from : 1
         * to : 2
         * data : [{"id":108,"dtype":"normal","title":"Natus vitae deleniti sed.","content":"Totam blanditiis laborum dolores. Nihil laboriosam dolor veniam blanditiis saepe. Aut fuga veritatis fugiat voluptatem sed dolorem ut ea. Quos inventore voluptatem iure quas omnis maxime.","tag":"harum","is_original":1,"created_at":"2016-08-06 09:29:06"},{"id":333,"dtype":"article","title":"hello world","content":"hihihihihihihihihihi","tag":null,"is_original":1,"created_at":"2016-08-10 17:43:19"}]
         */

        private DynamicBean dynamic;

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

        public int getHasSubscribe() {
            return hasSubscribe;
        }

        public void setHasSubscribe(int hasSubscribe) {
            this.hasSubscribe = hasSubscribe;
        }

        public int getHasInCommonUse() {
            return hasInCommonUse;
        }

        public void setHasInCommonUse(int hasInCommonUse) {
            this.hasInCommonUse = hasInCommonUse;
        }

        public int getSubscribe_count() {
            return subscribe_count;
        }

        public void setSubscribe_count(int subscribe_count) {
            this.subscribe_count = subscribe_count;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public DynamicBean getDynamic() {
            return dynamic;
        }

        public void setDynamic(DynamicBean dynamic) {
            this.dynamic = dynamic;
        }

        public static class SchoolBean {
            private int id;
            private String sname;
            private String badge;
            private String summary;
            private String longitude;
            private String latitude;

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

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
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
            private String email;
            private Object phone;
            private int attentionCount;
            private int attentionUserCount;
            private int fansCount;
            private int visitorCount;
            /**
             * id : 1
             * sname : 金陵科技学院
             * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
             * summary : jlkj
             * longitude : 118.905518
             * latitude : 31.912587
             */

            private SchoolInfoBean schoolInfo;

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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public int getAttentionCount() {
                return attentionCount;
            }

            public void setAttentionCount(int attentionCount) {
                this.attentionCount = attentionCount;
            }

            public int getAttentionUserCount() {
                return attentionUserCount;
            }

            public void setAttentionUserCount(int attentionUserCount) {
                this.attentionUserCount = attentionUserCount;
            }

            public int getFansCount() {
                return fansCount;
            }

            public void setFansCount(int fansCount) {
                this.fansCount = fansCount;
            }

            public int getVisitorCount() {
                return visitorCount;
            }

            public void setVisitorCount(int visitorCount) {
                this.visitorCount = visitorCount;
            }

            public SchoolInfoBean getSchoolInfo() {
                return schoolInfo;
            }

            public void setSchoolInfo(SchoolInfoBean schoolInfo) {
                this.schoolInfo = schoolInfo;
            }

            public static class SchoolInfoBean {
                private int id;
                private String sname;
                private String badge;
                private String summary;
                private String longitude;
                private String latitude;

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

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }
            }
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
             * id : 108
             * dtype : normal
             * title : Natus vitae deleniti sed.
             * content : Totam blanditiis laborum dolores. Nihil laboriosam dolor veniam blanditiis saepe. Aut fuga veritatis fugiat voluptatem sed dolorem ut ea. Quos inventore voluptatem iure quas omnis maxime.
             * tag : harum
             * is_original : 1
             * created_at : 2016-08-06 09:29:06
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
            }
        }
    }
}
