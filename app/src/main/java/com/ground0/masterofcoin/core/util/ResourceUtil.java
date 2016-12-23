package com.ground0.masterofcoin.core.util;

import com.ground0.masterofcoin.R;

/**
 * Created by zer0 on 24/12/16.
 */

public class ResourceUtil {

  public static int getDrawable(String category) {
    if (category == null) return R.drawable.ic_phone;
    if ("Taxi".equalsIgnoreCase(category)) return R.drawable.ic_taxi;
    if ("Recharge".equalsIgnoreCase(category)) {
      return R.drawable.ic_phone;
    } else {
      return R.drawable.ic_phone;
    }
  }

  public static int getColor(String state) {
    if (state == null) return R.color.md_yellow_700;
    if ("unverified".equalsIgnoreCase(state)) return R.color.md_yellow_700;
    if ("verified".equalsIgnoreCase(state)) return R.color.md_green_700;
    if ("fraud".equalsIgnoreCase(state)) {
      return R.color.md_red_700;
    } else {
      return R.color.md_yellow_700;
    }
  }
}
