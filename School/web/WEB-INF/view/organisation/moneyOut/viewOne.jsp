<%-- 
    Document   : MONEYOUT viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty wyplata}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">wypłata: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${wyplata.id}</td>
            </tr>
            <tr>
                <th>Lektor: </th>
                <td>${lektor.nazwa}</td>
            </tr>
            <tr>
                <th>Data: </th>
                <td>${wyplata.data}</td>
            </tr>
            <tr>
                <th>Kwota: </th>
                <td>${wyplata.kwota} PLN</td>
            </tr>
            <tr>
                <th>Opis: </th>
                <td>${wyplata.opis}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujWyplate?${wyplata.id}">
            <span class="smallButtonText">edytuj dane wypłaty &#x279f;</span>
        </a>

    </section>
</c:if>