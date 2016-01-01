<%-- 
    Document   : INVOICE confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nową fakturę w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
            <th colspan="2"></th>
        </tr>
        <tr>
            <th>Faktura nr: </th>
            <td>${numer}</td>
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
            <th>Firma: </th>
            <td>${firma.nazwa}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "faktury" : "edytujFakture?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajFaktureZapisz?id=${id}&numer=${numer}&data=${data}&kwota=${kwota}&opis=${opis}&firmaId=${firma.id}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>
    
</section>
