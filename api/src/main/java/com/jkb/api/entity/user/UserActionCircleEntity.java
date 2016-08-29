package com.jkb.api.entity.user;

import java.util.List;

/**
 * 获取用户关注、喜欢等接口返回的数据实体类
 * 有关圈子的数据
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class UserActionCircleEntity {


    /**
     * total : 24
     * per_page : 10
     * current_page : 1
     * last_page : 3
     * next_page_url : http://bsapi.lyfsmile.cn/api/v1/getOperation?page=2
     * prev_page_url : null
     * from : 1
     * to : 10
     * data : [{"id":273,"name":"Mr. Marcelino Spencer","type":"交友","picture":"http://lorempixel.com/640/480/?56281","introduction":"Optio voluptas et eaque. Mollitia ipsam repellendus magnam omnis aspernatur et magni voluptas. Accusamus magni autem nesciunt. Facere quaerat error officia iusto.","longitude":116.269979,"latitude":58.137138,"created_at":"2016-08-03 16:24:15","dynamics_count":2,"operation_count":28,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:17:47","updated_at":"2016-08-16 18:17:47"},"user":{"id":216,"UID":"6256950","nickname":"hanna67","avatar":"http://lorempixel.com/640/480/?38325","sex":"男","name":"Moriah Lueilwitz的菌菌","bref_introduction":"Aut veniam explicabo placeat numquam ab dolor nostrum. Ut ex placeat repellat. Distinctio voluptate illum quis tempora voluptates sed.","background":"http://lorempixel.com/640/480/?71975","longitude":160.894556,"latitude":20.571022,"created_at":"2016-08-03 16:23:52"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":279,"name":"Wellington Carroll DDS","type":"交友","picture":"http://lorempixel.com/640/480/?84316","introduction":"Necessitatibus quidem repellat iusto dolor ratione autem ratione reiciendis. Laboriosam vel necessitatibus iste earum ratione consequatur. Molestiae vel dolore in quam.","longitude":38.995106,"latitude":82.795956,"created_at":"2016-08-03 16:24:15","dynamics_count":0,"operation_count":20,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:50:42","updated_at":"2016-08-16 18:50:42"},"user":{"id":87,"UID":"1722884","nickname":"ldenesik","avatar":"http://lorempixel.com/640/480/?40113","sex":"男","name":"Marcellus Ratke的菌菌","bref_introduction":"Consequatur necessitatibus quis qui impedit delectus iste nemo quod. Enim a et aut ipsum. Et ea sint qui placeat quidem.","background":"http://lorempixel.com/640/480/?93734","longitude":-54.230525,"latitude":88.837162,"created_at":"2016-08-03 16:23:37"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":286,"name":"Deonte Goyette V","type":"交友","picture":"http://lorempixel.com/640/480/?24656","introduction":"Eligendi delectus officiis vel at voluptas. Voluptatibus qui qui nemo et. Repellendus facere rerum fuga.","longitude":-15.443078,"latitude":-59.471077,"created_at":"2016-08-03 16:24:15","dynamics_count":2,"operation_count":29,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-20 16:26:08","updated_at":"2016-08-21 15:44:58"},"user":{"id":250,"UID":"6015851","nickname":"eladio.robel","avatar":"http://lorempixel.com/640/480/?32277","sex":"女","name":"Libbie Kub的菌菌","bref_introduction":"Quibusdam eos quibusdam voluptatem. Ut corrupti ipsum maiores molestias ut explicabo. Deleniti corporis accusantium id sunt sed eius. Consequatur modi eum suscipit illo nobis omnis ut maiores.","background":"http://lorempixel.com/640/480/?35971","longitude":95.745512,"latitude":-68.830202,"created_at":"2016-08-03 16:23:56"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":225,"name":"Palma Gerhold","type":"娱乐","picture":"http://lorempixel.com/640/480/?16099","introduction":"Iusto architecto quod molestiae pariatur voluptatum natus ut necessitatibus. Voluptas tenetur qui sint velit qui in sed. Distinctio natus reiciendis corporis adipisci ipsam.","longitude":113.012617,"latitude":-80.238323,"created_at":"2016-08-03 16:24:14","dynamics_count":0,"operation_count":25,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:49:54","updated_at":"2016-08-16 18:49:54"},"user":{"id":215,"UID":"9778987","nickname":"ilebsack","avatar":"http://lorempixel.com/640/480/?94172","sex":"女","name":"Noble Rath的菌菌","bref_introduction":"Quo enim quod saepe illo recusandae. Minus occaecati quasi neque quibusdam libero magnam. Dignissimos unde libero tenetur et.","background":"http://lorempixel.com/640/480/?14403","longitude":51.335564,"latitude":-32.215197,"created_at":"2016-08-03 16:23:52"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":235,"name":"Clay Bahringer","type":"娱乐","picture":"http://lorempixel.com/640/480/?82119","introduction":"In eum id quia. Impedit quam molestiae minima aliquid. Sequi dolore tempore qui et. Assumenda nulla voluptates illum qui.","longitude":90.587166,"latitude":50.140802,"created_at":"2016-08-03 16:24:14","dynamics_count":2,"operation_count":12,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:48:07","updated_at":"2016-08-16 18:48:07"},"user":{"id":170,"UID":"3555788","nickname":"rutherford.elsie","avatar":"http://lorempixel.com/640/480/?29913","sex":"女","name":"Javon Goldner的菌菌","bref_introduction":"Vel corrupti unde repudiandae voluptas molestias molestias. Totam molestias laborum fugiat aut id quis eaque.","background":"http://lorempixel.com/640/480/?90839","longitude":-119.373027,"latitude":-20.707542,"created_at":"2016-08-03 16:23:46"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":244,"name":"Prof. Terry Fisher DDS","type":"娱乐","picture":"http://lorempixel.com/640/480/?92581","introduction":"Sequi eum aliquid vel asperiores vel esse. Voluptatem sit et voluptates laudantium et. Vel sunt quam vel illum id vitae omnis veritatis.","longitude":-26.402634,"latitude":-45.620563,"created_at":"2016-08-03 16:24:14","dynamics_count":1,"operation_count":31,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:31:44","updated_at":"2016-08-16 18:31:44"},"user":{"id":123,"UID":"1286515","nickname":"wkunde","avatar":"http://lorempixel.com/640/480/?95504","sex":"男","name":"Darby Thompson V的菌菌","bref_introduction":"Earum veniam sit veritatis impedit aut. Voluptatem est aut qui distinctio exercitationem quas reprehenderit doloribus. Et quod iusto possimus. Asperiores distinctio quidem tempore sit rem modi.","background":"http://lorempixel.com/640/480/?69861","longitude":173.986898,"latitude":46.151377,"created_at":"2016-08-03 16:23:41"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":253,"name":"Bridie Lockman","type":"交友","picture":"http://lorempixel.com/640/480/?99030","introduction":"Non quo explicabo quaerat maiores qui sed tempora. Impedit temporibus non qui dolor minima. Blanditiis qui consequuntur exercitationem numquam sed voluptates.","longitude":-22.691889,"latitude":-51.234372,"created_at":"2016-08-03 16:24:14","dynamics_count":1,"operation_count":25,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:31:21","updated_at":"2016-08-16 18:31:21"},"user":{"id":257,"UID":"3607080","nickname":"idella.reynolds","avatar":"http://lorempixel.com/640/480/?62907","sex":"女","name":"Beryl Schowalter V的菌菌","bref_introduction":"Cupiditate in autem officiis fugit nobis. Sed qui ut ut libero. Recusandae amet qui vero voluptates beatae aut.","background":"http://lorempixel.com/640/480/?86078","longitude":-72.120057,"latitude":76.730334,"created_at":"2016-08-03 16:23:57"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":192,"name":"Lavern Champlin","type":"日志","picture":"http://lorempixel.com/640/480/?19333","introduction":"Atque voluptas omnis temporibus placeat quo. Quidem ab sit dignissimos iste dignissimos. Ab officia qui quod vel nihil voluptatem quaerat. Voluptatibus accusamus modi eveniet aut dicta explicabo.","longitude":-175.753758,"latitude":-52.546249,"created_at":"2016-08-03 16:24:13","dynamics_count":1,"operation_count":23,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:17:35","updated_at":"2016-08-16 18:17:35"},"user":{"id":251,"UID":"8705454","nickname":"nicholas.sanford","avatar":"http://lorempixel.com/640/480/?65604","sex":"男","name":"Madonna Strosin的菌菌","bref_introduction":"Voluptatibus commodi autem possimus et et molestias. Omnis aut dolorem ipsum sit amet. Suscipit dolorum qui dolore qui inventore ea. Eum assumenda ut nam nam tempora numquam culpa qui.","background":"http://lorempixel.com/640/480/?25581","longitude":34.408543,"latitude":66.293969,"created_at":"2016-08-03 16:23:56"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":199,"name":"Dr. Lawrence Kilback DVM","type":"日志","picture":"http://lorempixel.com/640/480/?83301","introduction":"A a dignissimos reiciendis voluptatem voluptatem. Et ut quis ducimus fugiat magnam reprehenderit. Illo est voluptatem nobis odio distinctio explicabo.","longitude":61.60649,"latitude":-86.654811,"created_at":"2016-08-03 16:24:13","dynamics_count":1,"operation_count":21,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:31:45","updated_at":"2016-08-16 18:31:45"},"user":{"id":221,"UID":"6846139","nickname":"schaden.neal","avatar":"http://lorempixel.com/640/480/?91237","sex":"女","name":"Lessie Reinger的菌菌","bref_introduction":"Veritatis quo placeat provident cumque voluptas placeat. Porro qui odio ut assumenda deserunt quod. Et illo nihil libero nisi aut eius non. Autem rerum et consequatur velit magnam.","background":"http://lorempixel.com/640/480/?96885","longitude":28.322247,"latitude":53.805966,"created_at":"2016-08-03 16:23:52"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":215,"name":"Stacy Zboncak","type":"娱乐","picture":"http://lorempixel.com/640/480/?34568","introduction":"Enim nemo harum sequi hic aut. Ut provident eos laboriosam voluptatem. Molestiae aut deserunt modi non distinctio. Et natus illum ut perspiciatis delectus.","longitude":26.229782,"latitude":-77.648748,"created_at":"2016-08-03 16:24:13","dynamics_count":0,"operation_count":26,"hasSubscribe":1,"subscribe":{"action":"subscribe","created_at":"2016-08-16 18:32:13","updated_at":"2016-08-16 18:32:13"},"user":{"id":120,"UID":"8592404","nickname":"trenton24","avatar":"http://lorempixel.com/640/480/?84192","sex":"女","name":"Ms. Ara Price的菌菌","bref_introduction":"Et ipsa pariatur aperiam neque sed. Dolorum et optio molestiae necessitatibus reprehenderit qui eaque. Temporibus labore ut mollitia necessitatibus facilis dignissimos.","background":"http://lorempixel.com/640/480/?45545","longitude":101.807484,"latitude":64.639587,"created_at":"2016-08-03 16:23:41"},"school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}}]
     * error : ["action不在取值范围内."]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String next_page_url;
    private Object prev_page_url;
    private int from;
    private int to;
    /**
     * id : 273
     * name : Mr. Marcelino Spencer
     * type : 交友
     * picture : http://lorempixel.com/640/480/?56281
     * introduction : Optio voluptas et eaque. Mollitia ipsam repellendus magnam omnis aspernatur et magni voluptas. Accusamus magni autem nesciunt. Facere quaerat error officia iusto.
     * longitude : 116.269979
     * latitude : 58.137138
     * created_at : 2016-08-03 16:24:15
     * dynamics_count : 2
     * operation_count : 28
     * hasSubscribe : 1
     * subscribe : {"action":"subscribe","created_at":"2016-08-16 18:17:47","updated_at":"2016-08-16 18:17:47"}
     * user : {"id":216,"UID":"6256950","nickname":"hanna67","avatar":"http://lorempixel.com/640/480/?38325","sex":"男","name":"Moriah Lueilwitz的菌菌","bref_introduction":"Aut veniam explicabo placeat numquam ab dolor nostrum. Ut ex placeat repellat. Distinctio voluptate illum quis tempora voluptates sed.","background":"http://lorempixel.com/640/480/?71975","longitude":160.894556,"latitude":20.571022,"created_at":"2016-08-03 16:23:52"}
     * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}
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

    public Object getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(Object prev_page_url) {
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
        private String name;
        private String type;
        private String picture;
        private String introduction;
        private double longitude;
        private double latitude;
        private String created_at;
        private int dynamics_count;
        private int operation_count;
        private int hasSubscribe;
        /**
         * action : subscribe
         * created_at : 2016-08-16 18:17:47
         * updated_at : 2016-08-16 18:17:47
         */

        private SubscribeBean subscribe;
        /**
         * id : 216
         * UID : 6256950
         * nickname : hanna67
         * avatar : http://lorempixel.com/640/480/?38325
         * sex : 男
         * name : Moriah Lueilwitz的菌菌
         * bref_introduction : Aut veniam explicabo placeat numquam ab dolor nostrum. Ut ex placeat repellat. Distinctio voluptate illum quis tempora voluptates sed.
         * background : http://lorempixel.com/640/480/?71975
         * longitude : 160.894556
         * latitude : 20.571022
         * created_at : 2016-08-03 16:23:52
         */

        private UserBean user;
        /**
         * id : 1
         * sname : 金陵科技学院
         * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
         * summary : jlkj
         * longitude : 118.905518
         * latitude : 31.912587
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

        public int getHasSubscribe() {
            return hasSubscribe;
        }

        public void setHasSubscribe(int hasSubscribe) {
            this.hasSubscribe = hasSubscribe;
        }

        public SubscribeBean getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(SubscribeBean subscribe) {
            this.subscribe = subscribe;
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

        public static class SubscribeBean {
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

        public static class UserBean {
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
        }

        public static class SchoolBean {
            private int id;
            private String sname;
            private String badge;
            private String summary;
            private String longitude;
            private String latitude;

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

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
        }
    }
}
