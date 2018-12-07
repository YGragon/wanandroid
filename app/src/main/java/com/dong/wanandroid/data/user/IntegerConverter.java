package com.dong.wanandroid.model.user;

import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by macmini002 on 18/6/4.
 * GreenDao 中存储集合的转换方式
 */

public class IntegerConverter implements PropertyConverter<List<Integer>, String> {

    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        else {
            List<String> list_str = Arrays.asList(databaseValue.split(","));
            List<Integer> list_transport = new ArrayList<>();
            for (String s : list_str) {
                list_transport.add(new Gson().fromJson(s, Integer.class));
            }
            return list_transport;
        }
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        if(entityProperty==null){
            return null;
        }
        else{
            StringBuilder sb = new StringBuilder();
            for (Integer array : entityProperty) {
                String str = new Gson().toJson(array);
                sb.append(str);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
