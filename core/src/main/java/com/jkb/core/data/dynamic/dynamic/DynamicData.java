package com.jkb.core.data.dynamic.dynamic;

import java.util.List;

/**
 * 动态——动态
 * Created by JustKiddingBaby on 2016/9/8.
 */

public class DynamicData extends DynamicBaseData {

    private int id;//动态id
    private String dtype;//动态类型
    private String title;//标题
    private String tag;
    private String create_time;//创建时间
    private boolean is_orginal;//是否原创
    private int comments_count;//评论数
    private int operation_count;//动态数
    private int participation;//参与数
    private boolean hasFavorite;//是否喜欢
    private Originator originator;//原创作者信息

    private Topic topic;//话题信息
    private Article article;//文章信息
    private Normal normal;//普通信息

    /**
     * 动态原创作者信息（仅当is_original=0时，才会有原创作者信息）.
     */
    public static class Originator {
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

    public static class Normal{
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

    /**
     * 话题包含的内容
     */
    public static class Topic {
        /**
         * doc : Vitae ipsam alias nihil esse fugiat a corporis. Officiis sint assumenda commodi porro qui nostrum placeat quo. Quod distinctio ut sit placeat ipsa.
         * img : http://lorempixel.com/640/480/?50028
         */

        private TopicBean topic;

        public TopicBean getTopic() {
            return topic;
        }

        public void setTopic(TopicBean topic) {
            this.topic = topic;
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

    /**
     * 文章包含的内容
     */
    public static class Article {

        /**
         * doc : Non ipsam ratione molestiae quod provident non voluptates. Non rerum consectetur est eveniet cupiditate quaerat tenetur velit. Exercitationem laudantium est alias excepturi.
         * img : http://lorempixel.com/640/480/?15627
         */

        private List<ArticleBean> article;

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public boolean is_orginal() {
        return is_orginal;
    }

    public void setIs_orginal(boolean is_orginal) {
        this.is_orginal = is_orginal;
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

    public Originator getOriginator() {
        return originator;
    }

    public void setOriginator(Originator originator) {
        this.originator = originator;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }
}
