<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" 
indent="no" encoding="utf-8"
doctype-public = "-//W3C//DTD HTML 4.01 Transitional//EN"
doctype-system = "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd" />

<xsl:template name="contact" match="/person/contacts">

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Мобильный:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="*/cellphone"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Домашний:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="*/homephone"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				ICQ:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="*/icq"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Skype:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="*/skype"/>
		</td>
	</tr>
		<tr >
		<td height="30" width="40%" valign="top" align="left">
				Email:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="*/email"/>
		</td>
	</tr>
	
	
	<xsl:if test="*/adress">
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Адрес:
		</td>
		 <td width="60%" valign="top" align="left">
			 гор. <xsl:value-of select="*/adress/city"/>,
			 ул. <xsl:value-of select="*/adress/street"/>,
			 д. <xsl:value-of select="*/adress/home"/>,
			 корп. <xsl:value-of select="*/adress/building"/>,
			 кв. <xsl:value-of select="*/adress/flat"/>
		</td>
	</tr>
	</xsl:if>
	
</table>


</xsl:template>
</xsl:stylesheet>