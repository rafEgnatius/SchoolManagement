<%-- 
    Document   : COURSE confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nowy kurs w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
        </tr>
        <tr>
            <th>Symbol: </th>
            <td>${param.symbol}</td>
        </tr>
        <tr>
            <th>Język: </th>
            <td>${param.jezykId}</td>
        </tr>
        <tr>
            <th>Opis: </th>
            <td>${param.opis}</td>
        </tr>
        <tr>
            <th>Rok: </th>
            <td>${param.rok}</td>
        </tr>
        <tr>
            <th>Semestr: </th>
            <td>${param.semestr}</td>
        </tr>
        <tr>
            <th>Sala: </th>
            <td>${param.sala}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "kursy" : "edytujKurs?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajKursZapisz?id=${id}&symbol=${param.symbol}&jezykId=${param.jezykId}&opis=${param.opis}&rok=${param.rok}&semestr=${param.semestr}&sala=${param.sala}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>



</section>
