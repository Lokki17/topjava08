<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">ru<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a onclick="show('en')">English</a></li>
        <li><a onclick="show('ru')">Русский</a></li>
    </ul>
</li>

<script type="text/javascript">
    function show(val) {
        window.location.href = window.location.href.split('?')[0] + '?language=' + val;
    }
</script>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
