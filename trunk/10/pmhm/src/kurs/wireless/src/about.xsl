<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : articles.xsl
    Created on : 25 Май 2008 г., 15:39
    Author     : eav
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="site">
        <xsl:for-each select="about">
            <h1 align="center">О сайте</h1>
            <h2 align="center">Создатели</h2>
            <xsl:for-each select="author">
                <p>
                    <xsl:value-of select="@first_name"/>
                    <xsl:text>&#160;</xsl:text>
                    <xsl:value-of select="@last_name"/>
                </p>
                <p>Родился
                    <xsl:value-of select="@birth"/> в <xsl:value-of select="@place"/>e
                </p>
                <xsl:for-each select="school">
                    <p>Окончил школу
                        <xsl:value-of select="@number"/>(
                        <xsl:value-of select="./description"/>)
                    </p>
                </xsl:for-each>
                <xsl:for-each select="university">
                    <p>C
                        <xsl:value-of select="@accepted"/> по
                        <xsl:value-of select="@graduated"/> учился в
                        <xsl:value-of select="@name"/>
                        (
                        <xsl:value-of select="./description"/>)
                    </p>
                </xsl:for-each>
            </xsl:for-each>
            <div style="background-color:gray">
                <p align="center">
                    <xsl:value-of select="./description"/>
                </p>
            </div>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
