<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : articles.xsl
    Created on : 25 Май 2008 г., 15:39
    Author     : eav
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="site">
        <xsl:for-each select="articles">
            <xsl:for-each select="article">
                <hr />
                <table width="100%">
                    <tr width="100%" bgcolor="gray">
                        <td colspan="2">
                            <h3>Статья:
                                <xsl:value-of select="@title" />
                            </h3>
                        </td>
                    </tr>
                    <tr width="100%" bgcolor="gray">
                        <td>
                            <h4>Дата создания:
                                <xsl:value-of select="@created" />
                            </h4>
                        </td>
                        <td>
                            <h4>Автор:
                                <xsl:value-of select="@author" />
                            </h4>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <p>
                                <xsl:value-of select="./content"/>
                            </p>
                        </td>
                    </tr>
                </table>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
