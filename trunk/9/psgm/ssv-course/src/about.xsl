<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/TR/xhtml1/strict">

    <xsl:output method="html"/>
    				
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="about">
        
        <table  width="100%">
            <tr align="center">
                <td><h1>Разработчик сайта:</h1></td>
            </tr>
            <tr align="center">
                <td>
                    <img>
                        <xsl:attribute name="src"><xsl:value-of select="./photo" /></xsl:attribute>
                    </img>
                </td>
            </tr>
            <tr align="center">
                <td>
                    <h2><xsl:value-of select="./name" /></h2>
                </td>
            </tr>
            <tr align="center">
                <td>
                    <xsl:value-of select="./description" />
                </td>
            </tr>
        </table>
        
    </xsl:template>
    
</xsl:stylesheet>