package com.jkb.core.presenter.school;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.school.SchoolListEntity;
import com.jkb.core.contract.school.SelectSchoolContract;
import com.jkb.core.contract.school.data.SchoolData;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.dataSource.school.SelectSchoolDataRepertory;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 选择学校的P层
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SelectSchoolPresenter implements SelectSchoolContract.Presenter {

    //data
    private SelectSchoolContract.View view;
    private SelectSchoolDataRepertory repertory;

    //data
    private List<SchoolData> schoolDatas;
    private boolean isCached = false;

    public SelectSchoolPresenter(SelectSchoolContract.View view,
                                 SelectSchoolDataRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        schoolDatas = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void initSchoolData() {
        if (isCached) {
            bindDataToView();
        } else {
            reqAllSchoolData();
        }
    }


    @Override
    public void bindDataToView() {
        isCached = true;
        if (!view.isActive()) {
            return;
        }
        view.setSchoolData(schoolDatas);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onSchoolSelected(int position) {
        //点击选择的学校的时候
        SchoolData schoolData = schoolDatas.get(position);
        //保存选择的学校状态
        Schools schools = new Schools();
        schools.setSchool_id(schoolData.getSchool_id());
        schools.setSchool_name(schoolData.getSchoolName());
        schools.setBadge(schoolData.getSchoolBadge());
        schools.setSummary(schoolData.getSummary());
        schools.setLatitude(schoolData.getLatitude());
        schools.setLongitude(schoolData.getLongitude());
        schools.setUpdated_at(StringUtils.getSystemCurrentTime());

        repertory.saveSchoolToDb(schools);//保存到数据库中

        SchoolInfoSingleton.getInstance().setSchool(schools);
        SchoolInfoSingleton.getInstance().setSelectedSchool(true);

        //保存到状态表中
        Status status = new Status();
        if (LoginContext.getInstance().isLogined()) {
            UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
            Users users = userInfo.getUsers();
            status.setUser_id(users.getUser_id());
            status.setFlag_login(true);
        } else {
            status.setUser_id(0);
            status.setFlag_login(false);
        }
        status.setVersion(repertory.getCurrentVersion());
        status.setFlag_login(true);
        boolean selectedSchool = SchoolInfoSingleton.getInstance().isSelectedSchool();
        if (!selectedSchool) {
            status.setFlag_select_school(false);
            status.setSchool_id(0);
        } else {
            status.setFlag_select_school(true);
            status.setSchool_id(SchoolInfoSingleton.getInstance().getSchool().getSchool_id());
        }
        status.setCreated_at(StringUtils.getSystemCurrentTime());

        repertory.saveStatusToDb(status);//保存到用户状态表中
    }

    @Override
    public void start() {
        initSchoolData();
    }


    /**
     * 请求所有学校数据
     */
    private void reqAllSchoolData() {
        repertory.getAllSchool(1, schoolListApiCallback);
    }

    /**
     * 请求所有学校的数据回调接口
     */
    private ApiCallback<ApiResponse<SchoolListEntity>> schoolListApiCallback = new
            ApiCallback<ApiResponse<SchoolListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<SchoolListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<SchoolListEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        return;
                    }
                    handleSchoolListData(body.getMsg());
                }

                /**
                 * 处理学校数据
                 */
                private void handleSchoolListData(SchoolListEntity msg) {
                    if (msg == null) {
                        bindDataToView();
                        return;
                    }
                    SchoolListEntity.SchoolBean school = msg.getSchool();
                    if (school == null) {
                        bindDataToView();
                        return;
                    }
                    List<SchoolListEntity.SchoolBean.DataBean> data = school.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    schoolDatas = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        SchoolListEntity.SchoolBean.DataBean reqSchool = data.get(i);
                        SchoolData schoolData = new SchoolData();
                        schoolData.setSchool_id(reqSchool.getId());
                        schoolData.setSchoolName(reqSchool.getSname());
                        schoolData.setSchoolBadge(reqSchool.getBadge());
                        schoolData.setSummary(reqSchool.getSummary());
                        schoolData.setLatitude(reqSchool.getLatitude());
                        schoolData.setLongitude(reqSchool.getLongitude());

                        schoolDatas.add(schoolData);
                    }

                    bindDataToView();
                }

                @Override
                public void onError(Response<ApiResponse<SchoolListEntity>> response, String error,
                                    ApiResponse<SchoolListEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult("得到学校数据错误");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("得到学校数据失败");
                    }
                }
            };
}
