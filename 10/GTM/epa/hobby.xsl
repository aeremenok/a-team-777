<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" 
indent="no" encoding="utf-8"
doctype-public = "-//W3C//DTD HTML 4.01 Transitional//EN"
doctype-system = "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd" />

<xsl:template name="hobby" match="/person/hobbies">

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<th >
				Хобби:
		</th>		
	</tr>
	<tr>
		<td>
	
	
	<xsl:if test="count(*/hobby) = 0">
		<strong> Не заданы </strong>
	</xsl:if>
	
	
	<xsl:if test="count(*/hobby) > 0" >

			<ul>
				<xsl:for-each select="*/hobby">
					<li><xsl:value-of select="."/></li>
				</xsl:for-each>
			</ul>
		<strong>
			<xsl:text>Всего:</xsl:text>
			<xsl:value-of select="count(*/hobby) "/>
		</strong>
	</xsl:if>

	</td>
	</tr>
	
</table>


</xsl:template>
</xsl:stylesheet>