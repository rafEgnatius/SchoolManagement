<%-- 
    Document   : LANGUAGE TEST confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać test w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
        </tr>
        <tr>
            <th>rodzaj: </th>
            <td>${param.rodzaj}</td>
        </tr>
        <tr>
            <th>ocena: </th>
            <td>${param.ocena}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "pokazKurs?" : "edytujTest?"}${id eq "-1" ? param.kursId : param.id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajTestZapisz?id=${id}&kursId=${param.kursId}&kursantId=${param.kursantId}&rodzaj=${param.rodzaj}&ocena=${param.ocena}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>

</section>
