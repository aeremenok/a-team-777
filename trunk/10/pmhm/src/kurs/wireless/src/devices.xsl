<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : articles.xsl
    Created on : 25 Май 2008 г., 15:39
    Author     : eav
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="site">
        <xsl:for-each select="devices">
            <xsl:for-each select="device">
                <hr />
                <table width="100%">
                    <tr width="100%" bgcolor="opal">
                        <td width="80%">
                            <h3>Стандарт:
                                <xsl:value-of select="@name" />
                            </h3>
                        </td>
                        <td>
                            <h4>Производитель:
                                <xsl:value-of select="@vendor" />
                            </h4>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        Реализует:
                                    </td>
                                    <xsl:for-each select="implements">
                                        <td>
                                            <xsl:value-of select="@name" />
                                        </td>
                                    </xsl:for-each>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <xsl:value-of select="./description"/>
                        </td>
                    </tr>
                </table>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
