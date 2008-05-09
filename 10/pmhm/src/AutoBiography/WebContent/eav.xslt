<?xml version="1.0" encoding="windows-1251"?>

<!--
    Document   : eav.xsl
    Created on : 9 ��� 2008 �., 21:58
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
                <title>�������������</title>
            </head>
            <body>
                <h1 align="center">�������������
                </h1>
                <p>�,
                    <xsl:value-of select="author/credentials/@fullName"/>,
                    �������
                    <xsl:value-of select="author/credentials/@birthDate"/>
                    � ������
                    <xsl:value-of select="author/credentials/@birthPlace"/>e
                </p>
                <p>
                    <xsl:value-of select="author/school/@startDate"/>
                    �������� � ����� �
                    <xsl:value-of select="author/school/@number"/>
                    ������
                    <xsl:value-of select="author/school/@city"/>a,
                    ������� ������� 
                    <xsl:value-of select="author/school/@endDate"/>
                </p>
                <p>
                    <xsl:value-of select="author/highSchool/@startDate"/>
                    �������� �� ������ ���� 
                    <xsl:value-of select="author/highSchool/@schoolName"/>
                    �� ������������� 
                    <xsl:value-of select="author/highSchool/@profession"/>
                </p>
                <p>����� ���������� �����������: �.
                <xsl:value-of select="author/address/@city"/>,
                <xsl:value-of select="author/address/@district"/>
                �����, ��. 
                <xsl:value-of select="author/address/@street"/>
                , �.
                <xsl:value-of select="author/address/@house"/>
                , ��.
                <xsl:value-of select="author/address/@appartment"/>
                </p>
                <h2 align="center">�������� � ��������� �������������</h2>
                <xsl:for-each select="relatives/relative">
                <p>
                    <b><xsl:value-of select="@status"/></b>
                    : 
                    <xsl:value-of select="credentials/@fullName"/>
                    , �������(���) 
                    <xsl:value-of select="credentials/@birthDate"/>
                    � 
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

