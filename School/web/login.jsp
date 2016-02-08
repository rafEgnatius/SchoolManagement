<%-- 
    Document   : login
    Created on : 27-Jan-2016, 19:43:27
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">
    <form action="j_security_check" method=post>
        <div id="loginBox">
            <p><strong>użytkownik:</strong>
                <input type="text" size="20" name="j_username"></p>

            <p><strong>hasło:</strong>
                <input type="password" size="20" name="j_password"></p>

            <p><input type="submit" value="submit"></p>
        </div>
    </form>
</section>