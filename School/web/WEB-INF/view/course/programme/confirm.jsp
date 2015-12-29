<%-- 
    Document   : PROGRAMME confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nowy program w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
            <th colspan="2"></th>
        </tr>
        <tr>
            <th>Metoda: </th>
            <td>${metoda}</td>
        </tr>
        <tr>
            <th>referencja: </th>
            <td>${param.referencja}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "programy" : "edytujProgram?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajProgramZapisz?id=${id}&metoda=${metoda}&referencja=${param.referencja}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>
    
</section>
