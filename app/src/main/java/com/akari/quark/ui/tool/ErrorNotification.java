package com.akari.quark.ui.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Akari on 16/7/13.
 */
public class ErrorNotification {

    /**
     * //status
     * FAIL_CODE: 0,           //失败
     * SUCCESS_CODE: 1,        //成功
     * //通用
     * PARAMS_LOST: 1000,      //参数缺失
     * DATABASE_ERR: 1001,     //数据库错误
     * DATA_LOST_ERR: 1002,    //数据缺失
     * //用户
     * MAIL_ERROR: 2000,       //邮箱错误（例如不符合邮箱规范）
     * MAIL_EXIST: 2001,       //该邮箱已存在（例如注册时候）
     * PASSWORD_ERROR: 2002,   //密码错误
     * TOKEN_EXPIRED: 2003,    //token超时（需要重新请求）
     * ACCOUNT_NOT_EXIST: 2004,//该账户不存在
     * //问题
     * QUESTION_NOT_EXIST: 3000,//问题不存在
     * //回答
     * ANSWER_NOT_EXIST: 4000, //回答不存在
     * //评论
     * COMMENT_NOT_EXIST: 5000,//评论不存在
     * //话题
     * TOPIC_NOT_EXIST: 6000,//活题不存在
     * TOPIC_EXIST: 6001     //话题已存在（例如添加话题的时候）
     */

    public static void errorNotify(Context mContext, int errorCode) {
        switch (errorCode) {
            case 0:
                Toast.makeText(mContext, "未出现错误", Toast.LENGTH_SHORT).show();
                return;
            case 1000:
                Toast.makeText(mContext, "参数缺失", Toast.LENGTH_SHORT).show();
                return;
            case 1001:
                Toast.makeText(mContext, "数据库错误", Toast.LENGTH_SHORT).show();
                return;
            case 1002:
                Toast.makeText(mContext, "数据缺失", Toast.LENGTH_SHORT).show();
                return;
            case 2000:
                Toast.makeText(mContext, "邮箱错误", Toast.LENGTH_SHORT).show();
                return;
            case 2001:
                Toast.makeText(mContext, "该邮箱已存在", Toast.LENGTH_SHORT).show();
                return;
            case 2002:
                Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();
                return;
            case 2003:
                Toast.makeText(mContext, "Token超时", Toast.LENGTH_SHORT).show();
                return;
            case 2004:
                Toast.makeText(mContext, "该账户不存在", Toast.LENGTH_SHORT).show();
                return;
            case 3000:
                Toast.makeText(mContext, "问题不存在", Toast.LENGTH_SHORT).show();
                return;
            case 4000:
                Toast.makeText(mContext, "回答不存在", Toast.LENGTH_SHORT).show();
                return;
            case 5000:
                Toast.makeText(mContext, "评论不存在", Toast.LENGTH_SHORT).show();
                return;
            case 6000:
                Toast.makeText(mContext, "话题不存在", Toast.LENGTH_SHORT).show();
                return;
            case 6001:
                Toast.makeText(mContext, "话题已存在", Toast.LENGTH_SHORT).show();
                return;
            default:
                Toast.makeText(mContext, "出现未知错误", Toast.LENGTH_SHORT).show();
        }
    }
}
