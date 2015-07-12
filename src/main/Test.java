package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Test
{
    public static void main(String[] args)
    {
        String json = "{'id': '1111', 'name' : 'Dmytro'}";
        Map<String, String> src = new HashMap<String, String>();

        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Gson gson = new Gson();

        src = gson.fromJson(json, type);

        System.out.println(src);
        System.out.println(gson.toJson(src));
    }
}
