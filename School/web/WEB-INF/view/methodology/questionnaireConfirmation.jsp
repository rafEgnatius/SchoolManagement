<%-- 
    Document   : index
    Created on : 01-Dec-2015, 19:37:57
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-confirmation">

    <c:if test="${isAlreadyThere eq 'false'}">
        <h3>dziękujemy</h3>
        <p>ankieta została zapisana</p>
    </c:if>
    <c:if test="${isAlreadyThere eq 'true'}">
        <h3>ankieta znajduje się już w systemie</h3>
    </c:if>

</section>
