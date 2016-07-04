package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ProductTypeVO;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.ui.ProductListActivity;
import com.banksoft.XinChengShop.ui.ShopProductInfoActivity;
import com.banksoft.XinChengShop.widget.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 16/3/11.
 */
public class HomeDataListAdapter extends BaseAdapter {
    private Context mContext;
    public List dataList;
    private ImageLoader mImageLoader;

    public HomeDataListAdapter(Context context, List dataList) {
        this.mContext = context;
        this.dataList = dataList;
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {// 主题显示
            ProductTypeData productTypeData = (ProductTypeData) dataList.get(0);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.category_theme_layout, null);
            Object[] imageViews = new Object[]{convertView.findViewById(R.id.one), convertView.findViewById(R.id.two),
                    convertView.findViewById(R.id.three), convertView.findViewById(R.id.four),
                    convertView.findViewById(R.id.five),
                    convertView.findViewById(R.id.six), convertView.findViewById(R.id.seven),
                    convertView.findViewById(R.id.eight), convertView.findViewById(R.id.nine),
                    convertView.findViewById(R.id.ten),convertView.findViewById(R.id.eleven),
                    convertView.findViewById(R.id.twelve),convertView.findViewById(R.id.thirteen)
            };
            final LinkedList<ProductTypeVO> productTypeVOS = productTypeData.getData().getList();
            for (int i = 0;i < productTypeVOS.size();i++) {
                ImageView imageView = (ImageView) imageViews[i];
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,ProductListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(IntentFlag.TYPE_ID,productTypeVOS.get(finalI).getId());
                        intent.putExtra(IntentFlag.TITLE,productTypeVOS.get(finalI).getName());
                        mContext.startActivity(intent);
                    }
                });
                mImageLoader.displayImage(ControlUrl.BASE_URL +productTypeVOS.get(i).getIcon(),imageView,XCApplication.options);
            }


        } else if (position == 1) {//清仓产品
            ShopProductListData shopProductListData = (ShopProductListData) dataList.get(position);
           convertView = LayoutInflater.from(mContext).inflate(R.layout.clean_sale_layout,null);
            final MyGridView myGridView = (MyGridView) convertView.findViewById(R.id.gridView);
            final ClearanceAdapter clearanceAdapter = new ClearanceAdapter(mContext, shopProductListData.getData().getList());
            myGridView.setAdapter(clearanceAdapter);
            myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ShopProductListVO shopProductListVO = (ShopProductListVO) clearanceAdapter.getItem(position);
                    Intent intent = new Intent(mContext,ShopProductInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(IntentFlag.PRODUCT_ID,shopProductListVO.getId());
                    intent.putExtra(IntentFlag.SHOP_ID,shopProductListVO.getShopId());
                    mContext.startActivity(intent);
                }
            });
        } else if (position == 2) {// 团购产品

        }
        return convertView;
    }


}
