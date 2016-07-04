package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created by harry_robin on 15/11/12.
 *
 * {success:true,data:{shop:shopVO类，member:memberVO类，memberInfo:memberInfo类,accountInfo:accountInfo类}}
 */
public class Member extends BaseEntity {
    private ShopVO shop;// 店铺详情（status; //商铺是否开启
    private boolean auditStatus;  //商铺是否审核）
    private MemberVO member;//会员详情
    private MemberInfo memberInfo;//会员个人信息
    private AccountInfo accountInfo;//账号信息（登陆密码，支付密码）

    public ShopVO getShop() {
        return shop;
    }

    public void setShop(ShopVO shop) {
        this.shop = shop;
    }

    public MemberVO getMember() {
        return member;
    }

    public void setMember(MemberVO member) {
        this.member = member;
    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }
}
