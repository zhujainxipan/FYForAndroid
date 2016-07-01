package com.ht.fyforandroid.net.threadnet;

import java.util.List;

public class RemoteService {
	private static RemoteService service = null;

	private RemoteService() {

	}

	public static synchronized RemoteService getInstance() {
		if (RemoteService.service == null) {
			RemoteService.service = new RemoteService();
		}
		return RemoteService.service;
	}

	public void invoke(final URLData urlData,
			final List<RequestParameter> params,
			final RequestCallback callBack) {
		HttpRequest request = RequestManager.getInstance().createRequest(
				urlData, params, callBack);
		DefaultThreadPool.getInstance().execute(request);
	}
}