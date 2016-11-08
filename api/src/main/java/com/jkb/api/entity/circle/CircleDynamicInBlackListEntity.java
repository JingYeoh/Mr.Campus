package com.jkb.api.entity.circle;

import java.util.List;

/**
 * 圈子动态黑名单的数据实体类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class CircleDynamicInBlackListEntity {

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
        private int id;
        private int user_id;
        private int circle_id;
        private String dtype;
        private String title;
        private ContentBean content;
        private String tag;
        private int count_of_comment;
        private int count_of_favorite;
        private int count_of_participation;
        private String created_at;
        private String updated_at;

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

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
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

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class ContentBean {
            /**
             * doc : sjsnnejxkmqjks
             * img :
             */

            private List<ArticleBean> article;
            /**
             * doc : Ut et eaque ratione nihil...
             * img : [""]
             */

            private NormalBean normal;
            /**
             * doc : Est hic perspiciatis aut...
             * img : http://lorempixel.com/640/480/?50885
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
            private int count_of_attention;
            private int school_id;
            private double longitude;
            private double latitude;
            private String rc_token;
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

            public int getCount_of_attention() {
                return count_of_attention;
            }

            public void setCount_of_attention(int count_of_attention) {
                this.count_of_attention = count_of_attention;
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

            public String getRc_token() {
                return rc_token;
            }

            public void setRc_token(String rc_token) {
                this.rc_token = rc_token;
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
