package com.akari.quark.data;

/**
 * Created by motoon on 2016/5/12.
 */
import java.util.ArrayList;
import java.util.List;
public class DataDemo
{
    public static final List<String> generateData(int size)
    {
        if (size <= 0)
            return null;
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            datas.add("这是列表数据"+i);
        }
        return datas;
    }
}
