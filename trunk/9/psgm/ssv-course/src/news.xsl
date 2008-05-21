<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/TR/xhtml1/strict">

    <xsl:output method="html"/>
    				
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="news">
        <xsl:for-each select="new">
            <hr />
            <table width="100%">
            <tr width="100%" bgcolor="gray"><td align="left"><h1><xsl:value-of select="./title" /></h1></td></tr>
            <tr>
                <td align="justify">
                    <xsl:for-each select="./body/para">
                        <xsl:value-of select="." />
                        <br />
                    </xsl:for-each>
                </td>
            </tr>
    
            </table>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>