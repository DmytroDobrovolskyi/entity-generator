package com.softserve.entity.generator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DefaultParser implements CommandLineParser {
    @Override
    public CommandLine parse(Options options, String[] arguments) throws ParseException {
        return null;
    }

    @Override
    public CommandLine parse(Options options, String[] arguments, boolean stopAtNonOption) throws ParseException {
        return null;
    }
}
