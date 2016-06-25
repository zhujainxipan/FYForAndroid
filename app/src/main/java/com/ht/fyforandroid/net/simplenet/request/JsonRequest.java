package com.ht.fyforandroid.net.simplenet.request;

import com.ht.fyforandroid.net.simplenet.response.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by niehongtao on 16/6/25.
 */
public class JsonRequest extends Request<JSONObject> {


    /**
     * @param method   请求方式
     * @param url      请求的目标url
     * @param listener 请求回调，将结果回调给用户
     */
    public JsonRequest(HttpMethod method, String url, RequestListener<JSONObject> listener) {
        super(method, url, listener);
    }


    /**
     * 将response的结果转化为jsonobject
     * @param response
     * @return
     */
    @Override
    public JSONObject parseResponse(Response response) {
        String jsonString = new String(response.getRawData());
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
