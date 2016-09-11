package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 动态列表的数据实体类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicListEntity {


    /**
     * total : 4
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 4
     * data : [{"target_type":"dynamic","action":"favorite","title":"喜欢了一个动态","target_id":1,"created_at":"2016-08-29 16:18:07","creator_id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg","dynamic":{"id":1,"dtype":"topic","title":"Nesciunt quam non.","content":{"topic":{"doc":"Vitae ipsam alias nihil esse fugiat a corporis. Officiis sint assumenda commodi porro qui nostrum placeat quo. Quod distinctio ut sit placeat ipsa.","img":"http://lorempixel.com/640/480/?50028"}},"tag":"sunt","is_original":0,"created_at":"2016-09-07 18:13:04","comments_count":37,"operation_count":26,"participation":34,"hasFavorite":false,"originator":{"originator_id":1,"originator_nickname":"lyf1","originator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg"}}},{"target_type":"dynamic","action":"post","title":"发表了一个主题贴","target_id":339,"created_at":"2016-08-29 17:06:36","creator_id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg","dynamic":{"id":339,"dtype":"article","title":"Enim voluptatibus vitae.","content":{"article":[{"doc":"Non ipsam ratione molestiae quod provident non voluptates. Non rerum consectetur est eveniet cupiditate quaerat tenetur velit. Exercitationem laudantium est alias excepturi.","img":"http://lorempixel.com/640/480/?15627"},{"doc":"Omnis itaque pariatur veritatis modi. Mollitia molestiae sed exercitationem facere. Molestiae dolorem quidem tempora veniam non.","img":"http://lorempixel.com/640/480/?38216"},{"doc":"Repellat rem error impedit dolore assumenda in eveniet. Eos est rerum illum praesentium porro reprehenderit omnis consequatur. Rerum nihil est fuga incidunt magni enim suscipit.","img":"http://lorempixel.com/640/480/?84263"},{"doc":"Similique molestiae ut dolor quasi omnis. Delectus est dolorum aliquid quia. Sed ut ad reprehenderit ipsa voluptas. Et corrupti tenetur quia non consectetur itaque et.","img":"http://lorempixel.com/640/480/?77530"}]},"tag":"velit","is_original":0,"created_at":"2016-09-07 18:13:11","comments_count":0,"operation_count":0,"hasFavorite":false,"originator":{"originator_id":87,"originator_nickname":"ldenesik","originator_avatar":"http://lorempixel.com/640/480/?40113"}}},{"target_type":"circle","action":"subscribe","title":"订阅了一个圈子","target_id":121,"created_at":"2016-08-31 08:16:10","creator_id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg","circle":{"id":121,"name":"Josie Smith MD","type":"日志","picture":"http://lorempixel.com/640/480/?39666","introduction":"Sed illum placeat nesciunt in dolorum perspiciatis voluptatem. Qui sed architecto dolor suscipit. Excepturi necessitatibus ad cupiditate ducimus corporis asperiores aut. Fugiat possimus eos id aut.","longitude":133.047382,"latitude":87.551246,"created_at":"2016-08-03 16:24:11","dynamics_count":0,"operation_count":42,"hasSubscribe":false,"user":{"id":240,"circle_owner_nickname":"zspinka","circle_owner_avatar":"http://lorempixel.com/640/480/?72488"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg"}}},{"target_type":"dynamic","action":"post","title":"发表了一篇文章","target_id":351,"created_at":"2016-09-04 17:06:57","creator_id":1,"creator_nickname":"lyf1","creator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg","dynamic":{"id":351,"dtype":"topic","title":"Tempora consequatur delectus et.","content":{"topic":{"doc":"Laudantium est tempora ipsum maiores vel vel. Error ut aut corrupti et aliquam consequuntur debitis eos. Ducimus doloribus sed consequatur illo cumque voluptas consequuntur non.","img":"http://lorempixel.com/640/480/?16851"}},"tag":"eos","is_original":1,"created_at":"2016-09-07 18:13:12","comments_count":0,"operation_count":0,"participation":0,"hasFavorite":false}}]
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
     * target_type : dynamic
     * action : favorite
     * title : 喜欢了一个动态
     * target_id : 1
     * created_at : 2016-08-29 16:18:07
     * creator_id : 1
     * creator_nickname : lyf1
     * creator_avatar : http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg
     * dynamic : {"id":1,"dtype":"topic","title":"Nesciunt quam non.","content":{"topic":{"doc":"Vitae ipsam alias nihil esse fugiat a corporis. Officiis sint assumenda commodi porro qui nostrum placeat quo. Quod distinctio ut sit placeat ipsa.","img":"http://lorempixel.com/640/480/?50028"}},"tag":"sunt","is_original":0,"created_at":"2016-09-07 18:13:04","comments_count":37,"operation_count":26,"participation":34,"hasFavorite":false,"originator":{"originator_id":1,"originator_nickname":"lyf1","originator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg"}}
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
         * id : 1
         * dtype : topic
         * title : Nesciunt quam non.
         * content : {"topic":{"doc":"Vitae ipsam alias nihil esse fugiat a corporis. Officiis sint assumenda commodi porro qui nostrum placeat quo. Quod distinctio ut sit placeat ipsa.","img":"http://lorempixel.com/640/480/?50028"}}
         * tag : sunt
         * is_original : 0
         * created_at : 2016-09-07 18:13:04
         * comments_count : 37
         * operation_count : 26
         * participation : 34
         * hasFavorite : false
         * originator : {"originator_id":1,"originator_nickname":"lyf1","originator_avatar":"http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg"}
         */

        private DynamicBean dynamic;
        /**
         * id : 121
         * name : Josie Smith MD
         * type : 日志
         * picture : http://lorempixel.com/640/480/?39666
         * introduction : Sed illum placeat nesciunt in dolorum perspiciatis voluptatem. Qui sed architecto dolor suscipit. Excepturi necessitatibus ad cupiditate ducimus corporis asperiores aut. Fugiat possimus eos id aut.
         * longitude : 133.047382
         * latitude : 87.551246
         * created_at : 2016-08-03 16:24:11
         * dynamics_count : 0
         * operation_count : 42
         * hasSubscribe : false
         * user : {"id":240,"circle_owner_nickname":"zspinka","circle_owner_avatar":"http://lorempixel.com/640/480/?72488"}
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
             * topic : {"doc":"Vitae ipsam alias nihil esse fugiat a corporis. Officiis sint assumenda commodi porro qui nostrum placeat quo. Quod distinctio ut sit placeat ipsa.","img":"http://lorempixel.com/640/480/?50028"}
             */

            private ContentBean content;
            private String tag;
            private int is_original;
            private String created_at;
            private int comments_count;
            private int operation_count;
            private int participation;
            private boolean hasFavorite;
            /**
             * originator_id : 1
             * originator_nickname : lyf1
             * originator_avatar : http://image.lyfsmile.cn/avatar/image2841_1471333502.jpg
             */

            private OriginatorBean originator;

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

            public OriginatorBean getOriginator() {
                return originator;
            }

            public void setOriginator(OriginatorBean originator) {
                this.originator = originator;
            }

            public static class ContentBean {
                /**
                 * doc : Vitae ipsam alias nihil esse fugiat a corporis. Officiis sint assumenda commodi porro qui nostrum placeat quo. Quod distinctio ut sit placeat ipsa.
                 * img : http://lorempixel.com/640/480/?50028
                 */

                private TopicBean topic;
                /**
                 * doc : Non ipsam ratione molestiae quod provident non voluptates. Non rerum consectetur est eveniet cupiditate quaerat tenetur velit. Exercitationem laudantium est alias excepturi.
                 * img : http://lorempixel.com/640/480/?15627
                 */

                private List<ArticleBean> article;
                /**
                 * doc : Sint omnis earum tempora dolore minus vel. Dolor unde esse quidem est quae cumque quidem. Dolor molestiae animi in reiciendis. Similique sapiente ullam est ad et. Odio enim culpa culpa quia.
                 * img : ["http://lorempixel.com/640/480/?55242"]
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
    }
}
