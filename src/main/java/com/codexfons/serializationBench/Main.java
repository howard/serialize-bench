package com.codexfons.serializationBench;

import com.codexfons.serializationBench.fixtures.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.xmlrpc.XmlRpcConfig;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.XmlRpcRequestConfig;
import org.apache.xmlrpc.common.*;
import org.apache.xmlrpc.serializer.XmlRpcWriter;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
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
        XmlRpcWriter xmlRpcWriter = new XmlRpcWriter(xmlRpcClientConfig, xmlRpcHandler, xmlRpcTypeFactory);

        try {
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
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
