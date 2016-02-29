package com.bitwise.datemanipulation;

import cascading.pipe.Pipe;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import com.hotels.plunger.Bucket;
import com.hotels.plunger.Data;
import com.hotels.plunger.DataBuilder;
import com.hotels.plunger.Plunger;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * Created by AniruddhaS on 2/29/2016.
 */
public class DateAssemblyTest {
    @Test
    public void itShouldPrintTheDateInRequiredFormat(){
        //init: Initialize the plunger and the DataBuilder objects
        Plunger plungerTestApi=new Plunger();
        Data corpus = new DataBuilder(new Fields("character","date","field2","field3"))
                .addTuple("A",20121111,35,12)
                .addTuple("B",20121110,40,11)
                .addTuple("C",20121105,56,12)
                .build();

        Pipe pipe = plungerTestApi.newNamedPipe("in", corpus);

        Pipe assemblyToTest = new DateAssembler(pipe);

        Bucket bucket = plungerTestApi.newBucket(new Fields("date"), assemblyToTest);

        List<Tuple> actual = bucket.result().asTupleList();

        assertThat(actual.get(0), CoreMatchers.is(new Tuple("2012-11-11")));
        //assertThat(actual.get(1), CoreMatchers.is(new Tuple("2012-11-10")));
        //assertThat(actual.get(2), CoreMatchers.is(new Tuple("2012-11-05")));
    }
}
