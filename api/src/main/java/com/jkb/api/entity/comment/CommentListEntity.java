package com.jkb.api.entity.comment;

import java.util.List;

/**
 * 评论的数据实体类
 * Created by JustKiddingBaby on 2016/9/18.
 */

public class CommentListEntity {

    /**
     * total : 15
     * per_page : 10
     * current_page : 1
     * last_page : 2
     * next_page_url : http://bsapi.lyfsmile.cn/api/v1/comment/175?page=2
     * prev_page_url :
     * from : 1
     * to : 10
     * data : [{"comment_id":9819,"comment_user_id":26,"comment_user_nickname":"camylle22","comment_user_avatar":"http://lorempixel.com/640/480/?83696","comment":"Iure sunt similique expedita quis. Qui ea in optio cumque rerum accusamus.","comment_create_time":"2016-08-17 19:03:39","has_like":0,"count_of_like":87,"reply":[]},{"comment_id":9782,"comment_user_id":98,"comment_user_nickname":"blaise.bogan","comment_user_avatar":"http://lorempixel.com/640/480/?76028","comment":"Maxime est animi odio qui. Unde nemo voluptatem magni qui quisquam reiciendis.","comment_create_time":"2016-08-17 19:03:38","has_like":0,"count_of_like":23,"reply":[]},{"comment_id":8261,"comment_user_id":3,"comment_user_nickname":"veda.shields","comment_user_avatar":"http://lorempixel.com/640/480/?79132","comment":"Veritatis accusamus et nostrum ut amet rerum. Sed rerum sint quia officiis sed dolorem.","comment_create_time":"2016-08-17 19:03:03","has_like":0,"count_of_like":63,"reply":[]},{"comment_id":7961,"comment_user_id":99,"comment_user_nickname":"hane.tiffany","comment_user_avatar":"http://lorempixel.com/640/480/?81024","comment":"Aut aperiam deserunt est et tempore. Laudantium doloribus voluptates eum qui.","comment_create_time":"2016-08-17 19:02:56","has_like":0,"count_of_like":66,"reply":[]},{"comment_id":7649,"comment_user_id":212,"comment_user_nickname":"tokuneva","comment_user_avatar":"http://lorempixel.com/640/480/?64515","comment":"Deserunt dolorem quae debitis ducimus hic. Hic error id consequatur non rem.","comment_create_time":"2016-08-17 19:02:49","has_like":0,"count_of_like":86,"reply":[]},{"comment_id":6785,"comment_user_id":167,"comment_user_nickname":"june33","comment_user_avatar":"http://lorempixel.com/640/480/?93898","comment":"Labore enim autem sint eum. Rerum animi unde mollitia qui.","comment_create_time":"2016-08-17 19:02:30","has_like":0,"count_of_like":10,"reply":[]},{"comment_id":6443,"comment_user_id":249,"comment_user_nickname":"michel.hagenes","comment_user_avatar":"http://lorempixel.com/640/480/?70637","comment":"Hic debitis est aut sint. Qui aspernatur quia neque ad. Maiores eum ex beatae est.","comment_create_time":"2016-08-17 19:02:22","has_like":0,"count_of_like":63,"reply":[]},{"comment_id":5218,"comment_user_id":266,"comment_user_nickname":"nhirthe","comment_user_avatar":"http://lorempixel.com/640/480/?50642","comment":"Non aspernatur omnis voluptatem dolorum. Laborum vel rerum veniam vitae velit dolores odio.","comment_create_time":"2016-08-17 19:01:54","has_like":0,"count_of_like":57,"reply":[]},{"comment_id":4909,"comment_user_id":24,"comment_user_nickname":"wisoky.rubye","comment_user_avatar":"http://lorempixel.com/640/480/?23555","comment":"Autem non sit praesentium nisi est rerum dolor. Culpa non consequatur ut ea nulla.","comment_create_time":"2016-08-17 19:01:47","has_like":0,"count_of_like":93,"reply":[]},{"comment_id":4107,"comment_user_id":166,"comment_user_nickname":"lkozey","comment_user_avatar":"http://lorempixel.com/640/480/?33380","comment":"Atque eveniet voluptatem ut odio. Consequatur sequi voluptas est eum quam labore dicta qui.","comment_create_time":"2016-08-17 19:01:29","has_like":0,"count_of_like":42,"reply":[{"reply_id":10021,"reply_user_id":2,"reply_user_nickname":"assunta.bayer","reply_user_avatar":"http://lorempixel.com/640/480/?73711","target_user_id":238,"target_user_nickname":"uratke","target_user_avatar":"http://lorempixel.com/640/480/?30188","comment":"test reply","reply_create_time":"2016-09-16 08:33:44"},{"reply_id":10020,"reply_user_id":2,"reply_user_nickname":"assunta.bayer","reply_user_avatar":"http://lorempixel.com/640/480/?73711","target_user_id":238,"target_user_nickname":"uratke","target_user_avatar":"http://lorempixel.com/640/480/?30188","comment":"test reply","reply_create_time":"2016-09-16 08:33:41"}]}]
     * error : ["该动态不存在."]
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
     * comment_id : 9819
     * comment_user_id : 26
     * comment_user_nickname : camylle22
     * comment_user_avatar : http://lorempixel.com/640/480/?83696
     * comment : Iure sunt similique expedita quis. Qui ea in optio cumque rerum accusamus.
     * comment_create_time : 2016-08-17 19:03:39
     * has_like : 0
     * count_of_like : 87
     * reply : []
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
        private int comment_id;
        private int comment_user_id;
        private String comment_user_nickname;
        private String comment_user_avatar;
        private String comment;
        private String comment_create_time;
        private int has_like;
        private int count_of_like;
        /**
         * reply_id : 10021
         * reply_user_id : 2
         * reply_user_nickname : assunta.bayer
         * reply_user_avatar : http://lorempixel.com/640/480/?73711
         * target_user_id : 238
         * target_user_nickname : uratke
         * target_user_avatar : http://lorempixel.com/640/480/?30188
         * comment : test reply
         * reply_create_time : 2016-09-16 08:33:44
         */

        private List<ReplyBean> reply;


        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public int getComment_user_id() {
            return comment_user_id;
        }

        public void setComment_user_id(int comment_user_id) {
            this.comment_user_id = comment_user_id;
        }

        public String getComment_user_nickname() {
            return comment_user_nickname;
        }

        public void setComment_user_nickname(String comment_user_nickname) {
            this.comment_user_nickname = comment_user_nickname;
        }

        public String getComment_user_avatar() {
            return comment_user_avatar;
        }

        public void setComment_user_avatar(String comment_user_avatar) {
            this.comment_user_avatar = comment_user_avatar;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment_create_time() {
            return comment_create_time;
        }

        public void setComment_create_time(String comment_create_time) {
            this.comment_create_time = comment_create_time;
        }

        public int getHas_like() {
            return has_like;
        }

        public void setHas_like(int has_like) {
            this.has_like = has_like;
        }

        public int getCount_of_like() {
            return count_of_like;
        }

        public void setCount_of_like(int count_of_like) {
            this.count_of_like = count_of_like;
        }

        public List<ReplyBean> getReply() {
            return reply;
        }

        public void setReply(List<ReplyBean> reply) {
            this.reply = reply;
        }

        public static class ReplyBean {
            private int reply_id;
            private int reply_user_id;
            private String reply_user_nickname;
            private String reply_user_avatar;
            private int target_user_id;
            private String target_user_nickname;
            private String target_user_avatar;
            private String comment;
            private String reply_create_time;

            public int getReply_id() {
                return reply_id;
            }

            public void setReply_id(int reply_id) {
                this.reply_id = reply_id;
            }

            public int getReply_user_id() {
                return reply_user_id;
            }

            public void setReply_user_id(int reply_user_id) {
                this.reply_user_id = reply_user_id;
            }

            public String getReply_user_nickname() {
                return reply_user_nickname;
            }

            public void setReply_user_nickname(String reply_user_nickname) {
                this.reply_user_nickname = reply_user_nickname;
            }

            public String getReply_user_avatar() {
                return reply_user_avatar;
            }

            public void setReply_user_avatar(String reply_user_avatar) {
                this.reply_user_avatar = reply_user_avatar;
            }

            public int getTarget_user_id() {
                return target_user_id;
            }

            public void setTarget_user_id(int target_user_id) {
                this.target_user_id = target_user_id;
            }

            public String getTarget_user_nickname() {
                return target_user_nickname;
            }

            public void setTarget_user_nickname(String target_user_nickname) {
                this.target_user_nickname = target_user_nickname;
            }

            public String getTarget_user_avatar() {
                return target_user_avatar;
            }

            public void setTarget_user_avatar(String target_user_avatar) {
                this.target_user_avatar = target_user_avatar;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getReply_create_time() {
                return reply_create_time;
            }

            public void setReply_create_time(String reply_create_time) {
                this.reply_create_time = reply_create_time;
            }
        }
    }
}
