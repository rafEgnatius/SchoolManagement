<%-- 
    Document   : LECTOR confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">czy zatwierdzić wypożyczenie?</th>
        </tr>
        <tr>
            <th>Lektor:</th>
            <td>${lektor.nazwa}</td>
        </tr>
        <tr>
            <th>Podręcznik</th>
            <td>${podrecznik.nazwa} (id: ${podrecznik.id})</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="metodyka">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="wypozyczPodrecznikZBiblioteki?lektorId=${lektor.id}&podrecznikId=${podrecznik.id}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>



</section>
