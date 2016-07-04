package com.banksoft.XinChengShop.ui.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.XCMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.util.HashMap;

/**
 * Created by harry_robin on 15/11/4.
 */
public class TabBarFragment extends Fragment {
    ImageView tab_one;
    ImageView tab_two;
    ImageView tab_three;
    ImageView tab_four;
    //ImageView tab_five;

    ImageView tab_onebg;
    ImageView tab_twobg;
    ImageView tab_threebg;
    ImageView tab_fourbg;
    //ImageView tab_fivebg;

    TextView tab_one_text,tab_two_text,tab_three_text,tab_four_text;//tab_five_text;

    private FrameLayout tab_one_bg;
    private FrameLayout tab_two_bg;
    private FrameLayout tab_three_bg;
    private FrameLayout tab_four_bg;
    //private FrameLayout tab_five_bg;

    private XCMainActivity myActivity;
    public XCBaseFragment baseFragment;
    private XCBaseFragment currentFragment;

    private HashMap<Integer,Fragment> viewHashMap;

    private View bannerView;
//
//    private LinearLayout searchLayout;
//
//    private TextView title;
//    private ImageView backGround;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = (XCMainActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.tab_fragment_layout, container, false);
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

        this.tab_four = (ImageView) mainView.findViewById(R.id.toolbar_tabfour);
        this.tab_fourbg = (ImageView) mainView.findViewById(R.id.toolbar_tabfourbg);
        this.tab_four_bg = (FrameLayout) mainView.findViewById(R.id.tab_four_layout);
        this.tab_four_text = (TextView) mainView.findViewById(R.id.tab_four_text);
        this.tab_four_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabSelected(3);
            }
        });

        bannerView = LayoutInflater.from(myActivity.getApplicationContext()).inflate(R.layout.home_banner_layout,null);

//        searchLayout = (LinearLayout) myActivity.findViewById(R.id.search_layout);
//        title = (TextView) myActivity.findViewById(R.id.titleText);
//        backGround = (ImageView) myActivity.findViewById(R.id.titleBg);

//        this.tab_five = (ImageView) mainView.findViewById(R.id.toolbar_tabfive);
//        this.tab_fivebg = (ImageView) mainView.findViewById(R.id.toolbar_tabfivebg);
//        this.tab_five_bg = (FrameLayout) mainView.findViewById(R.id.tab_five_layout);
//        this.tab_five_text = (TextView) mainView.findViewById(R.id.tab_five_text);
//        this.tab_five_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onTabSelected(4);
//            }
//        });

        onTabSelected(0);
    }

    public void onTabSelected(int position) {
        baseFragment = (XCBaseFragment) viewHashMap.get(position);
        switch (position) {
            case 0:
                this.tab_one.setImageResource(R.drawable.guide_home_on);
                this.tab_two.setImageResource(R.drawable.guide_discover_nm);
                this.tab_three.setImageResource(R.drawable.guide_cart_nm);
                this.tab_four.setImageResource(R.drawable.guide_account_nm);
                //this.tab_five.setImageResource(R.drawable.guide_tfaccount_nm);

                this.tab_onebg.setVisibility(View.VISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);
                //this.tab_fivebg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.bg_red));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.text_black_light));
               // this.tab_five_text.setTextColor(getResources().getColor(R.color.text_black_light));

                if(baseFragment == null){
                    baseFragment = new XCHomeFragment();
                    viewHashMap.put(position,baseFragment);
                }
//                backGround.setBackgroundResource(R.color.bg_red);
//                searchLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                this.tab_one.setImageResource(R.drawable.guide_home_nm);
                this.tab_two.setImageResource(R.drawable.guide_discover_on);
                this.tab_three.setImageResource(R.drawable.guide_cart_nm);
                this.tab_four.setImageResource(R.drawable.guide_account_nm);
               // this.tab_five.setImageResource(R.drawable.guide_tfaccount_nm);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.VISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);
               // this.tab_fivebg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.bg_red));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.text_black_light));
               // this.tab_five_text.setTextColor(getResources().getColor(R.color.text_black_light));
                if(baseFragment == null){
                    baseFragment = new XCCategoryFragment();
                    viewHashMap.put(position,baseFragment);
                }
//                backGround.setBackgroundResource(R.color.white);
//                searchLayout.setVisibility(View.GONE);
//                title.setText(R.string.category_title);
                break;
            case 2:
                this.tab_one.setImageResource(R.drawable.guide_home_nm);
                this.tab_two.setImageResource(R.drawable.guide_discover_nm);
                this.tab_three.setImageResource(R.drawable.guide_cart_on);
                this.tab_four.setImageResource(R.drawable.guide_account_nm);
               // this.tab_five.setImageResource(R.drawable.guide_tfaccount_nm);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.VISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);
               // this.tab_fivebg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.bg_red));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.text_black_light));
               // this.tab_five_text.setTextColor(getResources().getColor(R.color.text_black_light));
                    baseFragment = new XCShopCartFragment();

//                backGround.setBackgroundResource(R.color.white);
//                searchLayout.setVisibility(View.GONE);
//                title.setText(R.string.shop_cart);
                break;
            case 3:
                this.tab_one.setImageResource(R.drawable.guide_home_nm);
                this.tab_two.setImageResource(R.drawable.guide_discover_nm);
                this.tab_three.setImageResource(R.drawable.guide_cart_nm);
                this.tab_four.setImageResource(R.drawable.guide_account_on);
                //this.tab_five.setImageResource(R.drawable.guide_tfaccount_nm);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.VISIBLE);
               // this.tab_fivebg.setVisibility(View.INVISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.bg_red));
               // this.tab_five_text.setTextColor(getResources().getColor(R.color.text_black_light));
                if(baseFragment == null){
                    baseFragment = new XCMyselfFragment();
                    viewHashMap.put(position,baseFragment);
                }
//                backGround.setBackgroundResource(R.color.bg_red);
//                searchLayout.setVisibility(View.GONE);
//                title.setText(R.string.my_self);

                break;
            case 4:
                this.tab_one.setImageResource(R.drawable.guide_home_nm);
                this.tab_two.setImageResource(R.drawable.guide_discover_nm);
                this.tab_three.setImageResource(R.drawable.guide_cart_nm);
                this.tab_four.setImageResource(R.drawable.guide_account_nm);
                //this.tab_five.setImageResource(R.drawable.guide_tfaccount_on);

                this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.INVISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);
               // this.tab_fivebg.setVisibility(View.VISIBLE);

                this.tab_one_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_two_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_three_text.setTextColor(getResources().getColor(R.color.text_black_light));
                this.tab_four_text.setTextColor(getResources().getColor(R.color.text_black_light));
            //    this.tab_five_text.setTextColor(getResources().getColor(R.color.bg_red));

                if(baseFragment == null){
                    baseFragment = new XCShopMallFragment();
                    viewHashMap.put(position,baseFragment);
                }
                break;
        }
        android.support.v4.app.FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();

        if(currentFragment != null){
           if(!baseFragment.isAdded()){
              transaction.hide(currentFragment).add(R.id.content,baseFragment).commit();
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
