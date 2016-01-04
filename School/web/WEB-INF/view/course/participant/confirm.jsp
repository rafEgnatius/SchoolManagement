<%-- 
    Document   : PARTICIPANT confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nowego kursanta w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
        </tr>
        <tr>
            <th>imię i nazwisko: </th>
            <td>${param.nazwa}</td>
        </tr>
        <tr>
            <th>telefon: </th>
            <td>${param.telefon}</td>
        </tr>
        <tr>
            <th>email: </th>
            <td>${param.email}</td>
        </tr>
        <tr>
            <th>firma: </th>
            <td>${firma.nazwa}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "kursanci" : "edytujKursanta?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajKursantaZapisz?id=${id}&nazwa=${param.nazwa}&telefon=${param.telefon}&email=${param.email}&umowa=${param.umowa}&firmaId=${firma.id}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>



</section>
