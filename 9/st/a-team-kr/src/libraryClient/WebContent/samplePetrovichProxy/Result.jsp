<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="samplePetrovichProxyid" scope="session" class="org.example.www.petrovich.PetrovichProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
samplePetrovichProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = samplePetrovichProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
        java.lang.String endpoint_0idTemp  = endpoint_0id;
        samplePetrovichProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        org.example.www.petrovich.Petrovich_PortType getPetrovich_PortType10mtemp = samplePetrovichProxyid.getPetrovich_PortType();
if(getPetrovich_PortType10mtemp == null){
%>
<%=getPetrovich_PortType10mtemp %>
<%
}else{
        if(getPetrovich_PortType10mtemp!= null){
        String tempreturnp11 = getPetrovich_PortType10mtemp.toString();
        %>
        <%=tempreturnp11%>
        <%
        }}
break;
case 13:
        gotMethod = true;
        String in_1id=  request.getParameter("in16");
        java.lang.String in_1idTemp  = in_1id;
        java.lang.String doSomething13mtemp = samplePetrovichProxyid.doSomething(in_1idTemp);
if(doSomething13mtemp == null){
%>
<%=doSomething13mtemp %>
<%
}else{
        String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(doSomething13mtemp));
        %>
        <%= tempResultreturnp14 %>
        <%
}
break;
case 18:
        gotMethod = true;
        String in_2id=  request.getParameter("in21");
        java.lang.String in_2idTemp  = in_2id;
        java.lang.String punish18mtemp = samplePetrovichProxyid.punish(in_2idTemp);
if(punish18mtemp == null){
%>
<%=punish18mtemp %>
<%
}else{
        String tempResultreturnp19 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(punish18mtemp));
        %>
        <%= tempResultreturnp19 %>
        <%
}
break;
case 23:
        gotMethod = true;
        String in_3id=  request.getParameter("in26");
        java.lang.String in_3idTemp  = in_3id;
        java.lang.String reward23mtemp = samplePetrovichProxyid.reward(in_3idTemp);
if(reward23mtemp == null){
%>
<%=reward23mtemp %>
<%
}else{
        String tempResultreturnp24 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(reward23mtemp));
        %>
        <%= tempResultreturnp24 %>
        <%
}
break;
}
} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>