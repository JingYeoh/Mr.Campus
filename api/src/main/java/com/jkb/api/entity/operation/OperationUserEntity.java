package com.jkb.api.entity.operation;

import java.util.List;

/**
 * 获取涉及操作的用户请求返回的实体
 * Created by JustKiddingBaby on 2016/8/21.
 */

public class OperationUserEntity {


    /**
     * total : 21
     * per_page : 10
     * current_page : 1
     * last_page : 3
     * next_page_url : http://bsapi.lyfsmile.cn/api/v1/operation/getUser?page=2
     * prev_page_url :
     * from : 1
     * to : 10
     * data : [{"id":3,"UID":"6915058","nickname":"justkiddingbaby","avatar":"http://image.lyfsmile.cn/avatar/image6408_1472035909.jpg","sex":"男","name":"justkiddingbaby君","bref_introduction":"Consectetur asperiores recusandae tempora aut unde ut est officia. Est aliquid et cumque minima. Quae temporibus dolores vero provident. Asperiores aperiam neque voluptate aut voluptatem aliquam.","background":"http://image.lyfsmile.cn/background/image8123_1472035931.jpg","longitude":-154.820961,"latitude":52.416631,"created_at":"2016-08-03 16:23:27","hasPayAttention":1,"payAttention":{"action":"payAttention","created_at":"2016-08-23 17:03:05","updated_at":"2016-08-24 09:08:54"}},{"id":284,"UID":"5935279","nickname":"herman.kameron","avatar":"http://lorempixel.com/640/480/?27534","sex":"男","name":"Lorenza Gibson的菌菌","bref_introduction":"Eum dolorum error et et nihil aut sunt. Iusto dolore quidem est et sint perspiciatis iusto soluta. Facere doloremque consequatur sapiente necessitatibus optio ut.","background":"http://lorempixel.com/640/480/?36070","longitude":-57.805494,"latitude":-24.30002,"created_at":"2016-08-03 16:24:00","hasPayAttention":0,"payAttention":""},{"id":290,"UID":"1910828","nickname":"herman.friedrich","avatar":"http://lorempixel.com/640/480/?36811","sex":"女","name":"Mr. Scottie Botsford IV的菌菌","bref_introduction":"Voluptas repudiandae soluta praesentium et voluptas voluptas. Molestiae fugit sapiente molestias ipsum. Et quia ex aut voluptates.","background":"http://lorempixel.com/640/480/?84323","longitude":101.198586,"latitude":75.360876,"created_at":"2016-08-03 16:24:00","hasPayAttention":0,"payAttention":""},{"id":267,"UID":"8689776","nickname":"genoveva.bartell","avatar":"http://lorempixel.com/640/480/?31023","sex":"女","name":"Prof. Claudie Bosco MD的菌菌","bref_introduction":"Minus repellat quia eius quia dolor nisi. Laudantium sapiente enim dolore. Quod totam veniam aut vel sed ad ea. Explicabo dolorum aut doloremque aliquam cupiditate ipsa a.","background":"http://lorempixel.com/640/480/?92453","longitude":-11.962855,"latitude":60.5783,"created_at":"2016-08-03 16:23:58","hasPayAttention":0,"payAttention":""},{"id":257,"UID":"3607080","nickname":"idella.reynolds","avatar":"http://lorempixel.com/640/480/?62907","sex":"女","name":"Beryl Schowalter V的菌菌","bref_introduction":"Cupiditate in autem officiis fugit nobis. Sed qui ut ut libero. Recusandae amet qui vero voluptates beatae aut.","background":"http://lorempixel.com/640/480/?86078","longitude":-72.120057,"latitude":76.730334,"created_at":"2016-08-03 16:23:57","hasPayAttention":0,"payAttention":""},{"id":264,"UID":"5698241","nickname":"koss.vance","avatar":"http://lorempixel.com/640/480/?30649","sex":"男","name":"Ara Parker的菌菌","bref_introduction":"Placeat aliquid est voluptatem. Voluptatum dolorem omnis qui tenetur in adipisci. Doloremque assumenda eligendi necessitatibus nihil ex porro possimus.","background":"http://lorempixel.com/640/480/?57866","longitude":-150.673294,"latitude":-52.823796,"created_at":"2016-08-03 16:23:57","hasPayAttention":0,"payAttention":""},{"id":250,"UID":"6015851","nickname":"eladio.robel","avatar":"http://lorempixel.com/640/480/?32277","sex":"女","name":"Libbie Kub的菌菌","bref_introduction":"Quibusdam eos quibusdam voluptatem. Ut corrupti ipsum maiores molestias ut explicabo. Deleniti corporis accusantium id sunt sed eius. Consequatur modi eum suscipit illo nobis omnis ut maiores.","background":"http://lorempixel.com/640/480/?35971","longitude":95.745512,"latitude":-68.830202,"created_at":"2016-08-03 16:23:56","hasPayAttention":0,"payAttention":""},{"id":251,"UID":"8705454","nickname":"nicholas.sanford","avatar":"http://lorempixel.com/640/480/?65604","sex":"男","name":"Madonna Strosin的菌菌","bref_introduction":"Voluptatibus commodi autem possimus et et molestias. Omnis aut dolorem ipsum sit amet. Suscipit dolorum qui dolore qui inventore ea. Eum assumenda ut nam nam tempora numquam culpa qui.","background":"http://lorempixel.com/640/480/?25581","longitude":34.408543,"latitude":66.293969,"created_at":"2016-08-03 16:23:56","hasPayAttention":0,"payAttention":""},{"id":215,"UID":"9778987","nickname":"ilebsack","avatar":"http://lorempixel.com/640/480/?94172","sex":"女","name":"Noble Rath的菌菌","bref_introduction":"Quo enim quod saepe illo recusandae. Minus occaecati quasi neque quibusdam libero magnam. Dignissimos unde libero tenetur et.","background":"http://lorempixel.com/640/480/?14403","longitude":51.335564,"latitude":-32.215197,"created_at":"2016-08-03 16:23:52","hasPayAttention":0,"payAttention":""},{"id":160,"UID":"9184348","nickname":"lonny.upton","avatar":"http://lorempixel.com/640/480/?49165","sex":"女","name":"Kali Zieme的菌菌","bref_introduction":"Quos nostrum et rerum quis blanditiis. Qui velit dolores ut. Consequatur sed quibusdam libero.","background":"http://lorempixel.com/640/480/?60180","longitude":40.084106,"latitude":77.570465,"created_at":"2016-08-03 16:23:45","hasPayAttention":0,"payAttention":""}]
     * error : ["action不在取值范围内."]
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
     * id : 3
     * UID : 6915058
     * nickname : justkiddingbaby
     * avatar : http://image.lyfsmile.cn/avatar/image6408_1472035909.jpg
     * sex : 男
     * name : justkiddingbaby君
     * bref_introduction : Consectetur asperiores recusandae tempora aut unde ut est officia. Est aliquid et cumque minima. Quae temporibus dolores vero provident. Asperiores aperiam neque voluptate aut voluptatem aliquam.
     * background : http://image.lyfsmile.cn/background/image8123_1472035931.jpg
     * longitude : -154.820961
     * latitude : 52.416631
     * created_at : 2016-08-03 16:23:27
     * hasPayAttention : 1
     * payAttention : {"action":"payAttention","created_at":"2016-08-23 17:03:05","updated_at":"2016-08-24 09:08:54"}
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
        private int id;
        private String UID;
        private String nickname;
        private String avatar;
        private String sex;
        private String name;
        private String bref_introduction;
        private String background;
        private double longitude;
        private double latitude;
        private String created_at;
        private int hasPayAttention;
        /**
         * action : payAttention
         * created_at : 2016-08-23 17:03:05
         * updated_at : 2016-08-24 09:08:54
         */

        private PayAttentionBean payAttention;

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

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
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

        public int getHasPayAttention() {
            return hasPayAttention;
        }

        public void setHasPayAttention(int hasPayAttention) {
            this.hasPayAttention = hasPayAttention;
        }

        public PayAttentionBean getPayAttention() {
            return payAttention;
        }

        public void setPayAttention(PayAttentionBean payAttention) {
            this.payAttention = payAttention;
        }

        public static class PayAttentionBean {
            private String action;
            private String created_at;
            private String updated_at;

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }
    }
}
