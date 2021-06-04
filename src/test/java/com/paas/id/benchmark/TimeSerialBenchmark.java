package com.paas.id.benchmark;

import com.paas.id.Application;
import com.paas.idgen.service.TimeSerial;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jaswine
 * @date 2021-06-03 15:00:39
 */
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Measurement(iterations = 5,time = 10,timeUnit = TimeUnit.SECONDS)
@Threads(value = 8)
@Warmup(iterations = 2)
@Fork(value = 2)
public class TimeSerialBenchmark {

    private ConfigurableApplicationContext context;
    private TimeSerial timeSerial;

    @Setup
    public void init(){
        context = SpringApplication.run(Application.class);
        timeSerial = context.getBean(TimeSerial.class);
    }

    @TearDown
    public void down(){
        context.close();
    }

    @Benchmark
    public void test(){
        timeSerial.generateSerial();
    }


    public static void main(String[] args) throws RunnerException, IOException {
        String relativelyPath = System.getProperty("user.dir");
        String log = relativelyPath.concat("/log/benchmark.log") ;
        File file = new File(log);

        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        Options options = new OptionsBuilder()
                .include(TimeSerialBenchmark.class.getSimpleName())
                .output(log)
                .build();
        new Runner(options).run();
    }
}
