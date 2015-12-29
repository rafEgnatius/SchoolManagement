<%-- 
    Document   : LECTOR viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!--        LECTOR'S DETAILS-->
<c:if test="${!empty lektor}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">lektor: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${lektor.id}</td>
            </tr>
            <tr>
                <th>Imię i nazwisko:</th>
                <td>${lektor.nazwa}</td>
            </tr>
            <tr>
                <th>Miasto:</th>
                <td>${lektor.miasto}</td>
            </tr>
            <tr>
                <th>Telefon:</th>
                <td>${lektor.telefon}</td>
            </tr>
            <tr>
                <th>Email:</th>
                <td><a class="email" href="mailto:${lektor.email}">${lektor.email}</a></td>
            </tr>
            <tr>
                <th>Typ umowy:</th>
                <td>${lektor.umowa}</td>
            </tr>
            <tr>
                <th>NIP/PESEL:</th>
                <td>${lektor.nip}</td>
            </tr>
        </table>
        <a  class="smallButton" href="edytujLektora?${lektor.id}">
            <span class="smallButtonText">edytuj dane lektora &#x279f;</span>
        </a>

        <hr />

        <!--        LECTOR'S LANGUAGE PART-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="3">lektor: języki</th>
            </tr>
            <c:forEach var="jezykLektora" items="${jezykLektoraList}">
                <c:choose>
                    <c:when test="${jezykLektora.lektor eq lektor}">
                        <c:forEach var="jezyk" items="${jezykList}">
                            <c:choose>
                                <c:when test="${jezyk eq jezykLektora.jezyk}">
                                    <tr>
                                        <th>${jezyk.nazwa}</th>
                                        <td>${(jezykLektora.natywny) ? "native" : ""}</td>
                                        <td><a class="tinyButton" href="usunJezykLektora?jezykId=${jezyk.id}&lektorId=${lektor.id}"><span class="tinyButtonText">usuń &#x279f;</span></a>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

        </table>

        <!--        ADD LECTOR'S LANGUAGE-->

        <table class="cancelSaveTable">
            <tr>
                <th>dodaj język:</th>
            </tr>
            <tr>
                <td>
                    <form action="dodajJezykLektora">
                        <select name="jezykId" >

                            <c:forEach var="jezyk" items="${jezykList}">    
                                <c:set var="languageOnTheList" value="false"></c:set>
                                <c:forEach var="jezykLektora" items="${jezykLektoraList}">
                                    <c:choose>
                                        <c:when test="${jezykLektora.jezyk eq jezyk}">
                                            <c:choose>
                                                <c:when test="${jezykLektora.lektor eq lektor}">
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
                        <input type="hidden" name="lektorId" value="${lektor.id}" >
                        <label for="nativeSpeaker"> native speaker </label>
                        <input type="checkbox" name="nativeSpeaker"  id="nativeSpeaker" value="ON" />
                        <input type="submit" value="Dodaj" />
                    </form>
                </td>
            </tr>
        </table>

        <hr />

        <!--        BORROWINGS-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="4">lektor: wypożyczone podręczniki</th>
            </tr>
            <c:forEach var="wypozyczenie" items="${wypozyczenieList}">
                <c:choose>
                    <c:when test="${wypozyczenie.lektor eq lektor}">
                        <c:forEach var="podrecznik" items="${podrecznikList}">
                            <c:choose>
                                <c:when test="${podrecznik eq wypozyczenie.podrecznik}">
                                    <tr>
                                        <th>${podrecznik.nazwa}</th>
                                        <td>poziom: ${podrecznik.poziom}</td>
                                        <td>nr w systemie: ${podrecznik.id}</td>
                                        <td><a class="tinyButton" href="oddajPodrecznikDoBiblioteki?podrecznikId=${podrecznik.id}&lektorId=${wypozyczenie.lektor.id}">
                                                <span class="tinyButtonText">usuń &#x279f;</span></a>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

        </table>

        <a class="smallButton" href="dodajPodrecznikDoWypozyczenia?lektorId=${lektor.id}">
            <span class="smallButtonText">dodaj wypożyczenie &#x279f;</span>
        </a>

    </section>
</c:if>