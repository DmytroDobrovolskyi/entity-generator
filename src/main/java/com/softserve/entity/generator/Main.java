package com.softserve.entity.generator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Main
{
    private int x;

    public static void main(String[] args)
    {
        System.out.println("Hello");
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }

        if (o == null || getClass() != o.getClass()) { return false; }

        Main main = (Main) o;

        return new EqualsBuilder()
                .append(x, main.x)
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(x)
                .toHashCode();
    }
}
