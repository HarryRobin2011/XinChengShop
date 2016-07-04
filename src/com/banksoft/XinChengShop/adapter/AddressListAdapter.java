package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.MemberAddressVO;
import java.util.List;

/**
 * Created by harry_robin on 15/11/29.
 */
public class AddressListAdapter extends BaseMyAdapter {

    public AddressListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    public class AddressListHolder extends BusinessHolder{
        private ImageView imageView;
        private TextView name,telPhone,address;
        private LinearLayout detailLayout;
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.shipping_address_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        AddressListHolder holder = new AddressListHolder();
        holder.imageView = (ImageView) cellView.findViewById(R.id.imageView);
        holder.name = (TextView) cellView.findViewById(R.id.shipping_name);
        holder.telPhone = (TextView) cellView.findViewById(R.id.telPhone);
        holder.address = (TextView) cellView.findViewById(R.id.address);
        holder.detailLayout = (LinearLayout) cellView.findViewById(R.id.address_detail_layout);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        AddressListHolder holder = (AddressListHolder) cellHolder;
        MemberAddressVO memberAddressVO = (MemberAddressVO) dataList.get(position);
        holder.detailLayout.setVisibility(View.VISIBLE);
        holder.name.setText("联系人："+memberAddressVO.getUserName());
        holder.address.setText("收货地址："+memberAddressVO.getAddress());
        holder.telPhone.setText("联系电话："+memberAddressVO.getTelephone());
        holder.imageView.setVisibility(View.GONE);
        return cellView;
    }
}
