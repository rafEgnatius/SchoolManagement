<%-- 
    Document   : BILL viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty mainEntity}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">rachunek: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${mainEntity.id}</td>
            </tr>
            <tr>
                <th>Lektor: </th>
                <td>${lektor.nazwa}</td>
            </tr>
            <tr>
                <th>Numer: </th>
                <td>${mainEntity.numer}</td>
            </tr>
            <tr>
                <th>Data: </th>
                <td>${mainEntity.data}</td>
            </tr>
            <tr>
                <th>Kwota: </th>
                <td>${mainEntity.kwota} PLN</td>
            </tr>
            <tr>
                <th>Opis: </th>
                <td>${mainEntity.opis}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujRachunek?${mainEntity.id}">
            <span class="smallButtonText">edytuj dane rachunku &#x279f;</span>
        </a>

    </section>
</c:if>