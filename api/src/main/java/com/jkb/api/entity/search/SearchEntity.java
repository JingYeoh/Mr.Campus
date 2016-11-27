package com.jkb.api.entity.search;

import java.util.List;

/**
 * 搜索的数据实体类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchEntity {

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String next_page_url;
    private String prev_page_url;
    private int from;
    private int to;
    /**
     * userCount : 2
     * articleCount : 0
     * normalCount : 0
     * topicCount : 0
     * subjectCount : 0
     * circleCount : 0
     */

    private CountBean count;

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

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class CountBean {
        private int userCount;
        private int subjectCount;
        private int circleCount;
        /**
         * dynamicCount : 400
         * allCount : 1900
         */

        private int dynamicCount;
        private int allCount;

        public int getUserCount() {
            return userCount;
        }

        public void setUserCount(int userCount) {
            this.userCount = userCount;
        }


        public int getSubjectCount() {
            return subjectCount;
        }

        public void setSubjectCount(int subjectCount) {
            this.subjectCount = subjectCount;
        }

        public int getCircleCount() {
            return circleCount;
        }

        public void setCircleCount(int circleCount) {
            this.circleCount = circleCount;
        }

        public int getDynamicCount() {
            return dynamicCount;
        }

        public void setDynamicCount(int dynamicCount) {
            this.dynamicCount = dynamicCount;
        }

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }
    }

    public static class DataBean {
        private String search_type;
        /**
         * id : 2
         * UID : 2769801
         * nickname : 闹气孩子
         * avatar : http://image.lyfsmile.cn/avatar/image6202_1479393210.jpg
         * count_of_fan : 38
         * has_attention : 0
         */

        private UserBean user;
        /**
         * id : 302
         * name : 西北兰州牛肉拉面
         * type : 美食
         * picture : http://image.lyfsmile.cn/circle/image696_1475831212.jpg
         * introduction : 兰州牛肉面馆。美
         * count_of_subscription : 0
         */

        private CircleBean circle;
        /**
         * id : 12558
         * dtype : complaint
         * title : 我要吐槽傻逼老师。
         * content : {"doc":"王池社这个傻逼。不听我说话。","img":[""]}
         * tag : 1
         * count_of_favorite : 0
         */

        private SubjectBean subject;
        /**
         * id : 12543
         * dtype : topic
         * title : 你觉得乌龟能不能当传家宝？
         * content : {"topic":{"doc":"你觉得乌龟能不能当传家宝？","img":"http://image.lyfsmile.cn/dynamic/image5330_1479458179.jpg"}}
         * tag : 趣事
         * count_of_favorite : 2
         */

        private DynamicBean dynamic;
        /**
         * id : 540
         * dtype : topic
         * title : 地震前做什么事最尴尬？
         * content : {"topic":{"doc":"我认为是?洗澡","img":"http://image.lyfsmile.cn/dynamic/image6346_1478416572.jpg"}}
         * tag : 趣事
         * circle_id : 182
         * count_of_favorite : 1
         * circle : {"id":182,"name":"Dr. Stephan Zieme MD","type":"日志","count_of_subscription":34,"count_of_dynamic":35,"created_at":"2016-08-03 16:24:13"}
         */

        private DynamicInCircleBean dynamicInCircle;

        public String getSearch_type() {
            return search_type;
        }

        public void setSearch_type(String search_type) {
            this.search_type = search_type;
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

        public SubjectBean getSubject() {
            return subject;
        }

        public void setSubject(SubjectBean subject) {
            this.subject = subject;
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

        public static class UserBean {
            private int id;
            private String UID;
            private String nickname;
            private String avatar;
            private int count_of_fan;
            private int has_attention;

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

            public int getCount_of_fan() {
                return count_of_fan;
            }

            public void setCount_of_fan(int count_of_fan) {
                this.count_of_fan = count_of_fan;
            }

            public int getHas_attention() {
                return has_attention;
            }

            public void setHas_attention(int has_attention) {
                this.has_attention = has_attention;
            }
        }

        public static class CircleBean {
            private int id;
            private String name;
            private String type;
            private String picture;
            private String introduction;
            private int count_of_subscription;

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

            public int getCount_of_subscription() {
                return count_of_subscription;
            }

            public void setCount_of_subscription(int count_of_subscription) {
                this.count_of_subscription = count_of_subscription;
            }
        }

        public static class SubjectBean {
            private int id;
            private String dtype;
            private String title;
            /**
             * doc : 王池社这个傻逼。不听我说话。
             * img : [""]
             */

            private ContentBean content;
            private int tag;
            private int count_of_favorite;

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

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            public int getCount_of_favorite() {
                return count_of_favorite;
            }

            public void setCount_of_favorite(int count_of_favorite) {
                this.count_of_favorite = count_of_favorite;
            }

            public static class ContentBean {
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

        public static class DynamicBean {
            private int id;
            private String dtype;
            private String title;
            /**
             * topic : {"doc":"你觉得乌龟能不能当传家宝？","
             */

            private ContentBean content;
            private String tag;
            private int count_of_favorite;
            /**
             * count_of_participation : 0
             */

            private int count_of_participation;

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
        }

        public static class DynamicInCircleBean {
            private int id;
            private String dtype;
            private String title;
            /**
             * topic : {"doc":"我认为是?洗澡","img":"http://image.lyfsmile.cn/dynamic/image6346_1478416572.jpg"}
             */

            private ContentBean content;
            private String tag;
            private int circle_id;
            private int count_of_favorite;
            /**
             * id : 182
             * name : Dr. Stephan Zieme MD
             * type : 日志
             * count_of_subscription : 34
             * count_of_dynamic : 35
             * created_at : 2016-08-03 16:24:13
             */

            private CircleBean circle;
            /**
             * count_of_participation : 0
             */

            private int count_of_participation;

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

            public int getCircle_id() {
                return circle_id;
            }

            public void setCircle_id(int circle_id) {
                this.circle_id = circle_id;
            }

            public int getCount_of_favorite() {
                return count_of_favorite;
            }

            public void setCount_of_favorite(int count_of_favorite) {
                this.count_of_favorite = count_of_favorite;
            }

            public CircleBean getCircle() {
                return circle;
            }

            public void setCircle(CircleBean circle) {
                this.circle = circle;
            }

            public int getCount_of_participation() {
                return count_of_participation;
            }

            public void setCount_of_participation(int count_of_participation) {
                this.count_of_participation = count_of_participation;
            }

            public static class CircleBean {
                private int id;
                private String name;
                private String type;
                private String picture;
                private int count_of_subscription;
                private int count_of_dynamic;
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

                public int getCount_of_subscription() {
                    return count_of_subscription;
                }

                public void setCount_of_subscription(int count_of_subscription) {
                    this.count_of_subscription = count_of_subscription;
                }

                public int getCount_of_dynamic() {
                    return count_of_dynamic;
                }

                public void setCount_of_dynamic(int count_of_dynamic) {
                    this.count_of_dynamic = count_of_dynamic;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }
            }
        }

        public static class ContentBean {
            /**
             * doc : 你觉得乌龟能不能当传家宝？
             * img : http://image.lyfsmile.cn/dynamic/image5330_1479458179.jpg
             */

            private TopicBean topic;
            /**
             * doc : Harum et enim quia et voluptatibus sint qui.
             * img : http://lorempixel.com/640/480/?96398
             */

            private List<ArticleBean> article;
            /**
             * doc : Odio quos minima dicta. Et unde voluptatem perferendis rerum.
             * img : ["http://lorempixel.com/640/480/?77544",
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
    }
}
