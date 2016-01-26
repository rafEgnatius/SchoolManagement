<%-- 
    Document   : methodology confirmation
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-confirmation">

    <table class="cancelSaveTable">
        <tr>
            <td class="textInCellsOnLeft">
                <h3>dziękujemy</h3>
            </td>
        </tr>
        <tr>
            <td class="textInCellsOnLeft">
                <p>zmiany zostały zapisane</p>
            </td>
        </tr>
        <tr>
            <td>
                <c:if test="${!empty lektorId}">
                    <a href="pokazLektora?${lektorId}" class="smallButton"><span class="smallButtonText">&#x21A9; wróć do lektora</span></a>
                </c:if>
                <c:if test="${!empty podrecznikId}">
                    <a href="pokazPodrecznik?${podrecznikId}" class="smallButton"><span class="smallButtonText">&#x21A9; wróć do podręcznika</span></a>
                </c:if>
            </td>
        </tr>
    </table>


</section>
