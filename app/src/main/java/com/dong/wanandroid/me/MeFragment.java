package com.dong.wanandroid.me;


import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseFragment;
import com.dong.wanandroid.data.db.DBHelper;
import com.dong.wanandroid.data.event_bus_model.Event;
import com.dong.wanandroid.data.read_record.ReadRecordModel;
import com.dong.wanandroid.data.user.UserModel;
import com.dong.wanandroid.util.LogUtils;
import com.dong.wanandroid.util.tool.AppBarStateChangeListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment implements MeIView {

    private static final String TAG = "MeFragment";
    @BindView(R.id.me_fragment_top_bg_image)
    ImageView mMeFragmentTopBgImage;
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.layout_user_info)
    LinearLayout mLayoutUserInfo;
    @BindView(R.id.my_collect_layout)
    LinearLayout mMyCollectLayout;
    @BindView(R.id.my_read_record_layout)
    LinearLayout mMyReadRecordLayout;
    @BindView(R.id.gank_layout)
    LinearLayout mGankLayout;

    @BindView(R.id.layout_main)
    NestedScrollView mNestedScrollView;
    Unbinder unbinder ;


    private MePresenterComple mMePresenterComple;

    public MeFragment() {}

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        UserModel userModel = (UserModel) event.getData();
        showUserLogin(userModel.getUsername());
    }


    @Override
    public void showUserLogin(String username) {
        mToolbarLayout.setTitle(username);
    }

    @Override
    public void showUserUnLogin() {
        mToolbarLayout.setTitle("你还未登录哟");
    }

    @Override
    public void showImage() {
        // 背景
        Glide.with(this).load("https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=4049333755,3381661484&fm=85&s=84DC30D552237EA21E90C0490300F0F7")
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(mMeFragmentTopBgImage);

        // 头像
        Glide.with(this).load("https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=4049333755,3381661484&fm=85&s=84DC30D552237EA21E90C0490300F0F7")
                .into(mProfileImage);

    }

    @OnClick({R.id.layout_user_info, R.id.my_collect_layout, R.id.my_read_record_layout,R.id.gank_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_user_info:
                mMePresenterComple.judgeToLoginAc(getContext());
                break;
            case R.id.my_collect_layout:
                mMePresenterComple.judgeToCollectAc(getContext());
                break;
            case R.id.my_read_record_layout:
                final BottomSheetDialog sheetDialog = new BottomSheetDialog(getActivity());
                View sheetDialogView = getLayoutInflater().inflate(R.layout.dialog_sheet_record_layout, null);
                sheetDialog.setContentView(sheetDialogView);

                RecyclerView recordRecyclerView = sheetDialogView.findViewById(R.id.record_recycler_view) ;
                List<ReadRecordModel> readRecordFromDb = DBHelper.getReadRecordFromDb();
                LogUtils.eTag("222","size---->"+readRecordFromDb.size());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recordRecyclerView.setLayoutManager(layoutManager);
                RecordAdapter recordAdapter = new RecordAdapter(R.layout.item_recod_layout, readRecordFromDb);
                recordRecyclerView.setAdapter(recordAdapter);

                View bottomView = sheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
                bottomView.setBackgroundResource(android.R.color.transparent);

                BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(bottomView);
                // 高度设置<500会有问题
                sheetBehavior.setPeekHeight(1600);
                sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {}

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        if (Math.abs(slideOffset) > 0.4){
                            sheetDialog.dismiss();
                        }
                    }
                });
                sheetDialog.show();

                break;
            case R.id.gank_layout:
                mMePresenterComple.judgeToFlowAc(getContext());
                break;
        }
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected void initData() {
        mMePresenterComple = new MePresenterComple(this);
        mMePresenterComple.judgeShowName(getContext());

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED){
                    mToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
                    mToolbarLayout.setTitle("你还未登录哟");
                }else if (state == State.EXPANDED){
                    mToolbarLayout.setTitle("你还未登录哟");
                    mToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);
                }else {

                }
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void onPreDestroyView() {

    }
}
