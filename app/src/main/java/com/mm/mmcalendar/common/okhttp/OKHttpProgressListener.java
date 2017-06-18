package com.mm.mmcalendar.common.okhttp;

/**
 * OKhttp进度监听
 * @author xxqj
 *
 */
public interface OKHttpProgressListener {
	
	void onProgress(long bytesRead, long contentLength, boolean done);

}
