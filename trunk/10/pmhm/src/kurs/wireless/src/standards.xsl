<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : articles.xsl
    Created on : 25 Май 2008 г., 15:39
    Author     : eav
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="site">
        <xsl:for-each select="standards">
            <xsl:for-each select="standard">
                <hr />
                <table width="100%">
                    <tr width="100%" bgcolor="opal">
                        <td width="80%">
                            <h3>Стандарт:
                                <xsl:value-of select="@name" />
                            </h3>
                        </td>
                        <td>
                            <h4>Дата создания:
                                <xsl:value-of select="@created" />
                            </h4>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <xsl:value-of select="./description"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <xsl:for-each select="impl">
                                <table width="90%" border="1">
                                    <tr>
                                        <td width="50%">
                                            Реализация:
                                            <xsl:value-of select="@name" />
                                        </td>
                                        <td width="50%">
                                            Дата создания:
                                            <xsl:value-of select="@created" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            Средняя скорость передачи: <xsl:value-of select="@speed"/>
                                            <img>
                                                <xsl:attribute name="src">
                                                    <xsl:choose>
                                                        <xsl:when test="@speed&gt;10000">images\fast.jpg</xsl:when>
                                                        <xsl:otherwise>images\slow.gif</xsl:otherwise>
                                                    </xsl:choose>
                                                </xsl:attribute>
                                            </img>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <xsl:value-of select="./description"/>
                                        </td>
                                    </tr>
                                </table>
                            </xsl:for-each>
                        </td>
                    </tr>
                </table>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
