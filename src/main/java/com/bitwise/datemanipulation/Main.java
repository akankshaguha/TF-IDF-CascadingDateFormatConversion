package com.bitwise.datemanipulation;

/**
 * Created by AniruddhaS on 2/29/2016.
 */
import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.hadoop2.Hadoop2MR1FlowConnector;
import cascading.operation.Identity;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.Scheme;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tap.hadoop.Lfs;
import cascading.tuple.Fields;

import java.util.Properties;

/**
 * Created by AniruddhaS on 2/22/2016.
 */
public class Main {
    public static void main(String[] args) {
        String sourcePath=args[0];
        String sinkPath=args[1];
        Fields fields=new Fields("character","date","field2","field3");
        Scheme sourceScheme=new TextDelimited(fields,",");
        Scheme sinkScheme=new TextDelimited(true,",");


        Tap sourceTap=new Lfs(sourceScheme,sourcePath);
        Tap sinkTap=new Lfs(sinkScheme,sinkPath, SinkMode.REPLACE);
        Pipe sourcePipe=new Pipe("source");
        //Pipe shapedPipe=new Each(sourcePipe,new Fields("year_of_release"),new Identity());
        sourcePipe=new DateAssembler(sourcePipe);

        Properties properties=new Properties();
        AppProps.setApplicationJarClass(properties,Main.class);
        FlowDef flowDef=FlowDef.flowDef()
                .setName("DateAssembler")
                .addSource(sourcePipe,sourceTap)
                .addTailSink(sourcePipe,sinkTap);
        Flow ZFlow=new Hadoop2MR1FlowConnector(properties).connect(flowDef);
        ZFlow.complete();

    }
}
