package com.softserve.entity.generator.service.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oonysh on 05.07.2015.
 */
public class Tes
{
    public static void main(String[] args)
    {
        String s = "ffd \"HOPE__c\" I've been messing \"HOPE__c\"  with it for hours and can get it to String the first " +
                "letters properly to uppercase but it adds the formatted string to the end of the lowercase " +
                "String. I  I'm making any sense at all. I've only been doing JAVA for about a year now so please " +
                "excuse the newbie code ";
        StringBuilder stringBuilder = new StringBuilder(s);
        Pattern pattern = Pattern.compile("\"[A-Z].*__c\"");
        Matcher matcher = pattern.matcher(stringBuilder);
        while (matcher.find()){
            System.out.println(matcher.start());
            stringBuilder.setCharAt(matcher.start()+1, Character.toLowerCase(stringBuilder.charAt(matcher.start()+1)));
        }
        System.out.println(stringBuilder);
    }
}
