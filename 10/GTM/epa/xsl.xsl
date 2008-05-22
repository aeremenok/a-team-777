<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:import href="top.xsl"/>
	
	<xsl:import href="common.xsl"/>
	<xsl:import href="contact.xsl"/>
	<xsl:import href="hobby.xsl"/>
	<xsl:import href="foto.xsl"/>
	<xsl:import href="form.xsl"/>
	
	
	<xsl:output method="html"/>
	<!-- indent="no" encoding="UTF-8" doctype-public = "-//W3C//DTD HTML 4.01 Transitional//EN" doctype-system = "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd" />
-->
	<xsl:template match="/person">
		<html>
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
				<meta http-equiv="content-language" content="en"/>
				<title>Инфо</title>
			</head>
			<body bgcolor="#FFFFFF">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" bgcolor="#FFFFFF">
					<tr>
						<td>
							<xsl:call-template name="links"/>
						</td>
					</tr>
					<tr>
						<td>
							<!--BODY-->
							<xsl:choose>
								<xsl:when test="portalid = 1 "><xsl:call-template name="contact"/></xsl:when>
								<xsl:when test="portalid = 2 "><xsl:call-template name="hobby"/></xsl:when>
								<xsl:when test="portalid = 3 "><xsl:call-template name="foto"/></xsl:when>
								<xsl:when test="portalid = 4 "><xsl:call-template name="form"/></xsl:when>
								<xsl:otherwise><xsl:call-template name="common" /></xsl:otherwise>
							</xsl:choose>

						</td>
					</tr>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
