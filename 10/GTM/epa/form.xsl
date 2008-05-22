<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" 
indent="no" encoding="utf-8"
doctype-public = "-//W3C//DTD HTML 4.01 Transitional//EN"
doctype-system = "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd" />

<xsl:template name="form" match="/person">

<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<th >
				Редактирование данных:
		</th>		
	</tr>
	<tr>
		<td>
	
		<form action="edit.php" method="post">

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
									 <input type="text" name="e_name" value="{name}" style="width:300px"/>
							</td>
						</tr>
						<tr >
							<td height="30" width="40%" valign="top" align="left">
									Фамилия:
							</td>
							 <td width="60%" valign="top" align="left">
									<input type="text" name="e_sname" value="{surname}" style="width:300px"/>
							</td>
						</tr>
						<tr >
							<td height="30" width="40%" valign="top" align="left">
									Дата рождения:
							</td>
							 <td width="60%" valign="top" align="left">
									<input type="text" name="e_birth" value="{birthdate}" style="width:300px"/>
							</td>
						</tr>

						<tr >
							<td height="30" width="40%" valign="top" align="left">
									О себе:
							</td>
							 <td width="60%" valign="top" align="left">
								 <textarea rows="10" cols="30" style="width:300px" name="e_about">
									<xsl:value-of select="about"/>
								 </textarea>
							</td>
						</tr>
						
						
						<tr>
							<td>Получать рекламные письма:</td>
							<td><input type="checkbox" name="promo"/>
							</td>
						</tr>
						
						<tr>
							<td>Пароль(для подтверждения)</td>
							<td><input type="password" name="pass"></input>
							</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" value="{id}"/>
								<input type="submit" value="Изменить"></input>

								<br/>
								<input type="reset" value="Отменить"></input>
							</td>
						</tr>
					</table>


		
		
		
		
		</form>
	
	</td>
	</tr>
	
</table>


</xsl:template>
</xsl:stylesheet>