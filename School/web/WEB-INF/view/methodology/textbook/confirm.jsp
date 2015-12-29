<%-- 
    Document   : TEXTBOOK confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nowy podręcznik w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
        </tr>
        <tr>
            <th>Podręcznik:</th>
            <td>${param.nazwa}</td>
        </tr>
        <tr>
            <th>Poziom</th>
            <td>${param.poziom}</td>
        </tr>
        <tr>
            <th>Język</th>
            <td>${jezyk.nazwa}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "podreczniki" : "edytujPodrecznik?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajPodrecznikZapisz?id=${id}&nazwa=${param.nazwa}&poziom=${param.poziom}&jezykId=${jezyk.id}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>



</section>
