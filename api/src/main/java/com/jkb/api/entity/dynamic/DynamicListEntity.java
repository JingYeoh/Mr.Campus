package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 动态列表的数据实体类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicListEntity {


    /**
     * total : 77
     * per_page : 10
     * current_page : 2
     * last_page : 8
     * next_page_url : http://www.campus.org/api/v1/dynamic/getAllDynamic?page=3
     * prev_page_url : http://www.campus.org/api/v1/dynamic/getAllDynamic?page=1
     * from : 11
     * to : 20
     * data : [{"target_type":"circle","action":"subscribe","title":"订阅了一个圈子","target_id":291,"created_at":"2016-09-12 10:31:55","creator_id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg","circle":{"id":291,"name":"Leif Abbott","type":"交友","picture":"http://lorempixel.com/640/480/?81197","introduction":"Mollitia suscipit occaecati qui quia...","longitude":8.237792,"latitude":28.382551,"created_at":"2016-08-03 16:24:15","dynamics_count":2,"operation_count":25,"hasSubscribe":false,"user":{"id":25,"circle_owner_nickname":"logan.von","circle_owner_avatar":"http://lorempixel.com/640/480/?46772"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}},{"target_type":"dynamic","action":"favorite","title":"喜欢了一个动态","target_id":227,"created_at":"2016-09-12 10:31:55","creator_id":136,"creator_nickname":"joy91","creator_avatar":"http://lorempixel.com/640/480/?15460","dynamic":{"id":227,"dtype":"normal","title":"Quod praesentium vitae ad.","content":{"normal":{"doc":"Ut et eaque ratione nihil...","img":[]}},"tag":null,"created_at":"2016-09-07 18:13:09","comments_count":25,"operation_count":19,"hasFavorite":false,"user":{"id":195,"nickname":"maverick.langworth","avatar":"http://lorempixel.com/640/480/?56911"}}},{"target_type":"dynamic","action":"post","title":"发表了一个动态.","target_id":260,"created_at":"2016-09-12 10:31:55","creator_id":121,"creator_nickname":"fmiller","creator_avatar":"http://lorempixel.com/640/480/?57889","dynamic":{"id":260,"dtype":"normal","title":"Corporis doloremque nisi soluta.","content":{"normal":{"doc":"Aut qui est eveniet ea...","img":["http://lorempixel.com/640/480/?78664"]}},"tag":null,"created_at":"2016-09-07 18:13:10","comments_count":50,"operation_count":24,"hasFavorite":false,"user":{"id":121,"nickname":"fmiller","avatar":"http://lorempixel.com/640/480/?57889"}}},{"target_type":"circleInCommonUse","action":"postInCircle","title":"有一个新话题.","target_id":84,"created_at":"2016-09-12 10:31:56","creator_id":183,"creator_nickname":"rogahn.pascale","creator_avatar":"http://lorempixel.com/640/480/?26042","dynamic":{"id":84,"dtype":"topic","title":"Nesciunt corporis error vel.","content":{"topic":{"doc":"Est hic perspiciatis aut...","img":"http://lorempixel.com/640/480/?50885"}},"tag":"omnis","created_at":"2016-09-07 18:13:05","comments_count":35,"operation_count":25,"participation":32,"hasFavorite":false,"circle":{"id":264,"name":"Miss Catharine Glover","type":"交友","picture":"http://lorempixel.com/640/480/?13576","introduction":"Eveniet quia ratione animi...","longitude":141.165887,"latitude":34.147448,"created_at":"2016-08-03 16:24:14","user":{"id":4,"circle_owner_nickname":"simone40","circle_owner_avatar":"http://lorempixel.com/640/480/?12085"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}},"user":{"id":183,"nickname":"rogahn.pascale","avatar":"http://lorempixel.com/640/480/?26042"}}},{"target_type":"circleInCommonUse","action":"postInCircle","title":"有一篇新文章.","target_id":213,"created_at":"2016-09-12 10:32:06","creator_id":70,"creator_nickname":"creola.schultz","creator_avatar":"http://lorempixel.com/640/480/?25139","dynamic":{"id":213,"dtype":"article","title":"Dolorem rem ad qui.","content":{"article":[{"doc":"Rerum beatae dolorum nemo alias...","img":"http://lorempixel.com/640/480/?70699"},{"doc":"Sint quis eius illum dolorem...","img":"http://lorempixel.com/640/480/?76481"},{"doc":"Voluptatem molestiae voluptas...","img":"http://lorempixel.com/640/480/?77799"}]},"tag":"alias","created_at":"2016-09-07 18:13:08","comments_count":29,"operation_count":24,"hasFavorite":false,"circle":{"id":219,"name":"Mrs. Magnolia Zieme MD","type":"娱乐","picture":"http://lorempixel.com/640/480/?51998","introduction":"Culpa excepturi quod recusandae...","longitude":168.716978,"latitude":38.989943,"created_at":"2016-08-03 16:24:13","user":{"id":21,"circle_owner_nickname":"darian24","circle_owner_avatar":"http://lorempixel.com/640/480/?54676"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}},"user":{"id":70,"nickname":"creola.schultz","avatar":"http://lorempixel.com/640/480/?25139"}}}]
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
     * target_id : 291
     * created_at : 2016-09-12 10:31:55
     * creator_id : 1
     * creator_nickname : lyf1
     * creator_avatar : http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg
     * circle : {"id":291,"name":"Leif Abbott","type":"交友","picture":"http://lorempixel.com/640/480/?81197","introduction":"Mollitia suscipit occaecati qui quia...","longitude":8.237792,"latitude":28.382551,"created_at":"2016-08-03 16:24:15","dynamics_count":2,"operation_count":25,"hasSubscribe":false,"user":{"id":25,"circle_owner_nickname":"logan.von","circle_owner_avatar":"http://lorempixel.com/640/480/?46772"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}
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
        private int target_id;
        private String created_at;
        private int creator_id;
        private String creator_nickname;
        private String creator_avatar;


        /**
         * id : 227
         * dtype : normal
         * title : Quod praesentium vitae ad.
         * content : {"normal":{"doc":"Ut et eaque ratione nihil...","img":[]}}
         * tag : null
         * created_at : 2016-09-07 18:13:09
         * comments_count : 25
         * operation_count : 19
         * hasFavorite : false
         * user : {"id":195,"nickname":"maverick.langworth","avatar":"http://lorempixel.com/640/480/?56911"}
         */

        private DynamicBean dynamic;
        /**
         * id : 174
         * name : Enrico Cole
         * type : 日志
         * picture : http://lorempixel.com/640/480/?75034
         * introduction : Adipisci dolore et itaque voluptas at reprehenderit non. Cumque quae saepe et voluptatem dolor sed officia ad.
         * longitude : -85.057115
         * latitude : 15.205842
         * created_at : 2016-08-03 16:24:12
         * dynamics_count : 0
         * operation_count : 30
         * hasSubscribe : false
         * user : {"id":165,"circle_owner_nickname":"oreichert","circle_owner_avatar":"http://lorempixel.com/640/480/?17079"}
         * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}
         */

        private CircleBean circle;


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

        public int getTarget_id() {
            return target_id;
        }

        public void setTarget_id(int target_id) {
            this.target_id = target_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(int creator_id) {
            this.creator_id = creator_id;
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

        public DynamicBean getDynamic() {
            return dynamic;
        }

        public void setDynamic(DynamicBean dynamic) {
            this.dynamic = dynamic;
        }

        public CircleBean getCircle() {
            return circle;
        }

        public void setCircle(CircleBean circle) {
            this.circle = circle;
        }


        public static class DynamicBean {
            private int id;
            private String dtype;
            private String title;
            /**
             * normal : {"doc":"Ut et eaque ratione nihil...","img":[]}
             */

            private ContentBean content;
            private String tag;
            private String created_at;
            private int comments_count;
            private int operation_count;
            private boolean hasFavorite;


            /**
             * id : 195
             * nickname : maverick.langworth
             * avatar : http://lorempixel.com/640/480/?56911
             */

            private UserBean user;
            /**
             * participation : 32
             */

            private int participation;
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

            public boolean isHasFavorite() {
                return hasFavorite;
            }

            public void setHasFavorite(boolean hasFavorite) {
                this.hasFavorite = hasFavorite;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public int getParticipation() {
                return participation;
            }

            public void setParticipation(int participation) {
                this.participation = participation;
            }

            public CircleBean getCircle() {
                return circle;
            }

            public void setCircle(CircleBean circle) {
                this.circle = circle;
            }

            public static class ContentBean {
                /**
                 * doc : Ut et eaque ratione nihil...
                 * img : []
                 */

                private NormalBean normal;
                /**
                 * doc : Est hic perspiciatis aut...
                 * img : http://lorempixel.com/640/480/?50885
                 */

                private TopicBean topic;
                /**
                 * doc : Rerum beatae dolorum nemo alias...
                 * img : http://lorempixel.com/640/480/?70699
                 */

                private List<ArticleBean> article;


                public NormalBean getNormal() {
                    return normal;
                }

                public void setNormal(NormalBean normal) {
                    this.normal = normal;
                }

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
             * id : 165
             * circle_owner_nickname : oreichert
             * circle_owner_avatar : http://lorempixel.com/640/480/?17079
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
    }
}
