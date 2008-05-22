<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" 
indent="no" encoding="utf-8"
doctype-public = "-//W3C//DTD HTML 4.01 Transitional//EN"
doctype-system = "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd" />

<xsl:template name="common" match="/person" >

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Номер:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="id"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Имя:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="name"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Фамилия:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="surname"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Дата рождения:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="birthdate"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				Дата регистрации:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="regdate"/>
		</td>
	</tr>
	<tr >
		<td height="30" width="40%" valign="top" align="left">
				О себе:
		</td>
		 <td width="60%" valign="top" align="left">
				<xsl:value-of select="about"/>
		</td>
	</tr>
</table>

</xsl:template>
</xsl:stylesheet>