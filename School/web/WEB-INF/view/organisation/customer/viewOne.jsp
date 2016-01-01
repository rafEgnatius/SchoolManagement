<%-- 
    Document   : CUSTOMER viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty firma}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">firma: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${firma.id}</td>
            </tr>
            <tr>
                <th>Nazwa:</th>
                <td>${firma.nazwa}</td>
            </tr>
            <tr>
                <th>Symbol:</th>
                <td>${firma.symbol}</td>
            </tr>
            <tr>
                <th>Miasto:</th>
                <td>${firma.miasto}</td>
            </tr>
            <tr>
                <th>Adres:</th>
                <td>${firma.adres}</td>
            </tr>
            <tr>
                <th>Osoba do kontaktu:</th>
                <td><a class="email" href="mailto:${firma.email}">${firma.osoba}</a></td>
            </tr>
            <tr>
                <th>Telefon:</th>
                <td>${firma.telefon}</td>
            </tr>
            <tr>
                <th>Komórka:</th>
                <td>${firma.komorka}</td>
            </tr>
            <tr>
                <th>Email:</th>
                <td><a class="email" href="mailto:${firma.email}">${firma.email}</a></td>
            </tr>
            <tr>
                <th>NIP:</th>
                <td>${firma.nip}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujFirme?${firma.id}">
            <span class="smallButtonText">edytuj dane firmy (klienta) &#x279f;</span>
        </a>

        <hr />

        <!--rates-->

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
                            dodaj stawkę dla native speakera
                            <br />
                            <form action="dodajStawkeNativeSpeakeraDlaFirmy" method="POST">
                                <input type="number" name="stawkaNative" min="0" max="9999999" step="0.01" value="${stawkaNative}" />
                                <input type="submit" value="Zapisz" />
                                <input type="hidden" name="id" value="${firma.id}" />
                            </form>
                        </td>
                        <td class="inputError">
                            ${stawkaNativeError}
                        </td>
                    </tr>
                </c:when>
            </c:choose>

            <c:choose>
                <c:when test="${foundNotNative eq false}">
                    <tr>
                        <td colspan="2">
                            dodaj stawkę dla lektora
                            <br />
                            <form action="dodajStawkeLektoraDlaFirmy" method="POST">
                                <input type="number" name="stawkaLektor" min="0" max="9999999" step="0.01" value="${stawkaLektor}" />
                                <input type="submit" value="Zapisz" />
                                <input type="hidden" name="id" value="${firma.id}" />
                            </form>
                        </td>
                        <td class="inputError">
                            ${stawkaLektorError}
                        </td>
                    </tr>
                </c:when>
            </c:choose>

        </table>

        <!--payment  -->




    </section>
</c:if>