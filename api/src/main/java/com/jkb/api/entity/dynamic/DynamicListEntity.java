package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 动态列表的数据实体类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicListEntity {


    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String next_page_url;
    private String prev_page_url;
    private int from;
    private int to;

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

        private DynamicBean dynamic;

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

            private ContentBean content;
            private String tag;
            private String created_at;
            private int comments_count;
            private int operation_count;
            private boolean hasFavorite;


            /**
             * id : 195
             * nickname : maverick.langworth
             * avatar : http://lorempixel.com/640/480/?56911
             */

            private UserBean user;
            /**
             * participation : 32
             */

            private int participation;

            private CircleBean circle;
            /**
             * user_id : 3
             */

            private int user_id;
            /**
             * updated_at : 2016-11-02 14:28:56
             */

            private String updated_at;
            /**
             * circle_id : 182
             * count_of_comment : 0
             * count_of_favorite : 1
             * count_of_participation : 0
             */

            private int circle_id;
            private int count_of_comment;
            private int count_of_favorite;
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

            public boolean isHasFavorite() {
                return hasFavorite;
            }

            public void setHasFavorite(boolean hasFavorite) {
                this.hasFavorite = hasFavorite;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public int getParticipation() {
                return participation;
            }

            public void setParticipation(int participation) {
                this.participation = participation;
            }

            public CircleBean getCircle() {
                return circle;
            }

            public void setCircle(CircleBean circle) {
                this.circle = circle;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getCircle_id() {
                return circle_id;
            }

            public void setCircle_id(int circle_id) {
                this.circle_id = circle_id;
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

            public static class ContentBean {
                /**
                 * doc : Ut et eaque ratione nihil...
                 * img : []
                 */

                private NormalBean normal;
                /**
                 * doc : Est hic perspiciatis aut...
                 * img : http://lorempixel.com/640/480/?50885
                 */

                private TopicBean topic;
                /**
                 * doc : Rerum beatae dolorum nemo alias...
                 * img : http://lorempixel.com/640/480/?70699
                 */

                private List<ArticleBean> article;


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

                public List<ArticleBean> getArticle() {
                    return article;
                }

                public void setArticle(List<ArticleBean> article) {
                    this.article = article;
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
                private String nickname;
                private String avatar;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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


                /**
                 * id : 118
                 * circle_owner_nickname : cartwright.trey
                 * circle_owner_avatar : http://lorempixel.com/640/480/?81509
                 */

                private UserBean user;
                /**
                 * id : 1
                 * sname : 金陵科技学院
                 * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
                 */

                private SchoolBean school;
                /**
                 * user_id : 2
                 * school_id : 1
                 * count_of_dynamic : 3
                 * count_of_subscription : 2
                 */

                private int user_id;
                private String school_id;
                private int count_of_dynamic;
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

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getSchool_id() {
                    return school_id;
                }

                public void setSchool_id(String school_id) {
                    this.school_id = school_id;
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
             * id : 165
             * circle_owner_nickname : oreichert
             * circle_owner_avatar : http://lorempixel.com/640/480/?17079
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
