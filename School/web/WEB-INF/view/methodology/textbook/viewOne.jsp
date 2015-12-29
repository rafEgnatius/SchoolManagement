<%-- 
    Document   : TEXTBOOK viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!--        TEXTBOOK'S DETAILS-->
<c:if test="${!empty podrecznik}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">podręcznik: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${podrecznik.id}</td>
            </tr>
            <tr>
                <th>Tytuł:</th>
                <td>${podrecznik.nazwa}</td>
            </tr>
            <tr>
                <th>Poziom:</th>
                <td>${podrecznik.poziom}</td>
            </tr>
            <tr>
                <th>Język:</th>
                    <c:forEach var="jezyk" items="${jezykList}">
                        <c:choose>
                            <c:when test="${jezyk eq podrecznik.jezyk}">
                            <td>${jezyk.symbol} (${jezyk.nazwa})</td>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </tr>
        </table>
        <a  class="smallButton" href="edytujPodrecznik?${podrecznik.id}">
            <span class="smallButtonText">edytuj informacje dotyczące podręcznika &#x279f;</span>
        </a>

        <hr />

        <!--        BORROWINGS-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="3">biblioteka</th>
            </tr>
            <c:set var="borrowed" value="false"></c:set>
            <c:forEach var="wypozyczenie" items="${wypozyczenieList}">
                <c:choose>
                    <c:when test="${wypozyczenie.podrecznik eq podrecznik}">
                        <c:set var="borrowed" value="true">
                        </c:set>
                        <tr>
                            <td>
                                podręcznik wypożyczył: ${wypozyczenie.lektor.nazwa}
                            </td>
                            <td>
                                Data wypożyczenia:<br />
                                ${wypozyczenie.data}
                            </td>
                            <td>
                                <a class="tinyButton" href="oddajPodrecznikDoBiblioteki?podrecznikId=${podrecznik.id}&lektorId=${wypozyczenie.lektor.id}">
                                    <span class="tinyButtonText">usuń &#x279f;</span>
                                </a>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${borrowed eq false}">
                    <tr>
                        <td>
                            <a class="smallButton" href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznik.id}">
                                <span class="smallButtonText">dodaj wypożyczenie &#x279f;</span>
                            </a>
                        </td>
                    </tr>
                </c:when>
            </c:choose>

        </table>



    </section>
</c:if>