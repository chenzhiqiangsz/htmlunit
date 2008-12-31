/*
 * Copyright (c) 2002-2009 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.javascript.host;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebTestCase;

/**
 * Tests for {@link XPathResult}.
 *
 * @version $Revision$
 * @author Ahmed Ashour
 */
public class XPathResultTest extends WebTestCase {

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void resultType() throws Exception {
        resultType("//div", "4");
        resultType("count(//div)", "1");
        resultType("count(//div)=2", "3");
    }

    private void resultType(final String expression, final String expectedAlert) throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
            + "    text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
            + "    text += '  <xsl:template match=\"/\">\\n';\n"
            + "    text += '  <html>\\n';\n"
            + "    text += '    <body>\\n';\n"
            + "    text += '      <div/>\\n';\n"
            + "    text += '      <div/>\\n';\n"
            + "    text += '    </body>\\n';\n"
            + "    text += '  </html>\\n';\n"
            + "    text += '  </xsl:template>\\n';\n"
            + "    text += '</xsl:stylesheet>';\n"
            + "    var parser=new DOMParser();\n"
            + "    var doc=parser.parseFromString(text,'text/xml');\n"
            + "    var result = doc.evaluate('" + expression + "', doc.documentElement, "
            + "null, XPathResult.ANY_TYPE, null);\n"
            + "    alert(result.resultType);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        final List<String> collectedAlerts = new ArrayList<String>();
        loadPage(BrowserVersion.FIREFOX_2, html, collectedAlerts);
        assertEquals(new String[] {expectedAlert}, collectedAlerts);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void snapshotType() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
            + "    text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
            + "    text += '  <xsl:template match=\"/\">\\n';\n"
            + "    text += '  <html>\\n';\n"
            + "    text += '    <body>\\n';\n"
            + "    text += '      <div id=\\'id1\\'/>\\n';\n"
            + "    text += '      <div id=\\'id2\\'/>\\n';\n"
            + "    text += '    </body>\\n';\n"
            + "    text += '  </html>\\n';\n"
            + "    text += '  </xsl:template>\\n';\n"
            + "    text += '</xsl:stylesheet>';\n"
            + "    var parser=new DOMParser();\n"
            + "    var doc=parser.parseFromString(text,'text/xml');\n"
            + "    var result = doc.evaluate('//div', doc.documentElement, null,"
            + " XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);\n"
            + "    alert(result.resultType);\n"
            + "    for (var i=0; i < result.snapshotLength; i++) {\n"
            + "      alert(result.snapshotItem(i).getAttribute('id'));\n"
            + "    }\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        final String[] expectedAlerts = {"7", "id1", "id2"};
        final List<String> collectedAlerts = new ArrayList<String>();
        loadPage(BrowserVersion.FIREFOX_2, html, collectedAlerts);
        assertEquals(expectedAlerts, collectedAlerts);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void singleNodeValue() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
            + "    text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
            + "    text += '  <xsl:template match=\"/\">\\n';\n"
            + "    text += '  <html>\\n';\n"
            + "    text += '    <body>\\n';\n"
            + "    text += '      <div id=\\'id1\\'/>\\n';\n"
            + "    text += '      <div id=\\'id2\\'/>\\n';\n"
            + "    text += '    </body>\\n';\n"
            + "    text += '  </html>\\n';\n"
            + "    text += '  </xsl:template>\\n';\n"
            + "    text += '</xsl:stylesheet>';\n"
            + "    var parser=new DOMParser();\n"
            + "    var doc=parser.parseFromString(text,'text/xml');\n"
            + "    var result = doc.evaluate('//div', doc.documentElement, null,"
            + " XPathResult.FIRST_ORDERED_NODE_TYPE, null);\n"
            + "    alert(result.resultType);\n"
            + "    alert(result.singleNodeValue.getAttribute('id'));\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        final String[] expectedAlerts = {"9", "id1"};
        final List<String> collectedAlerts = new ArrayList<String>();
        loadPage(BrowserVersion.FIREFOX_2, html, collectedAlerts);
        assertEquals(expectedAlerts, collectedAlerts);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void iterateNext() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var text='<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\\n';\n"
            + "    text += '<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://myNS\">\\n';\n"
            + "    text += '  <xsl:template match=\"/\">\\n';\n"
            + "    text += '  <html>\\n';\n"
            + "    text += '    <body>\\n';\n"
            + "    text += '      <div id=\"id1\"/>\\n';\n"
            + "    text += '      <div id=\"id2\"/>\\n';\n"
            + "    text += '    </body>\\n';\n"
            + "    text += '  </html>\\n';\n"
            + "    text += '  </xsl:template>\\n';\n"
            + "    text += '</xsl:stylesheet>';\n"
            + "    var parser=new DOMParser();\n"
            + "    var doc=parser.parseFromString(text,'text/xml');\n"
            + "    var result = doc.evaluate('" + "//div" + "', doc.documentElement, "
            + "null, XPathResult.ANY_TYPE, null);\n"
            + "    \n"
            + "    var thisNode = result.iterateNext();\n"
            + "    while (thisNode) {\n"
            + "      alert(thisNode.getAttribute('id'));\n"
            + "      thisNode = result.iterateNext();\n"
            + "    }\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        final String[] expectedAlerts = {"id1", "id2"};
        final List<String> collectedAlerts = new ArrayList<String>();
        loadPage(BrowserVersion.FIREFOX_2, html, collectedAlerts);
        assertEquals(expectedAlerts, collectedAlerts);
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    public void notOr() throws Exception {
        final String html = "<html><head><title>foo</title><script>\n"
            + "  function test() {\n"
            + "    var expression = \".//*[@id='level1']/*[not(preceding-sibling::* or following-sibling::*)]\";\n"
            + "    var result = document.evaluate(expression, document, null, "
            + "XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);\n"
            + "    alert(result.resultType);\n"
            + "  }\n"
            + "</script></head><body onload='test()'>\n"
            + "</body></html>";

        final String[] expectedAlerts = {"7"};
        final List<String> collectedAlerts = new ArrayList<String>();
        loadPage(BrowserVersion.FIREFOX_2, html, collectedAlerts);
        assertEquals(expectedAlerts, collectedAlerts);
    }
}
