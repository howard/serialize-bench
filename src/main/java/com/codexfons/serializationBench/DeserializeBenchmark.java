package com.codexfons.serializationBench;

import com.codexfons.serializationBench.fixtures.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.All;
import static org.openjdk.jmh.annotations.Scope.Thread;

@SuppressWarnings("unused") // Benchmark methods are auto-discovered at runtime and used.
@State(Thread)
@BenchmarkMode(All)
@OutputTimeUnit(MICROSECONDS)
public class DeserializeBenchmark extends BenchmarkBase {

    private String json;

    @Setup
    public void loadFixtures() throws IOException {
        InputStream jsonStream = getClass().getResourceAsStream("/response.json");
        json = IOUtils.readLines(jsonStream, StandardCharsets.UTF_8).stream().reduce((s, s2) -> s + '\n' + s2).get();
    }

    @Override
    @Benchmark
    public void jacksonVanilla() throws IOException {
        jacksonObjectMapper.readValue(json, Response.class);
    }

    @Override
    @Benchmark
    public void jacksonOptimized() throws IOException {
        jacksonOptimizedObjectMapper.readValue(json, Response.class);
    }

    @Override
    @Benchmark
    void jacksonOptimizedSmile() throws Exception {

    }

    @Override
    @Benchmark
    void jacksonOptimizedProtobuf() throws Exception {

    }

    @Override
    @Benchmark
    public void gson() {
        gson.toJson(Response.TEST_RESPONSE);
    }

    @Override
    @Benchmark
    public void xmlRpc() {

    }

    @Override
    @Benchmark
    public void xml() {

    }

    @Override
    @Benchmark
    public void woodstox() {

    }

    @Override
    @Benchmark
    public void protocolBuffer() {

    }
}
