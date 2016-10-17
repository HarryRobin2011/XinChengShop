package com.banksoft.XinChengShop.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.ProductStandardGridAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ProductStandard;
import com.banksoft.XinChengShop.entity.ProductVO;
import com.banksoft.XinChengShop.entity.Standard;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by harry_robin on 15/11/20.
 */
public class OfSizesFragment extends XCBaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private TextView price, inventory, of_sizes;
    private ImageView mImageView, close;
    private Button ok;
    private LinearLayout content;
    private EditText numEditText;
    private ImageView imageBtnMinus, imageBtnPlus;


    private Standard selectStandard;
    private ProductStandard productStandard;
    private ProductVO productVO;
    private ImageLoader mImageLoader;
    private String imageUrl;

    @Override
    public View initContentView() {
       // selectStandard = (Standard) getArguments().get(IntentFlag.SELECT_STANDARD);// 选择的模版，可能为空
        productVO = (ProductVO) getArguments().get(IntentFlag.PRODUCT_VO);
        productStandard = (ProductStandard) getArguments().get(IntentFlag.PRODUCT_STANDARD);
        return LayoutInflater.from(mContext).inflate(R.layout.of_sizes_layout, null);
    }

    @Override
    public void initView(View view) {
        price = (TextView) view.findViewById(R.id.price);
        inventory = (TextView) view.findViewById(R.id.inventory);
        of_sizes = (TextView) view.findViewById(R.id.of_sizes);
        ok = (Button) view.findViewById(R.id.ok);
        content = (LinearLayout) view.findViewById(R.id.content);
        close = (ImageView) view.findViewById(R.id.close);
        mImageView = (ImageView) view.findViewById(R.id.image);

        imageBtnMinus = (ImageView) view.findViewById(R.id.imgBtn_minus);
        imageBtnPlus = (ImageView) view.findViewById(R.id.imgBtn_plus);

        numEditText = (EditText) view.findViewById(R.id.num);
    }

    @Override
    public void initData() {
        String imageFile = "";
        mImageLoader = ImageLoader.getInstance();
        price.setText(productVO.getSalePrice() + "元");
        inventory.setText(productVO.getStockNum() + "件");
        if (productVO.getIcon() != null) {
            imageFile = productVO.getIcon().split("\\|")[0];
        }
        mImageLoader.displayImage(ControlUrl.BASE_URL + imageFile, mImageView, XCApplication.options);
    }


    @Override
    public void initOperation() {
        content.removeAllViews();
        if (productStandard.getStandardsList() != null) {
            for (Standard standard : productStandard.getStandardsList()) {
                View standardView = LayoutInflater.from(mContext).inflate(R.layout.product_standard_item_layout, null);
                TextView standardName = (TextView) standardView.findViewById(R.id.standard_name);
                GridView gridView = (GridView) standardView.findViewById(R.id.gridView);
                standardName.setText(standard.getName());
                boolean isImage;
                if(standard.getType().equals("image")){// 图片
                  isImage = true;
                }else{
                  isImage = false;
                }
                final ProductStandardGridAdapter productStandardGridAdapter = new ProductStandardGridAdapter(mContext, standard.getList(),isImage);

                gridView.setAdapter(productStandardGridAdapter);

                gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        parent.getSelectedView().setSelected(true);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                content.addView(standardView);//

            }
        } else {
            // standardLayout.setVisibility(View.GONE);
        }


    }

    private void setStandardLayout() {

    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
