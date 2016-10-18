package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 圈子中的动态数据实体类
 * Created by JustKiddingBaby on 2016/10/17.
 */

public class DynamicCircleListEntity {


    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String next_page_url;
    private String prev_page_url;
    private int from;
    private int to;

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
        private int dynamic_id;
        private String dynamic_type;
        private String title;
        private ContentBean content;
        private String tag;
        private int count_of_comment;
        private int count_of_favorite;
        private int count_of_participation;
        private String dynamic_create_time;
        private String dynamic_update_time;
        private int has_favorite;
        private int circle_id;
        private String circle_name;
        private String circle_type;
        private String circle_picture;
        private String circle_introduction;
        private int count_of_dynamic;
        private int count_of_subscription;
        private int circle_school_id;
        private String school_name;


        public int getDynamic_id() {
            return dynamic_id;
        }

        public void setDynamic_id(int dynamic_id) {
            this.dynamic_id = dynamic_id;
        }

        public String getDynamic_type() {
            return dynamic_type;
        }

        public void setDynamic_type(String dynamic_type) {
            this.dynamic_type = dynamic_type;
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

        public String getDynamic_create_time() {
            return dynamic_create_time;
        }

        public void setDynamic_create_time(String dynamic_create_time) {
            this.dynamic_create_time = dynamic_create_time;
        }

        public int getHas_favorite() {
            return has_favorite;
        }

        public void setHas_favorite(int has_favorite) {
            this.has_favorite = has_favorite;
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

        public int getCircle_school_id() {
            return circle_school_id;
        }

        public void setCircle_school_id(int circle_school_id) {
            this.circle_school_id = circle_school_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getDynamic_update_time() {
            return dynamic_update_time;
        }

        public void setDynamic_update_time(String dynamic_update_time) {
            this.dynamic_update_time = dynamic_update_time;
        }

        public static class ContentBean {

            private List<ArticleBean> article;

            private NormalBean normal;

            private TopicBean topic;

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
        }
    }
}
