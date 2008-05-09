<?xml version="1.0" encoding="windows-1251"?>

<!--
    Document   : eav.xsl
    Created on : 9 Май 2008 г., 21:58
    Author     : eav
    Description:
        Purpose of transformation follows.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="autobiography">
        <html>
            <head>
                <title>Автобиография</title>
            </head>
            <body>
                <h1 align="center">Автобиография
                </h1>
                <p>Я,
                    <xsl:value-of select="author/credentials/@fullName"/>,
                    родился
                    <xsl:value-of select="author/credentials/@birthDate"/>
                    в городе
                    <xsl:value-of select="author/credentials/@birthPlace"/>e
                </p>
                <p>
                    <xsl:value-of select="author/school/@startDate"/>
                    поступил в школу №
                    <xsl:value-of select="author/school/@number"/>
                    города
                    <xsl:value-of select="author/school/@city"/>a,
                    которую окончил 
                    <xsl:value-of select="author/school/@endDate"/>
                </p>
                <p>
                    <xsl:value-of select="author/highSchool/@startDate"/>
                    поступил на первый курс 
                    <xsl:value-of select="author/highSchool/@schoolName"/>
                    по специальности 
                    <xsl:value-of select="author/highSchool/@profession"/>
                </p>
                <p>Адрес постоянной регистрации: г.
                <xsl:value-of select="author/address/@city"/>,
                <xsl:value-of select="author/address/@district"/>
                район, ул. 
                <xsl:value-of select="author/address/@street"/>
                , д.
                <xsl:value-of select="author/address/@house"/>
                , кв.
                <xsl:value-of select="author/address/@appartment"/>
                </p>
                <h2 align="center">Сведения о ближайших родственниках</h2>
                <xsl:for-each select="relatives/relative">
                <p>
                    <b><xsl:value-of select="@status"/></b>
                    : 
                    <xsl:value-of select="credentials/@fullName"/>
                    , родился(ась) 
                    <xsl:value-of select="credentials/@birthDate"/>
                    в 
                    <xsl:value-of select="credentials/@birthPlace"/>
                </p>
                <br/>
                </xsl:for-each>
                <p><xsl:value-of select="legal/@court" /></p>
                <p><xsl:value-of select="legal/@abroad" /></p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>

