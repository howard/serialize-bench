package com.codexfons.serializationBench;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.protobuf.ProtobufFactory;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.xmlrpc.XmlRpcConfig;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.common.*;
import org.apache.xmlrpc.serializer.XmlRpcWriter;
import org.openjdk.jmh.annotations.Setup;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;

/**
 * Defines which technologies should be benchmarked.
 */
abstract class BenchmarkBase {
    abstract void jacksonVanilla() throws IOException;
    abstract void jacksonOptimized() throws IOException;
    abstract void jacksonOptimizedSmile() throws Exception;
    abstract void jacksonOptimizedProtobuf() throws Exception;
    abstract void gson();
    abstract void xmlRpc() throws SAXException;
    abstract void xml();
    abstract void woodstox();
    abstract void protocolBuffer();

    ObjectMapper jacksonObjectMapper;
    ObjectMapper jacksonOptimizedObjectMapper;
    ObjectMapper jacksonOptimizedSmileObjectMapper;
    ObjectMapper jacksonOptimizedProtobufObjectMapper;
    Gson gson;

    XmlRpcWriter xmlRpcWriter;

    @Setup
    public void createSerializers() {
        jacksonObjectMapper = new ObjectMapper();

        jacksonOptimizedObjectMapper = new ObjectMapper();
        jacksonOptimizedObjectMapper.registerModule(new AfterburnerModule());

        jacksonOptimizedSmileObjectMapper = new ObjectMapper(new SmileFactory());
        jacksonOptimizedSmileObjectMapper.registerModule(new AfterburnerModule());

        jacksonOptimizedProtobufObjectMapper = new ObjectMapper(new ProtobufFactory());
        jacksonOptimizedProtobufObjectMapper.registerModule(new AfterburnerModule());

        gson = new GsonBuilder().create();

        XmlRpcStreamConfig xmlRpcClientConfig = new XmlRpcHttpRequestConfigImpl() {{
            setEnabledForExceptions(true);
            setEnabledForExtensions(true);
        }};

        ContentHandler xmlRpcHandler =  new DefaultHandler();
        XmlRpcController xmlRpcController = new XmlRpcController() {
            @Override
            protected XmlRpcWorkerFactory getDefaultXmlRpcWorkerFactory() {
                return new XmlRpcWorkerFactory(this) {
                    @Override
                    protected XmlRpcWorker newWorker() {
                        return new XmlRpcWorker() {
                            @Override
                            public XmlRpcController getController() {
                                return null;
                            }

                            @Override
                            public Object execute(XmlRpcRequest pRequest) throws XmlRpcException {
                                return null;
                            }
                        };
                    }
                };
            }

            @Override
            public XmlRpcConfig getConfig() {
                return new XmlRpcHttpRequestConfigImpl();
            }
        };
        TypeFactory xmlRpcTypeFactory = new TypeFactoryImpl(xmlRpcController);
        xmlRpcWriter = new XmlRpcWriter(xmlRpcClientConfig, xmlRpcHandler, xmlRpcTypeFactory);
    }

}
