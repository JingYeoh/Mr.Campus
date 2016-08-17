package com.jkb.core.contract.usersList;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 访客列表的页面协议类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface VisitorContract {

    interface View extends BaseView<AttentionContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
