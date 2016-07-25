package com.akari.quark.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.akari.quark.entity.userDetail.Message;
import com.akari.quark.entity.userDetail.UserDetail;
import com.akari.quark.network.OkHttpManager;
import com.akari.quark.util.GsonUtil;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by motoon on 2016/7/16.
 */
public class Information {
    private static final String TOKEN = "token";
    public static String img_url = "";
    public static String name = "";
    public static String introduction = "";
    public static int gender = -1;
    public static String unit = "";
    public static String education = "";
    public static String work = "";
    public static String residence = "";
    public static int profession = -1;
    public static String token = "";
    public static SharedPreferences sharedPreferences;
    public static void loadInfo(final Context context, final DataCallBack callback) {
        sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
        token = sharedPreferences.getString(TOKEN, "");
        String url = OkHttpManager.API_USER_DETAIL;
        OkHttpManager.DataCallBack dataCallBack = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(context,"用户信息请求失败",Toast.LENGTH_SHORT).show();
                img_url = sharedPreferences.getString("img_url", "default");
                name = sharedPreferences.getString("name", "暂未登录");
                introduction = sharedPreferences.getString("introduction", "无");
                gender = sharedPreferences.getInt("gender", -1);
                unit = sharedPreferences.getString("unit", "无");
                education = sharedPreferences.getString("education", "无");
                work = sharedPreferences.getString("work", "无");
                residence = sharedPreferences.getString("residence", "无");
                profession = sharedPreferences.getInt("profession", -1);
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                UserDetail userDetail = GsonUtil.GsonToBean(result, UserDetail.class);
                Long status = userDetail.getStatus();
                String errorCode = userDetail.getErrorCode();
                Message message = userDetail.getMessage();
                if (status == 1) {
                    //写入SharedPreference
                    img_url = message.getImgUrl();
                    name = message.getName();
                    Information.setName(name);
                    introduction = message.getIntroduction();
                    gender = message.getGender().intValue();
                    unit = message.getUnit();
                    education = message.getEducation();
                    work = message.getWork();
                    residence = message.getResidence();
                    profession = message.getProfession().intValue();

                    sharedPreferences.edit().putString("img_url", img_url).commit();
                    sharedPreferences.edit().putString("name", name).commit();
                    sharedPreferences.edit().putString("introduction", introduction).commit();
                    sharedPreferences.edit().putInt("gender", gender).commit();
                    sharedPreferences.edit().putString("unit", unit).commit();
                    sharedPreferences.edit().putString("education", education).commit();
                    sharedPreferences.edit().putString("work", work).commit();
                    sharedPreferences.edit().putString("residence", residence).commit();
                    sharedPreferences.edit().putInt("profession", profession).commit();
                    sharedPreferences.edit().putString(TOKEN, token).commit();
                    callback.requestSuccess(message);
                } else {
                    if (errorCode.equals("2003")) {
                        Toast.makeText(context, "请求超时", Toast.LENGTH_SHORT).show();
                    }
                    if (errorCode.equals("2004")) {
                        Toast.makeText(context, "该账户不存在", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        };
        OkHttpManager.getAsync(url, dataCallBack, OkHttpManager.X_ACCESS_TOKEN, token);
    }

    public static String getImg_url() {
        return img_url;
    }

    public static void setImg_url(String img_url) {
        Information.img_url = img_url;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Information.name = name;
    }

    public static String getIntroduction() {
        return introduction;
    }

    public static void setIntroduction(String introduction) {
        Information.introduction = introduction;
    }

    public static int getGender() {
        return gender;
    }

    public static void setGender(int gender) {
        Information.gender = gender;
    }

    public static String getUnit() {
        return unit;
    }

    public static void setUnit(String unit) {
        Information.unit = unit;
    }

    public static String getEducation() {
        return education;
    }

    public static void setEducation(String education) {
        Information.education = education;
    }

    public static String getWork() {
        return work;
    }

    public static void setWork(String work) {
        Information.work = work;
    }

    public static String getResidence() {
        return residence;
    }

    public static void setResidence(String residence) {
        Information.residence = residence;
    }

    public static int getProfession() {
        return profession;
    }

    public static void setProfession(int profession) {
        Information.profession = profession;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Information.token = token;
    }
    /**
     * 数据回调接口
     */
    public interface DataCallBack {
        void requestSuccess(Message result) throws Exception;
    }
}
