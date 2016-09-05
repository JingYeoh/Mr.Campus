package com.jkb.core.contract.function.index.data;

/**
 * 圈子页面需要用到的数据类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicData {

    /**
     * target_type : dynamic
     * action : post
     * title : 发表了一个主题贴
     * dynamic : {"id":344,"dtype":"topic","title":"hahahaahahahahha","content":"213asdddddddddddw14213412asf","tag":"日志","is_original":0,"created_at":"2016-09-02 16:32:21","comments_count":0,"operation_count":0,"hasFavorite":false,"originator":{"originator_id":4,"originator_nickname":"simone40","originator_avatar":"http://lorempixel.com/640/480/?12085"},"circle":{"id":217,"name":"Adam Zboncak","type":"娱乐","picture":"http://lorempixel.com/640/480/?76432","introduction":"Est quia ea sunt quidem ex et ex. Alias adipisci hic cupiditate qui.\nOptio et quia corporis similique. Ducimus esse iusto est et suscipit at. Quo cum et aperiam est error minus.","longitude":-166.49387,"latitude":78.510009,"created_at":"2016-08-03 16:24:13","user":{"id":118,"circle_owner_nickname":"cartwright.trey","circle_owner_avatar":"http://lorempixel.com/640/480/?81509"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}}
     * user : {"id":2,"creator_nickname":"assunta.bayer","creator_avatar":"http://lorempixel.com/640/480/?73711"}
     */

    private String target_type;
    private String action;
    private String title;
    /**
     * id : 344
     * dtype : topic
     * title : hahahaahahahahha
     * content : 213asdddddddddddw14213412asf
     * tag : 日志
     * is_original : 0
     * created_at : 2016-09-02 16:32:21
     * comments_count : 0
     * operation_count : 0
     * hasFavorite : false
     * originator : {"originator_id":4,"originator_nickname":"simone40","originator_avatar":"http://lorempixel.com/640/480/?12085"}
     * circle : {"id":217,"name":"Adam Zboncak","type":"娱乐","picture":"http://lorempixel.com/640/480/?76432","introduction":"Est quia ea sunt quidem ex et ex. Alias adipisci hic cupiditate qui.\nOptio et quia corporis similique. Ducimus esse iusto est et suscipit at. Quo cum et aperiam est error minus.","longitude":-166.49387,"latitude":78.510009,"created_at":"2016-08-03 16:24:13","user":{"id":118,"circle_owner_nickname":"cartwright.trey","circle_owner_avatar":"http://lorempixel.com/640/480/?81509"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}
     */

    private DynamicBean dynamic;
    /**
     * id : 2
     * creator_nickname : assunta.bayer
     * creator_avatar : http://lorempixel.com/640/480/?73711
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

    public DynamicBean getDynamic() {
        return dynamic;
    }

    public void setDynamic(DynamicBean dynamic) {
        this.dynamic = dynamic;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class DynamicBean {
        private int id;
        private String dtype;
        private String title;
        private String content;
        private String tag;
        private int is_original;
        private String created_at;
        private int comments_count;
        private int operation_count;
        private boolean hasFavorite;
        /**
         * originator_id : 4
         * originator_nickname : simone40
         * originator_avatar : http://lorempixel.com/640/480/?12085
         */

        private OriginatorBean originator;
        /**
         * id : 217
         * name : Adam Zboncak
         * type : 娱乐
         * picture : http://lorempixel.com/640/480/?76432
         * introduction : Est quia ea sunt quidem ex et ex. Alias adipisci hic cupiditate qui.
         * Optio et quia corporis similique. Ducimus esse iusto est et suscipit at. Quo cum et aperiam est error minus.
         * longitude : -166.49387
         * latitude : 78.510009
         * created_at : 2016-08-03 16:24:13
         * user : {"id":118,"circle_owner_nickname":"cartwright.trey","circle_owner_avatar":"http://lorempixel.com/640/480/?81509"}
         * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}
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

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public int getOperation_count() {
            return operation_count;
        }

        public void setOperation_count(int operation_count) {
            this.operation_count = operation_count;
        }

        public boolean isHasFavorite() {
            return hasFavorite;
        }

        public void setHasFavorite(boolean hasFavorite) {
            this.hasFavorite = hasFavorite;
        }

        public OriginatorBean getOriginator() {
            return originator;
        }

        public void setOriginator(OriginatorBean originator) {
            this.originator = originator;
        }

        public CircleBean getCircle() {
            return circle;
        }

        public void setCircle(CircleBean circle) {
            this.circle = circle;
        }

        public static class OriginatorBean {
            private int originator_id;
            private String originator_nickname;
            private String originator_avatar;

            public int getOriginator_id() {
                return originator_id;
            }

            public void setOriginator_id(int originator_id) {
                this.originator_id = originator_id;
            }

            public String getOriginator_nickname() {
                return originator_nickname;
            }

            public void setOriginator_nickname(String originator_nickname) {
                this.originator_nickname = originator_nickname;
            }

            public String getOriginator_avatar() {
                return originator_avatar;
            }

            public void setOriginator_avatar(String originator_avatar) {
                this.originator_avatar = originator_avatar;
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
            /**
             * id : 118
             * circle_owner_nickname : cartwright.trey
             * circle_owner_avatar : http://lorempixel.com/640/480/?81509
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
