package com.bitwise.datemanipulation;


import cascading.operation.Identity;
import cascading.operation.text.DateFormatter;
import cascading.operation.text.DateParser;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.pipe.SubAssembly;
import cascading.pipe.assembly.Rename;
import cascading.tuple.Fields;

import java.time.DateTimeException;

/**
 * Created by AniruddhaS on 2/29/2016.
 */
public class DateAssembler extends SubAssembly {
    public DateAssembler(Pipe sourcePipe) {
        setPrevious(sourcePipe);
        Pipe datePipe=new Pipe("datePipe", sourcePipe);
        datePipe=new Each(datePipe,new Fields("date"),new DateParser("yyyymmdd"));
        datePipe=new Rename(datePipe,new Fields("ts"),new Fields("date"));
        //datePipe=new Each(datePipe,new Fields("character","date","field2","field3"),new Identity());
        Pipe formattedDatePipe= new Each(datePipe,new Fields("date"),new DateFormatter(new Fields("date"),"yyyy-mm-dd"));

        setTails(formattedDatePipe);
    }
}
