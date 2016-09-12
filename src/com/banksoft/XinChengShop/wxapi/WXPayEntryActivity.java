package com.banksoft.XinChengShop.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.PayConfig;
import com.banksoft.XinChengShop.ui.OrderListActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
		View view  = null;

        
    	api = WXAPIFactory.createWXAPI(this, PayConfig.wei_xin_app_id);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
       Log.i("HarryRobin","");
	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				case 0:
					finish();
					Toast.makeText(getApplicationContext(),getText(R.string.pay_success),Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
					startActivity(intent);
					break;
				case -1:
					Toast.makeText(getApplicationContext(),getText(R.string.pay_error_again),Toast.LENGTH_SHORT).show();
					finish();
					break;
				case -2:
					Toast.makeText(getApplicationContext(),getText(R.string.pay_cancel),Toast.LENGTH_SHORT).show();
					finish();
					break;
			}
		}
	}
}