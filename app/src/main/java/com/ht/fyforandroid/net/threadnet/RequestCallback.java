package com.ht.fyforandroid.net.threadnet;

public interface RequestCallback
{
	public void onSuccess(String content);

	public void onFail(String errorMessage);
}
