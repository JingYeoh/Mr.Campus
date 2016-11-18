package com.jkb.api.entity.tools;

/**
 * 工具——四六级查询的实体类
 * Created by JustKiddingBaby on 2016/11/16.
 */

public class ToolsCETEntity {


    /**
     * name : 张三
     * school : 金陵科技学院
     * type : 英语四级
     * num : 123456789123456
     * time : 2016年06月
     * total : 355
     * listen : 136
     * read : 114
     * write : 105
     */

    private CetBean cet;

    public CetBean getCet() {
        return cet;
    }

    public void setCet(CetBean cet) {
        this.cet = cet;
    }

    public static class CetBean {
        private String name;
        private String school;
        private String type;
        private String num;
        private String time;
        private int total;
        private int listen;
        private int read;
        private int write;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getListen() {
            return listen;
        }

        public void setListen(int listen) {
            this.listen = listen;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public int getWrite() {
            return write;
        }

        public void setWrite(int write) {
            this.write = write;
        }
    }
}
