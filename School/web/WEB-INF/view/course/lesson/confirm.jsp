<%-- 
    Document   : LESSON confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać lekcję w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
        </tr>
        <tr>
            <th>data: </th>
            <td>${param.data}</td>
        </tr>
        <tr>
            <th>odwołana: </th>
            <td>${odwolana ? "tak" : "nie"}</td>
        </tr>
        <tr>
            <th>kurs: </th>
            <td>${kurs.symbol}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "pokazKurs?" : "edytujLekcje?"}${id eq "-1" ? kurs.id : param.id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajLekcjeZapisz?id=${id}&kursId=${kurs.id}&data=${param.data}&odwolana=${odwolana}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>

</section>
