<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : test.xsl
    Created on : 25 Май 2008 г., 17:16
    Author     : eav
    Description:
        Purpose of transformation follows.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="site">
        <html>
            <head>
                <title>test.xsl</title>
            </head>
            <body>
                <xsl:for-each select="articles">
                    <xsl:for-each select="article">
                        <p>
                            <xsl:value-of select="@title" />
                        </p>
                    </xsl:for-each>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
