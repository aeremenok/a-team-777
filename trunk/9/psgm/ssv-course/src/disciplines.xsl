<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/TR/xhtml1/strict">

    <xsl:output method="html"/>
    				
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="disciplines">
        <xsl:for-each select="discipline">
            <xsl:sort select="./@name" />
            
            <hr />
            
            <table width="100%">
                <tr width="100%" bgcolor="gray">
                    <td colspan="2">
                        <h3>Краткое название предмета: <xsl:value-of select="./@name" /></h3>
                        <h4>Полное название предмета: <xsl:value-of select="./@fname" /></h4>
                    </td>
                </tr>

                <tr bgcolor="green">
                    <td width="30%" align="center"><b>Лектор</b></td>
                    <td align="center"><b>Отчётность</b></td>
                </tr>

                <tr>
                    <td align="center"><xsl:value-of select="./lector" /></td>
                    <td align="center">
                        <xsl:if test="./labs&gt;0">
                            Лабораторные работы<br />
                        </xsl:if>
                        
                        <xsl:if test="./course&gt;0">
                            Курсовая работа<br />
                        </xsl:if>
                        
                        <xsl:if test="./exam&gt;0">
                            <b>Экзамен</b><br />
                        </xsl:if>
                        
                        <br />
                    </td>
                </tr>
                
                <tr bgcolor="green">
                    <td width="30%" align="center"><b>Лектор</b></td>
                    <td align="center"><b>Отчётность</b></td>
                </tr>
            </table>
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>