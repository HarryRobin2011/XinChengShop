package com.banksoft.XinChengShop.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.ConfigPush;
import com.banksoft.XinChengShop.dao.UpdateDao;
import com.banksoft.XinChengShop.entity.InfoValue;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.banksoft.XinChengShop.ui.fragment.*;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.JPushUtil;
import com.banksoft.XinChengShop.utils.update.UpdateUtil;

import java.util.*;

/**
 * Created by harry_robin on 15/11/4.
 */
public class XCMainActivity extends XCBaseActivity {
    /**
     * Called when the activity is first created.
     */
    private LinkedList<XCBaseFragment> baseFragmentList;
    public ProgressBar progressBar;

    public XCHomeFragment homePageFragment;
    public XCCategoryFragment categoryFragment;
    public XCMyselfFragment myselfFragment;
    public XCShopCartFragment shopCartFragment;
    public XCShopMallFragment shopMallFragment;
    private long firstTime;
    private Toast toast;

    public TabBarFragment tabFragment;

    private FrameLayout mFragmentLayout;


    private SharedPreferences pushSp;
    private UpdateDao updateDao;

    private static final int MSG_SET_ALIAS = 1001;
    private String TAG = "HarryRobin";

//    public Button leftBtn;
//    public Button rightBtn;
//    public LinearLayout searchLayout;
//    public TextView title;
//    public ImageView backGround;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;


                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };


    @Override
    protected void initContentView() {
        pushSp = getSharedPreferences(ConfigPush.PUSH_SP, Context.MODE_PRIVATE);
        setContentView(R.layout.main);
        new UpdateUtil(this, false).isUpdate();
        Set<String> tag = new LinkedHashSet<>();
        tag.add("PushOperation_Recomend");
        JPushInterface.setAliasAndTags(getApplicationContext(), null, tag, mTagsCallback);

        if (isLogin()) {
            JPushInterface.setAliasAndTags(getApplicationContext(), member.getMember().getId(), null, mAliasCallback);
        }
    }

    public static InfoValue getDataList(HashMap<String, HashMap<String, String>> poorMap, String name) {
        InfoValue dataInfo = new InfoValue();
        dataInfo.setName(name);
        dataInfo.setIsLeaf(false);
        LinkedList poorList = new LinkedList();
        for (String key : poorMap.keySet()) {
            InfoValue infoValue = new InfoValue();
            infoValue.setName(key);
            infoValue.setIsLeaf(false);
            HashMap<String, String> mapData = poorMap.get(key);
            List childList = new LinkedList();
            for (String aa : mapData.keySet()) {
                InfoValue infoValue1 = new InfoValue();
                infoValue.setName(key);
                infoValue.setValue(mapData.get(aa));
                childList.add(infoValue1);
                infoValue.setIsLeaf(true);
            }
            infoValue.setChildList(childList);
            poorList.add(infoValue);
        }
        dataInfo.setChildList(poorList);
        return dataInfo;
    }

    @Override
    protected void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tabFragment = new TabBarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.tab_fragment, tabFragment).commit();
    }

    @Override
    protected void initData() {
        //   title.setText(R.string.home_page);
    }

    @Override
    protected void initOperate() {
        // title.setText(R.string.app_name);

    }

    public void setFragmentTitle(CharSequence resId) {
        //  title.setText(resId);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (tabFragment.baseFragment.getClass().equals(XCHomeFragment.class)) {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                    //setToastView(getText(R.string.again_exit).toString());
                    toast = Toast.makeText(getApplicationContext(), "再按一次退出程序..", Toast.LENGTH_SHORT);
                    toast.show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    if (toast != null) {
                        toast.cancel();
                    }
                    animation = false;
                    finish();
                }
            } else {
                tabFragment.onTabSelected(0);
            }
        }
        return false;
    }

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";

                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";

                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(CommonUtil.TAG, logs);
            }
        }

    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    if (JPushUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }

    };

//    private void showExitDialog(){
//        niftyDialogBuilder = NiftyDialogBuilder.getInstance(this);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_view, null);
//        niftyDialogBuilder.setContentView(view);
//        niftyDialogBuilder.withEffect(Effectstype.Slidetop);
//        niftyDialogBuilder.isCancelableOnTouchOutside(false);
//        niftyDialogBuilder.withDuration(1000);
//        niftyDialogBuilder.show();
//        TextView title = (TextView) view.findViewById(R.id.title);
//        TextView message = (TextView) view.findViewById(R.id.content);
//        TextView ok = (TextView) view.findViewById(R.id.ok);
//        TextView cancel = (TextView) view.findViewById(R.id.cancel);
//        title.setText(R.string.alert);
//        message.setText(R.string.exit_alert);
//        ok.setText(R.string.ok);
//        cancel.setText(R.string.cancel);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                animation = true;
//                finish();
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                niftyDialogBuilder.dismiss();
//            }
//        });
//    }

}
