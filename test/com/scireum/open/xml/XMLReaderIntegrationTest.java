package com.scireum.open.xml;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * @author jaran
 */
public class XMLReaderIntegrationTest {

    @Test
    public void parse_when_xml_uses_namespace_query_metods_of_node_still_works() {

        XMLReader reader = new XMLReader();
        final AtomicReference<StructuredNode> childNode = new AtomicReference<StructuredNode>();
        final AtomicReference<StructuredNode> anotherNode = new AtomicReference<StructuredNode>();
        reader.addHandler("myns:root", new NodeHandler() {
            @Override
            public void process(StructuredNode node) {

                try {

                    childNode.set(node.queryNode("child"));
                    anotherNode.set(node.queryNode("another"));
                } catch (XPathExpressionException e) {
                    fail(e.getMessage());
                }
            }
        });

        try {
            reader.parse(getClass().getResourceAsStream("/xml-with-namespace.xml"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals("myns:child", childNode.get().getNodeName());
        assertEquals("myns:another", anotherNode.get().getNodeName());
    }
}
