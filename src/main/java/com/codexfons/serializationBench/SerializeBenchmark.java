package com.codexfons.serializationBench;

import com.codexfons.serializationBench.fixtures.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.XmlRpcRequestConfig;
import org.apache.xmlrpc.common.XmlRpcHttpRequestConfigImpl;
import org.openjdk.jmh.annotations.*;
import org.xml.sax.SAXException;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.All;
import static org.openjdk.jmh.annotations.Scope.Thread;

@SuppressWarnings("unused") // Benchmark methods are auto-discovered at runtime and used.
@State(Thread)
@BenchmarkMode(All)
@OutputTimeUnit(MICROSECONDS)
public class SerializeBenchmark extends BenchmarkBase {

    @Override
    @Benchmark
    public void jacksonVanilla() throws JsonProcessingException {
        jacksonObjectMapper.writeValueAsString(Response.TEST_RESPONSE);
    }

    @Override
    @Benchmark
    public void jacksonOptimized() throws JsonProcessingException {
        jacksonOptimizedObjectMapper.writeValueAsString(Response.TEST_RESPONSE);
    }

    @Override
    @Benchmark
    void jacksonOptimizedSmile() throws Exception {
        jacksonOptimizedSmileObjectMapper.writeValueAsString(Response.TEST_RESPONSE);
    }

    @Override
    @Benchmark
    void jacksonOptimizedProtobuf() throws Exception {
        jacksonOptimizedProtobufObjectMapper.writeValueAsString(Response.TEST_RESPONSE);
    }

    @Override
    @Benchmark
    public void gson() {
        gson.toJson(Response.TEST_RESPONSE);
    }

    @Override
    @Benchmark
    public void xmlRpc() throws SAXException {
        xmlRpcWriter.write(new XmlRpcRequest() {
            @Override
            public XmlRpcRequestConfig getConfig() {
                return new XmlRpcHttpRequestConfigImpl();
            }

            @Override
            public String getMethodName() {
                return "Dummy";
            }

            @Override
            public int getParameterCount() {
                return 1;
            }

            @Override
            public Object getParameter(int pIndex) {
                return Response.TEST_RESPONSE;
            }
        });
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
