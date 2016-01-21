<%-- 
    Document   : LANGUAGE TEST viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty test}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">test: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${test.id}</td>
            </tr>
            <tr>
                <th>Kurs:</th>
                <td>${test.kurs.symbol}</td>
            </tr>
            <tr>
                <th>Kursant:</th>
                <td>${test.kursant.nazwa}</td>
            </tr>
            <tr>
                <th>Rodzaj testu:</th>
                <td>${test.rodzaj}</td>
            </tr>
            <tr>
                <th>Ocena:</th>
                <td>${test.ocena}</td>
            </tr>
            
        </table>

        <a  class="smallButton" href="edytujTest?${test.id}">
            <span class="smallButtonText">popraw informacje o teście &#x279f;</span>
        </a>

    </section>
</c:if>