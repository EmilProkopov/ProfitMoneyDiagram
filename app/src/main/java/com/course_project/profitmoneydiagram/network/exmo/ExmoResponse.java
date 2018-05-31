package com.course_project.profitmoneydiagram.network.exmo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExmoResponse {

@SerializedName("BTC_USDT")
@Expose
private BTCUSDT bTCUSDT;

public BTCUSDT getBTCUSDT() {
return bTCUSDT;
}

public void setBTCUSDT(BTCUSDT bTCUSDT) {
this.bTCUSDT = bTCUSDT;
}

}