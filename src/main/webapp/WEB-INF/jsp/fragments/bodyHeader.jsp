<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="meals" class="navbar-brand"><fmt:message key="app.title"/></a>

        <div class="collapse navbar-collapse">
            <form class="navbar-form navbar-right">
                <a class="btn btn-info" role="button" href="users"><fmt:message key="users.title"/></a>
                <a class="btn btn-primary" role="button" href="logout"><fmt:message key="app.logout"/></a>
            </form>
        </div>
    </div>
    <script type="text/javascript">
        var i18n = [];
        <c:forEach var='key' items='<%=new String[]{"common.update","common.delete","common.deleted","common.saved","common.enabled","common.disabled","common.failed"}%>'>
        i18n['${key}'] = '<fmt:message key="${key}"/>';
        </c:forEach>
        var edit_title = '<fmt:message key="meals.edit"/>';
    </script>
</div>