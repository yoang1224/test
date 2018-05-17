<%@ page pageEncoding="euc-kr" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:form action="checkbox">
   <s:checkbox name="male" value="true" label="남성"/>
   <s:checkbox name="female" value="false" label="여성"/>
   <s:submit/>
</s:form>