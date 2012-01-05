package com.scireum.open.xml;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class SimpleXPathCompiler implements XPathCompiler {

	private static final XPathFactory XPATH = XPathFactory.newInstance();

	@Override
	public XPathExpression compile(String xpath)
			throws XPathExpressionException {
		return XPATH.newXPath().compile(xpath);
	}

}
