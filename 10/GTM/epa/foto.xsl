<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" 
indent="no" encoding="utf-8"
doctype-public = "-//W3C//DTD HTML 4.01 Transitional//EN"
doctype-system = "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd" />

<xsl:template name="foto" match="/person/fotos">

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<th >
				Фотографии:
		</th>		
	</tr>
	<tr>
		<td>
	
	
	<xsl:if test="count(*/foto) = 0">
		<strong> Не заданы </strong>
	</xsl:if>
	
	
	<xsl:if test="count(*/foto) > 0" >
		<table cellpadding="10" cellspacing="10" border="0" width="100%" >
	
				
	
				<xsl:for-each select="*/foto">
				
					<xsl:if test="position() mod 3 = 1">
						<xsl:text disable-output-escaping="yes">&lt;tr&gt;</xsl:text>
					</xsl:if>


					<td>					
						<xsl:element name="img">
							<xsl:attribute name="src"><xsl:value-of select="@path"/></xsl:attribute>
							<xsl:attribute name="alt">Фотография</xsl:attribute>
							<xsl:attribute name="id">pic<xsl:value-of select="@id"/></xsl:attribute>
						</xsl:element>
					<br/>
					<small>
						<xsl:value-of select="@comment"/>
					</small>					
					</td>


					<xsl:if test="position() mod 3 = 0">
						<xsl:text disable-output-escaping="yes"> &lt;/tr&gt; </xsl:text>
					</xsl:if>
					
				</xsl:for-each>
				
		<xsl:if test="count(*/foto) mod 3 != 0">
			<xsl:text disable-output-escaping="yes"> &lt;/tr&gt; </xsl:text>
		</xsl:if>
	</table>
			
			
		<strong>
			<xsl:text>Всего:</xsl:text>
			<xsl:value-of select="count(*/foto) "/>
		</strong>
	</xsl:if>

	</td>
	</tr>
	
</table>


</xsl:template>
</xsl:stylesheet>