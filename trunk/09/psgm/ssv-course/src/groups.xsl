<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/TR/xhtml1/strict">

    <xsl:output method="html"/>
    				
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="groups">
        <xsl:for-each select="group">
            <xsl:sort select="./@name" />
            <hr />
            <table width="100%">
            <tr width="100%" bgcolor="gray"><td colspan="4"><h1>Группа <xsl:value-of select="./@name" /></h1></td></tr>
                <tr bgcolor="green">
                    <td width="10%" align="center"><b>Пол</b></td>
                    <td width="25%" align="center"><b>Имя</b></td>
                    <td width="50%" align="center"><b>Связь</b></td>
                    <td align="center"><b>День Рождения</b></td>
                </tr>

                <xsl:for-each select="./student">
                    <xsl:sort select="./@name" />
                        <tr>
                            <td align="center">
                                <img>
                                    <xsl:attribute name="src">
                                        <xsl:choose>
                                            <xsl:when test="male&gt;0">pics\boy.png</xsl:when>
                                            <xsl:otherwise>pics\girl.png</xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:attribute>
                                </img>
                            </td>
                            
                            <td align="center">
                                <xsl:value-of select="./@name"/><br />
                            </td>
                            
                            <td align="left">
                                <b>Почта: </b>
                                <a>
                                    <xsl:attribute name="href">
                                        mailto:<xsl:value-of select="./@mail"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="./@mail"/>
                                </a>
                                <br />
                                
                                <xsl:if test="./icq">
                                    <b>Номер ICQ: </b>
                                    <xsl:value-of select="./icq"/>
                                    <br />
                                </xsl:if>
                                
                                <xsl:if test="./skype">
                                    <b>Имя в Skype: </b>
                                    <xsl:value-of select="./skype"/>
                                    <br />
                                </xsl:if>

                                <xsl:if test="./jabber">
                                    <b>Jabber-аккаунт: </b>
                                    <xsl:value-of select="./jabber"/>
                                    <br />
                                </xsl:if>
                                
                                <br />
                            </td>

                            <td align="center">
                                <xsl:if test="./birthdate">
                                    <img src="pics\birth.png" />
                                    <xsl:value-of select="./birthdate"/>
                                    <img src="pics\birth.png" />
                                    <br />
                                </xsl:if>
                            </td>
                        </tr>
                    
                </xsl:for-each>

                <tr bgcolor="green">
                    <td width="10%" align="center"><b>Пол</b></td>
                    <td width="25%" align="center"><b>Имя</b></td>
                    <td width="50%" align="center"><b>Связь</b></td>
                    <td align="center"><b>День Рождения</b></td>
                </tr>
            </table>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>