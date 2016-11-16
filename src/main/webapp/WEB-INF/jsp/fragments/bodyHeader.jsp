<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="mvc" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="meals" class="navbar-brand"><spring:message code="app.title"/></a>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                <form:form class="navbar-form navbar-right" action="logout" method="post">
                    <sec:authorize access="isAuthenticated()">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <a class="btn btn-info" role="button" href="users"><fmt:message key="users.title"/></a>
                        </sec:authorize>
                        <a class="btn btn-info" role="button" href="profile">${userTo.name} <spring:message
                                code="app.profile"/></a>
                        <input type="submit" class="btn btn-primary" value="<spring:message code="app.logout"/>">
                    </sec:authorize>
                </form:form>
                </li>
                <jsp:include page="language.jsp"/>
<%--                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}<b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a onclick="show('en')">English</a></li>
                        <li><a onclick="show('ru')">Русский</a></li>
                    </ul>
                </li>--%>
            </ul>
        </div>
    </div>
</div>

<%--<script type="text/javascript">
    function show(val) {
        window.location.href = window.location.href.split('?')[0] + '?language=' + val;
    }
</script>--%>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>