<%-- 
    Document   : PARTICIPANT add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajKursantaPotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th>wprowadź dane kursanta:</th>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="nazwa">Imię i nazwisko: </label></td>
                <td><input type="text" name="nazwa" value="${nazwa}" class="inputForm" /></td>
                <td class="formErrorAsterix">${nazwaError}</td>
            </tr>
            <tr>
                <td><label for="telefon">Telefon: </label></td>
                <td><input type="tel" name="telefon" value="${telefon}" class="inputForm" /></td>
                <td class="formErrorAsterix">${telefonError}</td>
            </tr>
            <tr>
                <td><label for="email">Email: </label></td>
                <td><input type="email" name="email" value="${email}" class="inputForm" /></td>
                <td class="formErrorAsterix">${emailError}</td>
            </tr>
            <tr>
                <td><label for="firmaId">Firma: </label></td>
                <td>
                    <select name="firmaId">
                        <c:forEach var="firmaOption" items="${firmaList}">
                            <option value="${firmaOption.id}" ${firmaOption eq firma ? "selected" : ""}>${firmaOption.nazwa}</option>
                        </c:forEach>
                    </select>
                </td>
                
                <td class="formErrorAsterix">${firmaError}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zapisz" /></td>
                <td></td>
            </tr>
            <tr>

            </tr>
        </table>
            <input type="hidden" name="id" value="${id}" />
            <!-- we use this one in case we are editing -->
    </form>

</section>