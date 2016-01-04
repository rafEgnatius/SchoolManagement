<%-- 
    Document   : PARTICIPANT viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!--        LECTOR'S DETAILS-->
<c:if test="${!empty kursant}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">kursant: szczegóły</th>
            </tr>
            <tr>
                <th>id: </th>
                <td>${kursant.id}</td>
            </tr>
            <tr>
                <th>imię i nazwisko:</th>
                <td>${kursant.nazwa}</td>
            </tr>
            <tr>
                <th>telefon: </th>
                <td>${kursant.telefon}</td>
            </tr>
            <tr>
                <th>email: </th>
                <td><a class="email" href="mailto:${kursant.email}">${kursant.email}</a></td>
            </tr>
            <tr>
                <th>firma: </th>
                <td><a class="email" href="pokazFirme?${kursant.firma.id}">${kursant.firma.nazwa}</a></td>
            </tr>
        </table>
        <a  class="smallButton" href="edytujKursanta?${kursant.id}">
            <span class="smallButtonText">edytuj dane kursanta &#x279f;</span>
        </a>

        <hr />

        <!--        PARTICIPANT'S LANGUAGE PART-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="3">kursant: języki</th>
            </tr>
            <c:forEach var="jezykKursanta" items="${jezykKursantaList}">
                <c:choose>
                    <c:when test="${jezykKursanta.kursant eq kursant}">
                        <c:forEach var="jezyk" items="${jezykList}">
                            <c:choose>
                                <c:when test="${jezyk eq jezykKursanta.jezyk}">
                                    <tr>
                                        <th>${jezyk.nazwa}</th>
                                        <td>poziom: ${jezykKursanta.poziom}</td>
                                        <td><a class="tinyButton" href="usunJezykKursanta?jezykId=${jezyk.id}&kursantId=${kursant.id}"><span class="tinyButtonText">usuń &#x279f;</span></a>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

        </table>

        <!--        ADD LANGUAGE-->

        <form action="dodajJezykKursanta">
            <table class="detailedTable">
                <tr class="tableHeading">
                    <th colspan="3">dodaj język i poziom:</th>
                </tr>
                <tr>
                    <td>
                        <select name="jezykId" >
                            <c:forEach var="jezyk" items="${jezykList}">    
                                <c:set var="languageOnTheList" value="false"></c:set>
                                <c:forEach var="jezykKursanta" items="${jezykKursantaList}">
                                    <c:choose>
                                        <c:when test="${jezykKursanta.jezyk eq jezyk}">
                                            <c:choose>
                                                <c:when test="${jezykKursanta.kursant eq kursant}">
                                                    <c:set var="languageOnTheList" value="true"></c:set>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>        
                                <c:choose>
                                    <c:when test="${languageOnTheList eq false}">
                                        <option value="${jezyk.id}">${jezyk.nazwa}</option>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name="poziom">
                            <c:forEach var="languageLevel" items="${applicationScope['languageLevels']}">
                                <option value="${languageLevel}">${languageLevel}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type="hidden" name="kursantId" value="${kursant.id}" >
                        <input type="submit" value="Dodaj" />
                    </td>
                </tr>
            </table>
        </form>
                        
    </section>
</c:if>