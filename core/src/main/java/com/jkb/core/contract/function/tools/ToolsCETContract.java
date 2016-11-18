package com.jkb.core.contract.function.tools;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 四六级成绩查询的页面协议类
 * Created by JustKiddingBaby on 2016/11/16.
 */

public interface ToolsCETContract {

    interface View extends BaseView<Presenter> {

        /**
         * 发送CET相关数据
         */
        void sendCetValue();

        /**
         * 显示错误的页面
         */
        void showErrorView();

        /**
         * 设置成绩单内容
         *
         * @param name   姓名
         * @param school 学校
         * @param type   类型
         * @param num    准考证号
         * @param total  总分
         * @param listen 听力
         * @param read   阅读
         * @param write  写作
         */
        void setCetReportCardValue(String name, String school, String type, String num,
                                   int total, int listen, int read, int write);
    }

    interface Presenter extends BasePresenter {

        /**
         * 得到成绩单
         *
         * @param zkzh 准考证号
         * @param xm   姓名
         */
        void getCetReportCard(String zkzh, String xm);
    }
}
