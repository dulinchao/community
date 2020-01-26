package com.community.my.community.provider;

import com.alibaba.fastjson.JSON;
import com.community.my.community.dto.AccessTokenDTO;
import com.community.my.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    /**
     * 通过AccessTokenDTO携带请求参数获取access_token
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));//把accessTokenDTO转换为String再放到requestbody中
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseText = response.body().string();//access_token=5eff48eebb18f4ef06db1a8bb6a0f0d1374cdf85&scope=user&token_type=bearer 这是返回的值，对其拆分
            String tokenstr = responseText.split("&")[0];
            String access_token = tokenstr.split("=")[1];
            System.out.println("accesstoken="+access_token);
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户信息，返回一个GithubUser对象
     * @param access_token
     * @return
     */
    public GithubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
