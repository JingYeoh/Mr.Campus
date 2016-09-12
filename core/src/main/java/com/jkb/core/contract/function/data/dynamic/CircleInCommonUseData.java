package com.jkb.core.contract.function.data.dynamic;

import java.util.List;

/**
 * 设置为常用圈子的动态数据
 * Created by JustKiddingBaby on 2016/9/12.
 */

public class CircleInCommonUseData extends DynamicBaseData {


    /**
     * title : 有一个新话题.
     * dynamic : {"id":84,"dtype":"topic","title":"Nesciunt corporis error vel.","content":{"topic":{"doc":"Est hic perspiciatis aut...","img":"http://lorempixel.com/640/480/?50885"}},"tag":"omnis","created_at":"2016-09-07 18:13:05","comments_count":35,"operation_count":25,"participation":32,"hasFavorite":false,"circle":{"id":264,"name":"Miss Catharine Glover","type":"交友","picture":"http://lorempixel.com/640/480/?13576","introduction":"Eveniet quia ratione animi...","longitude":141.165887,"latitude":34.147448,"created_at":"2016-08-03 16:24:14","user":{"id":4,"circle_owner_nickname":"simone40","circle_owner_avatar":"http://lorempixel.com/640/480/?12085"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}},"user":{"id":183,"nickname":"rogahn.pascale","avatar":"http://lorempixel.com/640/480/?26042"}}
     */

    private String title;
    /**
     * id : 84
     * dtype : topic
     * title : Nesciunt corporis error vel.
     * content : {"topic":{"doc":"Est hic perspiciatis aut...","img":"http://lorempixel.com/640/480/?50885"}}
     * tag : omnis
     * created_at : 2016-09-07 18:13:05
     * comments_count : 35
     * operation_count : 25
     * participation : 32
     * hasFavorite : false
     * circle : {"id":264,"name":"Miss Catharine Glover","type":"交友","picture":"http://lorempixel.com/640/480/?13576","introduction":"Eveniet quia ratione animi...","longitude":141.165887,"latitude":34.147448,"created_at":"2016-08-03 16:24:14","user":{"id":4,"circle_owner_nickname":"simone40","circle_owner_avatar":"http://lorempixel.com/640/480/?12085"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}
     * user : {"id":183,"nickname":"rogahn.pascale","avatar":"http://lorempixel.com/640/480/?26042"}
     */

    private DynamicBean dynamic;

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

    public static class DynamicBean {
        private int id;
        private String dtype;
        private String title;
        /**
         * topic : {"doc":"Est hic perspiciatis aut...","img":"http://lorempixel.com/640/480/?50885"}
         */

        private ContentBean content;
        private String tag;
        private String created_at;
        private int comments_count;
        private int operation_count;
        private int participation;
        private boolean hasFavorite;
        /**
         * id : 264
         * name : Miss Catharine Glover
         * type : 交友
         * picture : http://lorempixel.com/640/480/?13576
         * introduction : Eveniet quia ratione animi...
         * longitude : 141.165887
         * latitude : 34.147448
         * created_at : 2016-08-03 16:24:14
         * user : {"id":4,"circle_owner_nickname":"simone40","circle_owner_avatar":"http://lorempixel.com/640/480/?12085"}
         * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}
         */

        private CircleBean circle;
        /**
         * id : 183
         * nickname : rogahn.pascale
         * avatar : http://lorempixel.com/640/480/?26042
         */

        private UserBean user;

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

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
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

        public int getParticipation() {
            return participation;
        }

        public void setParticipation(int participation) {
            this.participation = participation;
        }

        public boolean isHasFavorite() {
            return hasFavorite;
        }

        public void setHasFavorite(boolean hasFavorite) {
            this.hasFavorite = hasFavorite;
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

        public static class ContentBean {
            /**
             * doc : Est hic perspiciatis aut...
             * img : http://lorempixel.com/640/480/?50885
             */

            private TopicBean topic;
            /**
             * doc : Rerum beatae dolorum nemo alias est adipisci. Ea alias maxime doloribus soluta. Blanditiis aut eveniet ut numquam est.
             * img : http://lorempixel.com/640/480/?70699
             */

            private List<ArticleBean> article;


            /**
             * doc : Ut et eaque ratione nihil perspiciatis placeat inventore perferendis. Quaerat nesciunt unde sed voluptatibus.
             * img : ["",""]
             */

            private String doc;
            private List<String> img;
            /**
             * doc : Ut et eaque ratione nihil perspiciatis placeat inventore perferendis. Quaerat nesciunt unde sed voluptatibus.
             * img : ["",""]
             */

            private NormalBean normal;

            public TopicBean getTopic() {
                return topic;
            }

            public void setTopic(TopicBean topic) {
                this.topic = topic;
            }

            public List<ArticleBean> getArticle() {
                return article;
            }

            public void setArticle(List<ArticleBean> article) {
                this.article = article;
            }

            public String getDoc() {
                return doc;
            }

            public void setDoc(String doc) {
                this.doc = doc;
            }

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }

            public NormalBean getNormal() {
                return normal;
            }

            public void setNormal(NormalBean normal) {
                this.normal = normal;
            }

            public static class TopicBean {
                private String doc;
                private String img;

                public String getDoc() {
                    return doc;
                }

                public void setDoc(String doc) {
                    this.doc = doc;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public static class ArticleBean {
                private String doc;
                private String img;

                public String getDoc() {
                    return doc;
                }

                public void setDoc(String doc) {
                    this.doc = doc;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public static class NormalBean {
                private String doc;
                private List<String> img;

                public String getDoc() {
                    return doc;
                }

                public void setDoc(String doc) {
                    this.doc = doc;
                }

                public List<String> getImg() {
                    return img;
                }

                public void setImg(List<String> img) {
                    this.img = img;
                }
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
             * id : 4
             * circle_owner_nickname : simone40
             * circle_owner_avatar : http://lorempixel.com/640/480/?12085
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

        public static class UserBean {
            private int id;
            private String nickname;
            private String avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
        }
    }
}
