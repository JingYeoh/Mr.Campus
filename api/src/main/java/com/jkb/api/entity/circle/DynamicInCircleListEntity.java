package com.jkb.api.entity.circle;

import java.util.List;

/**
 * 圈子内动态列表数据
 * Created by JustKiddingBaby on 2016/10/6.
 */

public class DynamicInCircleListEntity {


    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url : “”
     * prev_page_url : “”
     * from : 1
     * to : 1
     * data : [{"dynamic_id":381,"dtype":"article","title":"Voluptatem pariatur nam earum.","content":{"article":[{"doc":"Non vel et quas dolores dicta. Aut quaerat minus odio eligendi quas quis in. Voluptatem animi id quam dolorum. Repudiandae nihil ut magni qui suscipit et est.","img":"http://lorempixel.com/640/480/?19312"},{"doc":"Blanditiis illum consequatur possimus. Et vel corporis at. Aliquam culpa magnam aut eum commodi esse eum. Quas quis earum pariatur dolores omnis sed.","img":"http://lorempixel.com/640/480/?38790"},{"doc":"Inventore sit corporis nulla est consequatur consequuntur deleniti eum. Aut amet velit qui non sunt ducimus. Aperiam perferendis et similique.","img":"http://lorempixel.com/640/480/?79965"}]},"tag":"aut","count_of_comment":17,"count_of_favorite":3,"count_of_participation":0,"created_at":"2016-09-12 16:10:27","has_favorite":0,"author_id":77,"author_nickname":"hallie.jast","author_avatar":"http://lorempixel.com/640/480/?60643"}]
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
     * dynamic_id : 381
     * dtype : article
     * title : Voluptatem pariatur nam earum.
     * content : {"article":[{"doc":"Non vel et quas dolores dicta. Aut quaerat minus odio eligendi quas quis in. Voluptatem animi id quam dolorum. Repudiandae nihil ut magni qui suscipit et est.","img":"http://lorempixel.com/640/480/?19312"},{"doc":"Blanditiis illum consequatur possimus. Et vel corporis at. Aliquam culpa magnam aut eum commodi esse eum. Quas quis earum pariatur dolores omnis sed.","img":"http://lorempixel.com/640/480/?38790"},{"doc":"Inventore sit corporis nulla est consequatur consequuntur deleniti eum. Aut amet velit qui non sunt ducimus. Aperiam perferendis et similique.","img":"http://lorempixel.com/640/480/?79965"}]}
     * tag : aut
     * count_of_comment : 17
     * count_of_favorite : 3
     * count_of_participation : 0
     * created_at : 2016-09-12 16:10:27
     * has_favorite : 0
     * author_id : 77
     * author_nickname : hallie.jast
     * author_avatar : http://lorempixel.com/640/480/?60643
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
        private int dynamic_id;
        private String dtype;
        private String title;
        private ContentBean content;
        private String tag;
        private int count_of_comment;
        private int count_of_favorite;
        private int count_of_participation;
        private String created_at;
        private int has_favorite;
        private int author_id;
        private String author_nickname;
        private String author_avatar;

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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getHas_favorite() {
            return has_favorite;
        }

        public void setHas_favorite(int has_favorite) {
            this.has_favorite = has_favorite;
        }

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }

        public String getAuthor_nickname() {
            return author_nickname;
        }

        public void setAuthor_nickname(String author_nickname) {
            this.author_nickname = author_nickname;
        }

        public String getAuthor_avatar() {
            return author_avatar;
        }

        public void setAuthor_avatar(String author_avatar) {
            this.author_avatar = author_avatar;
        }

        public static class ContentBean {
            /**
             * doc : Non vel et quas dolores dicta. Aut quaerat minus odio eligendi quas quis in. Voluptatem animi id quam dolorum. Repudiandae nihil ut magni qui suscipit et est.
             * img : http://lorempixel.com/640/480/?19312
             */

            private List<ArticleBean> article;
            /**
             * doc : Consequatur repellat provident alias qui eligendi quo excepturi. Et est totam autem quos. Sunt nam eligendi recusandae numquam.
             * img : ["http://lorempixel.com/640/480/?23772","http://lorempixel.com/640/480/?33273","http://lorempixel.com/640/480/?85235"]
             */

            private NormalBean normal;
            /**
             * doc : Exercitationem facilis qui facilis cupiditate aliquid aspernatur. Assumenda ut amet beatae esse commodi. Non dolores magni ut commodi quis.
             * img : http://lorempixel.com/640/480/?73748
             */

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
