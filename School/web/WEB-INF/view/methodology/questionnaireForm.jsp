<%-- 
    Document   : QUESTIONNAIRE add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajAnkietePotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th colspan="3">prosimy o odpowiedź na poniższe pytania: </th>
            </tr>
            <c:if test="${formError  == 'formError'}">
                <tr>
                    <td colspan="3" class="formError">Formularz zawiera błędy:</td>
                </tr>
            </c:if>

            <c:forEach var="pytanie" items="${listaPytan}" varStatus="iter">
                <tr>
                    <td>
                        ${pytanie}
                    </td>

                    <c:choose>
                        <c:when test="${errorList[iter.index] == '*'}"> <!-- here we had an error -->
                            <td class="cellsInCentre">
                                <input type="radio" name="pytanie${iter.index}" value="true" />tak
                                <input type="radio" name="pytanie${iter.index}" value="false" />nie
                            </td>
                            <td class="formErrorAsterix">
                                ${errorList[iter.index]}
                                <br>
                                ${iter.index} -> ${answerList[iter.index]}
                            </td>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${formError  == 'formError'}"> <!-- there was an error somewhere but not here -->
                                    <td class="cellsInCentre">
                                        <input type="radio" name="pytanie${iter.index}" value="true" ${answerList[iter.index] == 'true' ? 'checked' : ''} />tak
                                        <input type="radio" name="pytanie${iter.index}" value="false" ${answerList[iter.index] == 'false' ? 'checked' : ''} />nie
                                    </td>
                                    <td>
                                        ${iter.index} -> ${answerList[iter.index]}
                                        <!--empty -> there was an error somewhere but this answer was OK, so we give answer above -->
                                    </td>
                                </c:when>
                                <c:otherwise> <!-- this is a new form -->
                                    <td>
                                        <input type="radio" name="pytanie${iter.index}" value="true" />tak
                                        <input type="radio" name="pytanie${iter.index}" value="false" />nie
                                    </td>
                                    <td>
                                        <!--empty -> this is a new form -->
                                        ${iter.index} -> ${answerList[iter.index]}
                                    </td>
                                </c:otherwise>
                            </c:choose> 
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            <tr>
                <td>
                    ${pytanieOpisowe}
                </td>
                <td>
                    <textarea name="pytanie23" rows="3" class="inputForm">${answer23}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="cellsOnRight">
                    <input type="submit" value="Zapisz" />
                </td>
            </tr>
            <input type="hidden" name="lektorId" value="${lektorId}" />
            <input type="hidden" name="kursantId" value="${kursantId}" />
            <!-- we use this one in case we are editing -->
        </table>
    </form>
</section>