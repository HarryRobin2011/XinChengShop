package com.banksoft.XinChengShop.ui.fragment.takeout;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.takeout.TakeOutMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.util.HashMap;

/**
 * Created by harry_robin on 16/3/18.
 */
public class TakeOutTabbarFragment extends Fragment{
    ImageView tab_one;
    ImageView tab_two;
    ImageView tab_three;

    ImageView tab_onebg;
    ImageView tab_twobg;
    ImageView tab_threebg;

    TextView tab_one_text,tab_two_text,tab_three_text;

    private FrameLayout tab_one_bg;
    private FrameLayout tab_two_bg;
    private FrameLayout tab_three_bg;

    private TakeOutMainActivity myActivity;
    public XCBaseFragment baseFragment;
    public XCBaseFragment currentFragment;

    private HashMap<Integer,Fragment> viewHashMap;

    private View bannerView;

    private FragmentTransaction fragmentTransaction;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = (TakeOutMainActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.take_out_tab_fragment_layout, container, false);
        viewHashMap = new HashMap<>();
        init(mainView);

        return mainView;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        setRetainInstance(true);
    }

    private void init(View mainView) {
        this.tab_one = (ImageView) mainView.findViewById(R.id.toolbar_tabone);
        this.tab_onebg = (ImageView) mainView.findViewById(R.id.toolbar_tabonebg);
        this.tab_one_bg = (FrameLayout) mainView.findViewById(R.id.tab_one_layout);
        this.tab_one_text = (TextView) mainView.findViewById(R.id.tab_one_text);
        this.tab_one_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(0);
            }
        });

        this.tab_two = (ImageView) mainView.findViewById(R.id.toolbar_tabtwo);
        this.tab_twobg = (ImageView) mainView.findViewById(R.id.toolbar_tabtwobg);
        this.tab_two_bg = (FrameLayout) mainView.findViewById(R.id.tab_two_layout);
        this.tab_two_text = (TextView) mainView.findViewById(R.id.tab_two_text);
        this.tab_two_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(1);
            }
        });

        this.tab_three = (ImageView) mainView.findViewById(R.id.toolbar_tabthree);
        this.tab_threebg = (ImageView) mainView.findViewById(R.id.toolbar_tabthreebg);
        this.tab_three_bg = (FrameLayout) mainView.findViewById(R.id.tab_three_layout);
        this.tab_three_text = (TextView) mainView.findViewById(R.id.tab_three_text);
        this.tab_three_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(2);
            }
        });


        bannerView = LayoutInflater.from(myActivity.getApplicationContext()).inflate(R.layout.home_banner_layout,null);

        onTabSelected(0);
    }

    public void onTabSelected(int position) {
        baseFragment = (XCBaseFragment) viewHashMap.get(position);
        switch (position) {
            case 0:
                this.tab_one.setImageResource(R.drawable.guide_home_on);
                this.tab_two.setImageResource(R.drawable.guide_discover_nm);
                this.tab_three.setImageResource(R.drawable.guide_account_nm);

                this.tab_onebg.setVisibility(View.VISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.bg_red));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.text_black_light));

                if(baseFragment == null){
                    baseFragment = new TakeOutShopListFragment();
                    viewHashMap.put(position,baseFragment);
                }
                break;
            case 1:
                this.tab_one.setImageResource(R.drawable.guide_home_nm);
                this.tab_two.setImageResource(R.drawable.guide_discover_on);
                this.tab_three.setImageResource(R.drawable.guide_account_nm);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.VISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.bg_red));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.text_black_light));
                if(baseFragment == null){
                    baseFragment = new TakeOutOrderListFragment();
                    viewHashMap.put(position,baseFragment);
                }
                break;
            case 2:
                this.tab_one.setImageResource(R.drawable.guide_home_nm);
                this.tab_two.setImageResource(R.drawable.guide_discover_nm);
                this.tab_three.setImageResource(R.drawable.guide_account_on);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.VISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.bg_red));
                if(baseFragment == null){
                    baseFragment = new TakeOutMySelfFragment();
                    viewHashMap.put(position,baseFragment);
                }
                break;
        }
        android.support.v4.app.FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();

        if(currentFragment != null){
            if(!baseFragment.isAdded()){
                transaction.hide(currentFragment).add(R.id.content,baseFragment,baseFragment.getClass().getSimpleName()).commit();
            }else{
                transaction.hide(currentFragment).show(baseFragment).commit();
            }
        }else{
            transaction.add(R.id.content,baseFragment).commit();
        }
        this.currentFragment = baseFragment;


    }

    public interface NavigationCallBackListener {
        public void OnCallBackListener(int position);
    }


    @Override
    public void onResume() {

        super.onResume();
    }
}
