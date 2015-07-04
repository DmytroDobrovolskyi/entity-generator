package main;


import com.google.gson.Gson;
import com.softserve.entity.generator.entity.Entity;
import com.softserve.entity.generator.entity.Field;

public class Test {
    public static void main(String[] args) {
        String attempt = "{ 'tableName' : 'PC', 'name' : 'Personal Computer', 'fields' : [ { 'name' : 'CPU', 'columnName' : 'CPU', 'type' : 'varchar' } ] }";

        Gson gson = new Gson();

        Entity entity = gson.fromJson(attempt, Entity.class);

        for (Field field : entity.getFields()) {
            field.setEntity(entity);
        }

        System.out.println(entity.getFields());
    }
}
