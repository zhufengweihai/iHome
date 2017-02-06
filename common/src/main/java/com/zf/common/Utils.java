package com.zf.common;

import android.content.Context;

public class Utils {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static String getFileSize(int size) {
		if (size < 102.4f) {
			return "0.1KB";
		}
		if (size < 1048576) {
			float round = Math.round(size / 1024f * 10);
			return round / 10 + "KB";
		} else {
			float round = Math.round(size / 1048576f * 10);
			return round / 10 + "MB";
		}

	}
}