<%-- 
    Document   : index
    Created on : 01-Dec-2015, 19:37:57
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
                <c:if test="${!empty lekcjaId}">
                    <a href="pokazLekcje?${lekcjaId}" class="smallButton"><span class="smallButtonText">&#x21A9; wróć do lekcji</span></a>
                </c:if>
            </td>
        </tr>
    </table>
    
</section>
