package com.scireum.open.xml;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

/**
 * Compiles an xpath into an expression.
 */
public interface XPathCompiler {
	XPathExpression compile(String xpath) throws XPathExpressionException;
}
