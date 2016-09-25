package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 特定动态：文章动态
 * Created by JustKiddingBaby on 2016/9/25.
 */

public class DynamicArticleEntity {


    /**
     * id : 148
     * user_id : 208
     * dtype : article
     * title : Commodi nulla facere cupiditate.
     * content : {"article":[{"doc":"Non sequi iste dolore. Aliquam ut sed esse totam. Eius voluptas expedita incidunt quia.\nAutem fuga adipisci vitae sed. Eligendi in cumque fuga. Ducimus hic vel qui et. Omnis provident quia corporis.","img":"http://lorempixel.com/640/480/?95272"},{"doc":"Est ipsum quia quod quaerat perspiciatis aut. Repudiandae fugiat accusantium id soluta culpa perferendis suscipit. Vitae non et suscipit dicta.","img":"http://lorempixel.com/640/480/?97580"}]}
     * tag : ipsum
     * count_of_comment : 24
     * count_of_favorite : 9
     * count_of_participation : 1
     * created_at : 2016-09-12 16:10:21
     * comments_count : 33
     * operation_count : 16
     * hasFavorite : 1
     * user : {"id":208,"UID":"8394976","nickname":"lamar.corkery","avatar":"http://lorempixel.com/640/480/?14669","sex":"男","name":"Nikolas Kertzmann的菌菌","bref_introduction":"Iure facere eum eum repellat tempora sit. Corrupti ut et dolores qui modi asperiores. Similique qui quis pariatur voluptas.","background":"http://lorempixel.com/640/480/?54905","count_of_fan":13,"count_of_visitor":9,"school_id":1,"longitude":-98.305643,"latitude":-76.362278,"created_at":"2016-08-03 16:23:51"}
     */

    private DynamicBean dynamic;

    public DynamicBean getDynamic() {
        return dynamic;
    }

    public void setDynamic(DynamicBean dynamic) {
        this.dynamic = dynamic;
    }

    public static class DynamicBean {
        private int id;
        private int user_id;
        private String dtype;
        private String title;
        private ContentBean content;
        private String tag;
        private int count_of_comment;
        private int count_of_favorite;
        private int count_of_participation;
        private String created_at;
        private int comments_count;
        private int operation_count;
        private int hasFavorite;
        /**
         * id : 208
         * UID : 8394976
         * nickname : lamar.corkery
         * avatar : http://lorempixel.com/640/480/?14669
         * sex : 男
         * name : Nikolas Kertzmann的菌菌
         * bref_introduction : Iure facere eum eum repellat tempora sit. Corrupti ut et dolores qui modi asperiores. Similique qui quis pariatur voluptas.
         * background : http://lorempixel.com/640/480/?54905
         * count_of_fan : 13
         * count_of_visitor : 9
         * school_id : 1
         * longitude : -98.305643
         * latitude : -76.362278
         * created_at : 2016-08-03 16:23:51
         */

        private UserBean user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public int getHasFavorite() {
            return hasFavorite;
        }

        public void setHasFavorite(int hasFavorite) {
            this.hasFavorite = hasFavorite;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class ContentBean {
            /**
             * doc : Non sequi iste dolore. Aliquam ut sed esse totam. Eius voluptas expedita incidunt quia.
             Autem fuga adipisci vitae sed. Eligendi in cumque fuga. Ducimus hic vel qui et. Omnis provident quia corporis.
             * img : http://lorempixel.com/640/480/?95272
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

        public static class UserBean {
            private int id;
            private String UID;
            private String nickname;
            private String avatar;
            private String sex;
            private String name;
            private String bref_introduction;
            private String background;
            private int count_of_fan;
            private int count_of_visitor;
            private int school_id;
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

            public int getSchool_id() {
                return school_id;
            }

            public void setSchool_id(int school_id) {
                this.school_id = school_id;
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
    }
}
