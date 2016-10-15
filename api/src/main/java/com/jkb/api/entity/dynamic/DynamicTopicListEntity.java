package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 普通动态列表数据
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class DynamicTopicListEntity {

    /**
     * total : 15
     * per_page : 10
     * current_page : 1
     * last_page : 2
     * next_page_url : http://bsapi.lyfsmile.cn/api/v1/personalDynamics/topic?page=2
     * prev_page_url :
     * from : 1
     * to : 10
     * data : [{"dynamic_id":430,"dynamic_type":"topic","title":"Voluptatibus quaerat est.","content":{"topic":{"doc":"Non voluptas in reprehenderit aspernatur reprehenderit temporibus. Debitis nam maiores veritatis qui voluptate et perferendis odit. Magnam eligendi earum inventore odit incidunt.","img":"http://lorempixel.com/640/480/?19455"}},"tag":"cum","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":440,"dynamic_type":"topic","title":"Quidem doloribus occaecati consequuntur.","content":{"topic":{"doc":"Et eum atque quos nihil et. At aut odit optio placeat neque. Dolor et quos unde eum doloribus hic.","img":"http://lorempixel.com/640/480/?58019"}},"tag":"error","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":443,"dynamic_type":"topic","title":"Quis suscipit.","content":{"topic":{"doc":"Error cupiditate et sit rem et aut pariatur. Repudiandae ad dignissimos laudantium itaque. Reprehenderit distinctio eaque facilis quis at voluptas officia.","img":"http://lorempixel.com/640/480/?40519"}},"tag":"earum","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":449,"dynamic_type":"topic","title":"Quibusdam eius sint.","content":{"topic":{"doc":"Earum rerum laborum temporibus sed. Vitae cum nam error distinctio et. Voluptates eveniet amet enim quod explicabo sed. Reiciendis laboriosam praesentium non corporis mollitia eum consequatur error.","img":"http://lorempixel.com/640/480/?94424"}},"tag":"aut","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":461,"dynamic_type":"topic","title":"Non quis incidunt autem.","content":{"topic":{"doc":"Minus molestiae tempora quasi quasi voluptate ut ut modi. Aut praesentium quia possimus. Velit quia voluptatibus omnis dignissimos quia fugit aperiam.","img":"http://lorempixel.com/640/480/?89305"}},"tag":"architecto","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":462,"dynamic_type":"topic","title":"Et non ut voluptatem.","content":{"topic":{"doc":"Veniam ipsum dicta facere sequi id nostrum cupiditate. Doloribus labore quis esse error ad. Deserunt aliquam minus pariatur tenetur omnis est aut enim.","img":"http://lorempixel.com/640/480/?50012"}},"tag":"quis","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":463,"dynamic_type":"topic","title":"Ullam nihil nihil consectetur.","content":{"topic":{"doc":"Eos qui accusantium nihil corporis. Optio dolor facilis eum asperiores fugit officiis necessitatibus consectetur. Rerum enim veritatis aut explicabo. Ut voluptatem dolores fugiat excepturi animi.","img":"http://lorempixel.com/640/480/?88847"}},"tag":"numquam","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":470,"dynamic_type":"topic","title":"Dolore id dignissimos dolores.","content":{"topic":{"doc":"Voluptas fugiat maxime eos earum fugit esse. Molestiae itaque ducimus molestiae deserunt. Ullam omnis sit laboriosam rerum eligendi quod rerum saepe. Illum quidem explicabo nulla.","img":"http://lorempixel.com/640/480/?51020"}},"tag":"quasi","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":477,"dynamic_type":"topic","title":"Numquam iure ipsum architecto.","content":{"topic":{"doc":"Ut delectus voluptatem ea voluptatem et numquam ipsa. Quasi laborum eum deserunt placeat non. Commodi et aperiam architecto distinctio voluptas saepe sit quam.","img":"http://lorempixel.com/640/480/?81650"}},"tag":"magnam","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0},{"dynamic_id":497,"dynamic_type":"topic","title":"Saepe unde placeat.","content":{"topic":{"doc":"Repellat saepe autem provident numquam est. Eos libero harum delectus autem debitis. Sit ab ipsa necessitatibus.","img":"http://lorempixel.com/640/480/?58773"}},"tag":"doloremque","count_of_comment":0,"count_of_favorite":0,"count_of_participation":0,"dynamic_create_time":"2016-10-12 14:20:31","has_favorite":0}]
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
     * dynamic_id : 430
     * dynamic_type : topic
     * title : Voluptatibus quaerat est.
     * content : {"topic":{"doc":"Non voluptas in reprehenderit aspernatur reprehenderit temporibus. Debitis nam maiores veritatis qui voluptate et perferendis odit. Magnam eligendi earum inventore odit incidunt.","img":"http://lorempixel.com/640/480/?19455"}}
     * tag : cum
     * count_of_comment : 0
     * count_of_favorite : 0
     * count_of_participation : 0
     * dynamic_create_time : 2016-10-12 14:20:31
     * has_favorite : 0
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
        private String dynamic_type;
        private String title;
        /**
         * topic : {"doc":"Non voluptas in reprehenderit aspernatur reprehenderit temporibus. Debitis nam maiores veritatis qui voluptate et perferendis odit. Magnam eligendi earum inventore odit incidunt.","img":"http://lorempixel.com/640/480/?19455"}
         */

        private ContentBean content;
        private String tag;
        private int count_of_comment;
        private int count_of_favorite;
        private int count_of_participation;
        private String dynamic_create_time;
        private int has_favorite;

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

        public static class ContentBean {
            /**
             * doc : Non voluptas in reprehenderit aspernatur reprehenderit temporibus. Debitis nam maiores veritatis qui voluptate et perferendis odit. Magnam eligendi earum inventore odit incidunt.
             * img : http://lorempixel.com/640/480/?19455
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
    }
}
