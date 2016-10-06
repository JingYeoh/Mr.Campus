package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 热门动态列表的数据实体类
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class DynamicPopularListEntity {


    /**
     * total : 80
     * per_page : 10
     * current_page : 1
     * last_page : 8
     * next_page_url : http://bsapi.lyfsmile.cn/api/v1/getPopular/1?page=2
     * prev_page_url :
     * from : 1
     * to : 10
     * data : [{"popular_type":"circle","circle_id":47,"circle_name":"Damion Jones","circle_picture":"http://lorempixel.com/640/480/?58883","circle_introduction":"Voluptatem laboriosam aut doloremque quo. Quia dolorem velit accusantium dolorum temporibus debitis. Cumque non non nostrum et esse quis ut. Nisi vel autem et assumenda.","circle_type":"学生会","circle_created_at":"2016-08-03 16:24:09","has_subscribe":0,"count_of_dynamic":4,"count_of_subscription":39,"circle_longitude":163.54388,"circle_latitude":-75.638815,"creator_id":208,"creator_nickname":"lamar.corkery","creator_avatar":"http://lorempixel.com/640/480/?14669","school_id":1,"school_name":"金陵科技学院","school_badge":"http://image.lyfsmile.cn/school/jlkj.jpg"}]
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
     * popular_type : circle
     * circle_id : 47
     * circle_name : Damion Jones
     * circle_picture : http://lorempixel.com/640/480/?58883
     * circle_introduction : Voluptatem laboriosam aut doloremque quo. Quia dolorem velit accusantium dolorum temporibus debitis. Cumque non non nostrum et esse quis ut. Nisi vel autem et assumenda.
     * circle_type : 学生会
     * circle_created_at : 2016-08-03 16:24:09
     * has_subscribe : 0
     * count_of_dynamic : 4
     * count_of_subscription : 39
     * circle_longitude : 163.54388
     * circle_latitude : -75.638815
     * creator_id : 208
     * creator_nickname : lamar.corkery
     * creator_avatar : http://lorempixel.com/640/480/?14669
     * school_id : 1
     * school_name : 金陵科技学院
     * school_badge : http://image.lyfsmile.cn/school/jlkj.jpg
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

        /**
         * popular_type : circle
         * circle : {"circle_id":66,"circle_name":"Melvina Kohler","circle_picture":"http://lorempixel.com/640/480/?48108","circle_introduction":"Dolorum mollitia in mollitia doloribus. Non dolores porro beatae et assumenda odio.","circle_type":"学生会","circle_created_at":"2016-08-03 16:24:10","has_subscribe":0,"count_of_dynamic":21,"count_of_subscription":38,"circle_longitude":-71.727543,"circle_latitude":76.881574,"creator_id":131,"creator_nickname":"bruen.rebeka","creator_avatar":"http://lorempixel.com/640/480/?14004","school_id":1,"school_name":"金陵科技学院","school_badge":"http://image.lyfsmile.cn/school/jlkj.jpg"}
         */

        private String popular_type;
        /**
         * circle_id : 66
         * circle_name : Melvina Kohler
         * circle_picture : http://lorempixel.com/640/480/?48108
         * circle_introduction : Dolorum mollitia in mollitia doloribus. Non dolores porro beatae et assumenda odio.
         * circle_type : 学生会
         * circle_created_at : 2016-08-03 16:24:10
         * has_subscribe : 0
         * count_of_dynamic : 21
         * count_of_subscription : 38
         * circle_longitude : -71.727543
         * circle_latitude : 76.881574
         * creator_id : 131
         * creator_nickname : bruen.rebeka
         * creator_avatar : http://lorempixel.com/640/480/?14004
         * school_id : 1
         * school_name : 金陵科技学院
         * school_badge : http://image.lyfsmile.cn/school/jlkj.jpg
         */

        private CircleBean circle;
        /**
         * user_id : 240
         * nickname : zspinka
         * avatar : http://lorempixel.com/640/480/?72488
         * sex : 女
         * name : Dino Hartmann的菌菌
         * bref_introduction : Inventore deleniti perspiciatis sed aut atque neque eligendi. Temporibus sit culpa veniam perferendis. Eveniet ea similique qui quis eius ut.
         * count_of_fan : 38
         * count_of_visitor : 25
         * has_attention : 1
         * user_longitude : 68.900513
         * user_latitude : 86.95131
         * school_id : 1
         * school_name : 金陵科技学院
         * school_badge : http://image.lyfsmile.cn/school/jlkj.jpg
         */

        private UserBean user;
        /**
         * dynamic_id : 84
         * dtype : topic
         * title : Laborum sunt enim sint.
         * content : {"topic":{"doc":"Fugit reprehenderit labore quis dolore provident est expedita. Fugit in similique quas excepturi. Necessitatibus cupiditate inventore facilis in est.","img":"http://lorempixel.com/640/480/?34719"}}
         * tag : voluptas
         * count_of_comment : 26
         * count_of_favorite : 29
         * count_of_participation : 39
         * dynamic_created_at : 2016-09-12 16:10:20
         * has_favorite : 0
         * creator_id : 114
         * creator_nickname : rogelio.koelpin
         * creator_avatar : http://lorempixel.com/640/480/?56886
         * circle_id : 86
         * circle_name : Noemi Stoltenberg
         * circle_type : 学生会
         * circle_picture : http://lorempixel.com/640/480/?84170
         * circle_introduction : Quos quae ullam dolorem unde. Tempora culpa cupiditate sint quas omnis libero distinctio sapiente. Natus quia vero et sint culpa ullam. Aut culpa dolorem corporis id in doloribus.
         */

        private DynamicBean dynamic;
        /**
         * dynamic_id : 80
         * dtype : article
         * title : Sint rerum pariatur sit.
         * content : {"article":[{"doc":"Porro tempora placeat illum omnis consequuntur et assumenda. Corrupti ipsam rem est.","img":"http://lorempixel.com/640/480/?41574"},{"doc":"Consequatur animi ut sed ratione quis. Illo eaque sit maxime vitae laboriosam. Et exercitationem quia possimus excepturi hic.","img":"http://lorempixel.com/640/480/?58888"}]}
         * tag : in
         * count_of_comment : 10
         * count_of_favorite : 37
         * dynamic_created_at : 2016-09-12 16:10:19
         * has_favorite : 0
         * creator_id : 118
         * creator_nickname : cartwright.trey
         * creator_avatar : http://lorempixel.com/640/480/?81509
         * circle_id : 158
         * circle_name : Hadley Bruen
         * circle_type : 日志
         * circle_picture : http://lorempixel.com/640/480/?60212
         * circle_introduction : Laboriosam repudiandae qui veniam veniam optio molestiae minima. Eligendi voluptas qui neque impedit. Accusamus consequatur eveniet velit provident ex. Impedit voluptatibus quia totam.
         */

        private DynamicInCircleBean dynamicInCircle;


        public String getPopular_type() {
            return popular_type;
        }

        public void setPopular_type(String popular_type) {
            this.popular_type = popular_type;
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

        public DynamicBean getDynamic() {
            return dynamic;
        }

        public void setDynamic(DynamicBean dynamic) {
            this.dynamic = dynamic;
        }

        public DynamicInCircleBean getDynamicInCircle() {
            return dynamicInCircle;
        }

        public void setDynamicInCircle(DynamicInCircleBean dynamicInCircle) {
            this.dynamicInCircle = dynamicInCircle;
        }

        public static class CircleBean {
            private int circle_id;
            private String circle_name;
            private String circle_picture;
            private String circle_introduction;
            private String circle_type;
            private String circle_created_at;
            private int has_subscribe;
            private int count_of_dynamic;
            private int count_of_subscription;
            private double circle_longitude;
            private double circle_latitude;
            private int creator_id;
            private String creator_nickname;
            private String creator_avatar;
            private int school_id;
            private String school_name;
            private String school_badge;

            public int getCircle_id() {
                return circle_id;
            }

            public void setCircle_id(int circle_id) {
                this.circle_id = circle_id;
            }

            public String getCircle_name() {
                return circle_name;
            }

            public void setCircle_name(String circle_name) {
                this.circle_name = circle_name;
            }

            public String getCircle_picture() {
                return circle_picture;
            }

            public void setCircle_picture(String circle_picture) {
                this.circle_picture = circle_picture;
            }

            public String getCircle_introduction() {
                return circle_introduction;
            }

            public void setCircle_introduction(String circle_introduction) {
                this.circle_introduction = circle_introduction;
            }

            public String getCircle_type() {
                return circle_type;
            }

            public void setCircle_type(String circle_type) {
                this.circle_type = circle_type;
            }

            public String getCircle_created_at() {
                return circle_created_at;
            }

            public void setCircle_created_at(String circle_created_at) {
                this.circle_created_at = circle_created_at;
            }

            public int getHas_subscribe() {
                return has_subscribe;
            }

            public void setHas_subscribe(int has_subscribe) {
                this.has_subscribe = has_subscribe;
            }

            public int getCount_of_dynamic() {
                return count_of_dynamic;
            }

            public void setCount_of_dynamic(int count_of_dynamic) {
                this.count_of_dynamic = count_of_dynamic;
            }

            public int getCount_of_subscription() {
                return count_of_subscription;
            }

            public void setCount_of_subscription(int count_of_subscription) {
                this.count_of_subscription = count_of_subscription;
            }

            public double getCircle_longitude() {
                return circle_longitude;
            }

            public void setCircle_longitude(double circle_longitude) {
                this.circle_longitude = circle_longitude;
            }

            public double getCircle_latitude() {
                return circle_latitude;
            }

            public void setCircle_latitude(double circle_latitude) {
                this.circle_latitude = circle_latitude;
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

            public int getSchool_id() {
                return school_id;
            }

            public void setSchool_id(int school_id) {
                this.school_id = school_id;
            }

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }

            public String getSchool_badge() {
                return school_badge;
            }

            public void setSchool_badge(String school_badge) {
                this.school_badge = school_badge;
            }
        }

        public static class UserBean {
            private int user_id;
            private String nickname;
            private String avatar;
            private String sex;
            private String name;
            private String bref_introduction;
            private int count_of_fan;
            private int count_of_visitor;
            private int has_attention;
            private double user_longitude;
            private double user_latitude;
            private int school_id;
            private String school_name;
            private String school_badge;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

            public int getCount_of_fan() {
                return count_of_fan;
            }

            public void setCount_of_fan(int count_of_fan) {
                this.count_of_fan = count_of_fan;
            }

            public int getCount_of_visitor() {
                return count_of_visitor;
            }

            public void setCount_of_visitor(int count_of_visitor) {
                this.count_of_visitor = count_of_visitor;
            }

            public int getHas_attention() {
                return has_attention;
            }

            public void setHas_attention(int has_attention) {
                this.has_attention = has_attention;
            }

            public double getUser_longitude() {
                return user_longitude;
            }

            public void setUser_longitude(double user_longitude) {
                this.user_longitude = user_longitude;
            }

            public double getUser_latitude() {
                return user_latitude;
            }

            public void setUser_latitude(double user_latitude) {
                this.user_latitude = user_latitude;
            }

            public int getSchool_id() {
                return school_id;
            }

            public void setSchool_id(int school_id) {
                this.school_id = school_id;
            }

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }

            public String getSchool_badge() {
                return school_badge;
            }

            public void setSchool_badge(String school_badge) {
                this.school_badge = school_badge;
            }
        }

        public static class DynamicBean {
            private int dynamic_id;
            private String dtype;
            private String title;
            /**
             * topic : {"doc":"Fugit reprehenderit labore quis dolore provident est expedita. Fugit in similique quas excepturi. Necessitatibus cupiditate inventore facilis in est.","img":"http://lorempixel.com/640/480/?34719"}
             */

            private ContentBean content;
            private String tag;
            private int count_of_comment;
            private int count_of_favorite;
            private int count_of_participation;
            private String dynamic_created_at;
            private int has_favorite;
            private int creator_id;
            private String creator_nickname;
            private String creator_avatar;
            private int circle_id;
            private String circle_name;
            private String circle_type;
            private String circle_picture;
            private String circle_introduction;

            public int getDynamic_id() {
                return dynamic_id;
            }

            public void setDynamic_id(int dynamic_id) {
                this.dynamic_id = dynamic_id;
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

            public int getCount_of_comment() {
                return count_of_comment;
            }

            public void setCount_of_comment(int count_of_comment) {
                this.count_of_comment = count_of_comment;
            }

            public int getCount_of_favorite() {
                return count_of_favorite;
            }

            public void setCount_of_favorite(int count_of_favorite) {
                this.count_of_favorite = count_of_favorite;
            }

            public int getCount_of_participation() {
                return count_of_participation;
            }

            public void setCount_of_participation(int count_of_participation) {
                this.count_of_participation = count_of_participation;
            }

            public String getDynamic_created_at() {
                return dynamic_created_at;
            }

            public void setDynamic_created_at(String dynamic_created_at) {
                this.dynamic_created_at = dynamic_created_at;
            }

            public int getHas_favorite() {
                return has_favorite;
            }

            public void setHas_favorite(int has_favorite) {
                this.has_favorite = has_favorite;
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

            public int getCircle_id() {
                return circle_id;
            }

            public void setCircle_id(int circle_id) {
                this.circle_id = circle_id;
            }

            public String getCircle_name() {
                return circle_name;
            }

            public void setCircle_name(String circle_name) {
                this.circle_name = circle_name;
            }

            public String getCircle_type() {
                return circle_type;
            }

            public void setCircle_type(String circle_type) {
                this.circle_type = circle_type;
            }

            public String getCircle_picture() {
                return circle_picture;
            }

            public void setCircle_picture(String circle_picture) {
                this.circle_picture = circle_picture;
            }

            public String getCircle_introduction() {
                return circle_introduction;
            }

            public void setCircle_introduction(String circle_introduction) {
                this.circle_introduction = circle_introduction;
            }

            public static class ContentBean {
                /**
                 * doc : Fugit reprehenderit labore quis dolore provident est expedita. Fugit in similique quas excepturi. Necessitatibus cupiditate inventore facilis in est.
                 * img : http://lorempixel.com/640/480/?34719
                 */

                private TopicBean topic;
                /**
                 * doc : Quia earum distinctio esse qui dolorem dignissimos. A hic quas reiciendis aut. Qui optio harum amet voluptatum.
                 * img : http://lorempixel.com/640/480/?57887
                 */

                private List<ArticleBean> article;


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
        }

        public static class DynamicInCircleBean {
            private int dynamic_id;
            private String dtype;
            private String title;
            private ContentBean content;
            private String tag;
            private int count_of_comment;
            private int count_of_favorite;
            private int count_of_participation;
            private String dynamic_created_at;
            private int has_favorite;
            private int creator_id;
            private String creator_nickname;
            private String creator_avatar;
            private int circle_id;
            private String circle_name;
            private String circle_type;
            private String circle_picture;
            private String circle_introduction;

            public int getDynamic_id() {
                return dynamic_id;
            }

            public void setDynamic_id(int dynamic_id) {
                this.dynamic_id = dynamic_id;
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

            public int getCount_of_comment() {
                return count_of_comment;
            }

            public void setCount_of_comment(int count_of_comment) {
                this.count_of_comment = count_of_comment;
            }

            public int getCount_of_participation() {
                return count_of_participation;
            }

            public void setCount_of_participation(int count_of_participation) {
                this.count_of_participation = count_of_participation;
            }

            public int getCount_of_favorite() {
                return count_of_favorite;
            }

            public void setCount_of_favorite(int count_of_favorite) {
                this.count_of_favorite = count_of_favorite;
            }

            public String getDynamic_created_at() {
                return dynamic_created_at;
            }

            public void setDynamic_created_at(String dynamic_created_at) {
                this.dynamic_created_at = dynamic_created_at;
            }

            public int getHas_favorite() {
                return has_favorite;
            }

            public void setHas_favorite(int has_favorite) {
                this.has_favorite = has_favorite;
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

            public int getCircle_id() {
                return circle_id;
            }

            public void setCircle_id(int circle_id) {
                this.circle_id = circle_id;
            }

            public String getCircle_name() {
                return circle_name;
            }

            public void setCircle_name(String circle_name) {
                this.circle_name = circle_name;
            }

            public String getCircle_type() {
                return circle_type;
            }

            public void setCircle_type(String circle_type) {
                this.circle_type = circle_type;
            }

            public String getCircle_picture() {
                return circle_picture;
            }

            public void setCircle_picture(String circle_picture) {
                this.circle_picture = circle_picture;
            }

            public String getCircle_introduction() {
                return circle_introduction;
            }

            public void setCircle_introduction(String circle_introduction) {
                this.circle_introduction = circle_introduction;
            }

            public static class ContentBean {
                /**
                 * doc : Porro tempora placeat illum omnis consequuntur et assumenda. Corrupti ipsam rem est.
                 * img : http://lorempixel.com/640/480/?41574
                 */

                private List<ArticleBean> article;
                /**
                 * doc : Officiis laborum enim ratione aut. Dolor rem similique dolorem quidem. Aut nihil occaecati ipsam. Est porro amet velit doloremque eos error.
                 * img : http://lorempixel.com/640/480/?36948
                 */

                private TopicBean topic;


                public List<ArticleBean> getArticle() {
                    return article;
                }

                public void setArticle(List<ArticleBean> article) {
                    this.article = article;
                }

                public TopicBean getTopic() {
                    return topic;
                }

                public void setTopic(TopicBean topic) {
                    this.topic = topic;
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
            }
        }
    }
}
