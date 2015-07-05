package com.softserve.entity.generator.service.request.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{

    public String parseSObjectJson(String sObjectJson, Class fieldName) {

        String removeRecords = "\"records\" : \\[ \\{.*? \\},";
        sObjectJson = Pattern.compile(removeRecords, Pattern.DOTALL).matcher(sObjectJson).replaceAll("");

        String removeAttributes = "\"attributes\" : \\{.*?\\},";
        sObjectJson = Pattern.compile(removeAttributes, Pattern.DOTALL).matcher(sObjectJson).replaceAll("");

            sObjectJson = sObjectJson
                                        .replaceAll("\\{\\n.*\"totalSize\" : .,\\n.*\"done\" :.*,","")
                                        .replaceAll("\\]","] }")
                                        .replaceAll("\\}\\n.*} \\] \\}\\n.*\\}","")
                                        .replaceAll("\\{\\n.*\\n.*\\n.*\\{","\\{")
                                        .replaceAll("\"[A-Z].*__r\" :", "\"fields\" : \\[ \\{")
                                        .replaceAll("__c","")
                                        .replaceAll("ColumnName","columnName")
                                        .replaceAll("\"EntityId\"","{ \"entityId\"")
                                        .replaceAll("TableName","tableName")
                                        .replaceAll("Name","name")
                                        .replaceAll("Type", "type")
                                        .replaceAll("FieldId","fieldId")
                                        .replaceAll("\"Entity\" : .*?,","");

        return sObjectJson;
    }
}
