package duc.aintea.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup
public class TestBenchMark {
    private AtomicInteger counter;

    @Setup(Level.Iteration)
    public void setup() {
        counter = new AtomicInteger(0);
    }

    @Benchmark
    public void aSimpleBench() {
        counter.addAndGet(1);
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        System.out.println(counter.get());
    }

    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder().include(TestBenchMark.class.getSimpleName()).build();
        new Runner(opt).run();
    }
}
