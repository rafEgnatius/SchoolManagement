<%-- 
    Document   : LANGUAGE confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nowy język w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
            <th colspan="2"></th>
        </tr>
        <tr>
            <th>Język: </th>
            <td>${param.nazwa}</td>
        </tr>
        <tr>
            <th>Symbol: </th>
            <td>${param.symbol}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "jezyki" : "edytujJezyk?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajJezykZapisz?id=${id}&nazwa=${param.nazwa}&symbol=${param.symbol}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>
    
</section>
