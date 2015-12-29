<%-- 
    Document   : INVOICE add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajFakturePotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th colspan="2">wprowadź informacje dotyczące faktury:</th>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="numer">Numer: </label></td>
                <td><input type="text" name="numer" value="${numer}" /></td>
                <td class="formErrorAsterix">${numerError}</td>
            </tr>
            <tr>
                <td><label for="data">Data: </label></td>
                <td><input type="date" name="data" value="${data}" /></td>
                <td class="formErrorAsterix">${dataError}</td>
            </tr>
            <tr>
                <td><label for="kwota">Kwota: </label></td>
                <td><input type="number" name="kwota" min="0" max="9999999" step="0.01" value="${kwota}" /></td>
<!--                <td><input type="text" name="kwota" value="${kwota}" /></td>-->
                <td class="formErrorAsterix">${kwotaError}</td>
            </tr>
            <tr>
                <td><label for="opis">Opis: </label></td>
                <td><textarea name="opis" rows="3" class="inputForm">${opis}</textarea></td>
                <td class="formErrorAsterix">${opisError}</td>
            </tr>
            <tr>
                <td><label for="firmaId">Firma: </label></td>
                <td>
                    <select name="firmaId">
                        <c:forEach var="firmaToSelect" items="${firmaList}">
                            <option value="${firmaToSelect.id}" ${firmaToSelect eq firma ? "selected" : ""}>${firmaToSelect.nazwa}</option>
                        </c:forEach>
                    </select>
                </td>
                
                <td class="formErrorAsterix">${firmaError}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zapisz" /></td>
                <td></td>
            </tr>
        </table>
        <input type="hidden" name="id" value="${id}" />
        <!-- we use this one in case we are editing -->
    </form>

</section>