<%-- 
    Document   : MONEYOUT confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nową wypłatę w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
            <th colspan="2"></th>
        </tr>
        <tr>
            <th>Data: </th>
            <td>${data}</td>
        </tr>
        <tr>
            <th>Kwota: </th>
            <td>${kwota}</td>
        </tr>
        <tr>
            <th>Opis: </th>
            <td>${opis}</td>
        </tr>
        <tr>
            <th>Lektor: </th>
            <td>${lektor.nazwa}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "wyplaty" : "edytujWyplate?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajWyplateZapisz?id=${id}&data=${data}&kwota=${kwota}&opis=${opis}&lektorId=${lektor.id}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>
    
</section>
