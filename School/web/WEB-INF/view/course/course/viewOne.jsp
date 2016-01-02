<%-- 
    Document   : COURSE viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty kurs}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">kurs: szczegóły</th>
            </tr>
            <tr>
                <th>Id: </th>
                <td>${kurs.id}</td>
            </tr>
            <tr>
                <th>Język: </th>
                <td>${kurs.jezyk.nazwa}</td>
            </tr>
            <tr>
                <th>Symbol: </th>
                <td>${kurs.symbol}</td>
            </tr>
            <tr>
                <th>Opis: </th>
                <td>${kurs.opis}</td>
            </tr>
            <tr>
                <th>Rok: </th>
                <td>${kurs.rok}</td>
            </tr>
            <tr>
                <th>Semestr: </th>
                <td>${kurs.semestr}</td>
            </tr>
            <tr>
                <th>Sala: </th>
                <td>${kurs.sala}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujKurs?${kurs.id}">
            <span class="smallButtonText">edytuj informacje dotyczące kursu &#x279f;</span>
        </a>

        <hr />

        <!--        course set client-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">firma</th>
            </tr>

            <c:if test="${not empty kurs.firma}">
                <tr>
                    <td>
                        kurs prowadzony w: ${kurs.firma.nazwa}
                    </td>
                    <td>
                        <a class="tinyButton" href="usunFirmeZKursu?${kurs.id}">
                            <span class="tinyButtonText">usuń &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty kurs.firma}">
                <tr>
                    <td colspan="2">
                        <a class="tinyButton" href="dodajFirmeDoKursu?kursId=${kurs.id}">
                            <span class="tinyButtonText">dodaj &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
        </table>

        <hr />

        <!--        course set program-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">program kursu</th>
            </tr>

            <c:if test="${not empty kurs.program}">
                <tr>
                    <td>
                        metoda: ${kurs.program.metoda}
                    </td>
                    <td rowspan="2">
                        <a class="tinyButton" href="usunProgramZKursu?${kurs.id}">
                            <span class="tinyButtonText">usuń &#x279f;</span>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        referencja: ${kurs.program.referencja}
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty kurs.program}">
                <tr>
                    <td colspan="2">
                        <a class="tinyButton" href="dodajProgramDoKursu?kursId=${kurs.id}">
                            <span class="tinyButtonText">dodaj &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
        </table>

        <hr />

        <!--        course set lector-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">kurs prowadzi</th>
            </tr>

            <c:if test="${not empty kurs.lektor}">
                <tr>
                    <td>
                        lektor: ${kurs.lektor.nazwa}
                    </td>
                    <td>
                        <a class="tinyButton" href="usunLektoraZKursu?${kurs.id}">
                            <span class="tinyButtonText">usuń &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty kurs.lektor}">
                <tr>
                    <td colspan="2">
                        <a class="tinyButton" href="dodajLektoraDoKursu?kursId=${kurs.id}">
                            <span class="tinyButtonText">dodaj &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
        </table>

        <hr />

        <!--        LECTOR rates-->

        <c:if test="${!empty kurs.lektor}">
            <table class="detailedTable">
                <tr class="tableHeading">
                    <th colspan="2">stawka lektora</th>
                </tr>
                <c:set var="foundLector" value="false"></c:set>
                <c:forEach var="stawkaLektora" items="${stawkaLektoraList}">
                    <c:choose>
                        <c:when test="${stawkaLektora.lektor eq kurs.lektor}">

                            <c:choose>
                                <c:when test="${stawkaLektora.kurs eq kurs}">
                                    <c:set var="foundLector" value="true"></c:set>
                                        <tr>
                                            <td>
                                                stawka dla tego kursu wynosi: ${stawkaLektora.stawka} PLN
                                        </td>
                                        <td>
                                            <a class="tinyButton" href="usunStawkeLektora?id=${kurs.id}&lektorId=${stawkaLektora.lektor.id}">
                                                <span class="tinyButtonText">zmień &#x279f;</span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>

                        </c:when>
                    </c:choose>
                </c:forEach>

                <!--                                add-->

                <c:choose>
                    <c:when test="${foundLector eq false}">
                        <tr>
                            <td>
                                dodaj stawkę dla lektora
                                <br />
                                <form action="dodajStawkeLektora" method="POST">
                                    <input type="number" name="stawka" min="0" max="9999999" step="0.01" value="${stawka}" />
                                    <input type="submit" value="Zapisz" />
                                    <input type="hidden" name="id" value="${kurs.id}" />
                                    <input type="hidden" name="lektorId" value="${kurs.lektor.id}" />
                                </form>
                            </td>
                            <td class="inputError">
                                ${stawkaError}
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </table>
            <hr />
        </c:if>


        <!--CUSTOMER rates-->

        <c:if test="${!empty firma}">
            <table class="detailedTable">
                <tr class="tableHeading">
                    <th colspan="2">stawka firmy</th>
                </tr>
                <c:set var="foundNative" value="false"></c:set>
                <c:set var="foundNotNative" value="false"></c:set>
                <c:forEach var="stawkaFirmy" items="${stawkaFirmyList}">
                    <c:choose>
                        <c:when test="${stawkaFirmy.firma eq firma}">

                            <c:choose>
                                <c:when test="${stawkaFirmy.stawkaFirmyPK.natywny}">
                                    <c:set var="foundNative" value="true"></c:set>
                                        <tr>
                                            <td>
                                                stawka dla native speakera wynosi: ${stawkaFirmy.stawka} PLN
                                        </td>
                                        <td>
                                            <a class="tinyButton" href="usunStawkeNativeSpeakeraDlaFirmy?${firma.id}">
                                                <span class="tinyButtonText">zmień &#x279f;</span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>

                            <c:choose>
                                <c:when test="${not stawkaFirmy.stawkaFirmyPK.natywny}">
                                    <c:set var="foundNotNative" value="true"></c:set>
                                        <tr>
                                            <td>
                                                stawka dla lektora wynosi: ${stawkaFirmy.stawka} PLN
                                        </td>
                                        <td>
                                            <a class="tinyButton" href="usunStawkeLektoraDlaFirmy?${firma.id}">
                                                <span class="tinyButtonText">zmień &#x279f;</span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>

                        </c:when>
                    </c:choose>
                </c:forEach>

                <!--                                add-->

                <c:choose>
                    <c:when test="${foundNative eq false}">
                        <tr>
                            <td colspan="2">
                                <a class="tinyButton" href="pokazFirme?${firma.id}">
                                    <span class="tinyButtonText">dodaj stawkę dla native speakera &#x279f;</span>
                                </a>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>

                <c:choose>
                    <c:when test="${foundNotNative eq false}">
                        <tr>
                            <td colspan="2">
                                <a class="tinyButton" href="pokazFirme?${firma.id}">
                                    <span class="tinyButtonText">dodaj stawkę dla lektora &#x279f;</span>
                                </a>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>

            </table>
        </c:if>

    </section>
</c:if>
