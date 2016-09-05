package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 动态列表的数据实体类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicListEntity {

    /**
     * total : 3
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 3
     * data : [{"target_type":"circle","action":"subscribe","title":"订阅了一个圈子","circle":{"id":121,"name":"Josie Smith MD","type":"日志","picture":"http://lorempixel.com/640/480/?39666","introduction":"Sed illum placeat nesciunt in ...","longitude":133.047382,"latitude":87.551246,"created_at":"2016-08-03 16:24:11","dynamics_count":1,"operation_count":42,"hasSubscribe":false,"user":{"id":240,"circle_owner_nickname":"zspinka","circle_owner_avatar":"http://lorempixel.com/640/480/?72488"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}},"user":{"id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg"}},{"target_type":"dynamic","action":"post","title":"发表了一个主题贴","dynamic":{"id":339,"dtype":"topic","title":"adasdasd","content":"laraaaseae","tag":"数学","is_original":0,"created_at":"2016-08-29 17:06:36","comments_count":0,"operation_count":0,"hasFavorite":false,"originator":{"originator_id":211,"originator_nickname":"amy.goyette","originator_avatar":"http://lorempixel.com/640/480/?27185"},"circle":null},"user":{"id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg"}},{"target_type":"dynamic","action":"favorite","title":"喜欢了一个动态","dynamic":{"id":1,"dtype":"topic","title":"Eos doloribus ut id.","content":"Illum sed sint et quia aliquid ut...","tag":"rem","is_original":1,"created_at":"2016-08-06 09:29:04","comments_count":35,"operation_count":26,"hasFavorite":false,"circle":{"id":121,"name":"Josie Smith MD","type":"日志","picture":"http://lorempixel.com/640/480/?39666","introduction":"Sed illum placeat nesciunt...","longitude":133.047382,"latitude":87.551246,"created_at":"2016-08-03 16:24:11","user":{"id":240,"circle_owner_nickname":"zspinka","circle_owner_avatar":"http://lorempixel.com/640/480/?72488"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}},"user":{"id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg"}}]
     * error : ["该用户不存在."]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String next_page_url;
    private String prev_page_url;
    private int from;
    private int to;
    /**
     * target_type : circle
     * action : subscribe
     * title : 订阅了一个圈子
     * circle : {"id":121,"name":"Josie Smith MD","type":"日志","picture":"http://lorempixel.com/640/480/?39666","introduction":"Sed illum placeat nesciunt in ...","longitude":133.047382,"latitude":87.551246,"created_at":"2016-08-03 16:24:11","dynamics_count":1,"operation_count":42,"hasSubscribe":false,"user":{"id":240,"circle_owner_nickname":"zspinka","circle_owner_avatar":"http://lorempixel.com/640/480/?72488"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}
     * user : {"id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg"}
     */

    private List<DataBean> data;
    private List<String> error;

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

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public static class DataBean {
        private String target_type;
        private String action;
        private String title;
        /**
         * id : 121
         * name : Josie Smith MD
         * type : 日志
         * picture : http://lorempixel.com/640/480/?39666
         * introduction : Sed illum placeat nesciunt in ...
         * longitude : 133.047382
         * latitude : 87.551246
         * created_at : 2016-08-03 16:24:11
         * dynamics_count : 1
         * operation_count : 42
         * hasSubscribe : false
         * user : {"id":240,"circle_owner_nickname":"zspinka","circle_owner_avatar":"http://lorempixel.com/640/480/?72488"}
         * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}
         */

        private CircleBean circle;
        /**
         * id : 1
         * creator_nickname : lyf1
         * creator_avatar : http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg
         */

        private UserBean user;

        public String getTarget_type() {
            return target_type;
        }

        public void setTarget_type(String target_type) {
            this.target_type = target_type;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public CircleBean getCircle() {
            return circle;
        }

        public void setCircle(CircleBean circle) {
            this.circle = circle;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
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
            private int dynamics_count;
            private int operation_count;
            private boolean hasSubscribe;
            /**
             * id : 240
             * circle_owner_nickname : zspinka
             * circle_owner_avatar : http://lorempixel.com/640/480/?72488
             */

            private UserBean user;
            /**
             * id : 1
             * sname : 金陵科技学院
             * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
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

            public int getDynamics_count() {
                return dynamics_count;
            }

            public void setDynamics_count(int dynamics_count) {
                this.dynamics_count = dynamics_count;
            }

            public int getOperation_count() {
                return operation_count;
            }

            public void setOperation_count(int operation_count) {
                this.operation_count = operation_count;
            }

            public boolean isHasSubscribe() {
                return hasSubscribe;
            }

            public void setHasSubscribe(boolean hasSubscribe) {
                this.hasSubscribe = hasSubscribe;
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
                private String circle_owner_nickname;
                private String circle_owner_avatar;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCircle_owner_nickname() {
                    return circle_owner_nickname;
                }

                public void setCircle_owner_nickname(String circle_owner_nickname) {
                    this.circle_owner_nickname = circle_owner_nickname;
                }

                public String getCircle_owner_avatar() {
                    return circle_owner_avatar;
                }

                public void setCircle_owner_avatar(String circle_owner_avatar) {
                    this.circle_owner_avatar = circle_owner_avatar;
                }
            }

            public static class SchoolBean {
                private int id;
                private String sname;
                private String badge;

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
            }
        }

        public static class UserBean {
            private int id;
            private String creator_nickname;
            private String creator_avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreator_nickname() {
                return creator_nickname;
            }

            public void setCreator_nickname(String creator_nickname) {
                this.creator_nickname = creator_nickname;
            }

            public String getCreator_avatar() {
                return creator_avatar;
            }

            public void setCreator_avatar(String creator_avatar) {
                this.creator_avatar = creator_avatar;
            }
        }
    }
}
