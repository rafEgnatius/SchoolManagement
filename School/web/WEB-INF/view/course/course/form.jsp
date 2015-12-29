<%-- 
    Document   : COURSE add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajKursPotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th>wprowadź informacje dotyczące kursu:</td>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="rok">Rok: </label></td>
                <td><input type="text" name="rok" value="${rok}" class="inputForm" /></td>
                <td class="formErrorAsterix">${rokError}</td>
            </tr>
            <tr>
                <td><label for="semestr">Semestr: </label></td>
                <td>
                    <select name="semestr" class="inputForm">
                        <option value="letni" ${semestr eq "letni" ? "selected" : ""}>letni</option>
                        <option value="zimowy" ${semestr eq "zimowy" ? "selected" : ""}>zimowy</option>
                        <option value="inny" ${semestr eq "inny" ? "selected" : ""}>inny</option>
                    </select>
                </td>
                <td class="formErrorAsterix">${semestrError}</td>
            </tr>
            <tr>
                <td><label for="jezykId">Język: </label></td>
                <td>
                    <select name="jezykId" class="inputForm">
                        <c:forEach var="jezykFromList" items="${jezykList}">
                            <option value="${jezykFromList.id}" ${jezykFromList eq jezyk ? "selected" : ""}>${jezykFromList.nazwa}</option>
                        </c:forEach>
                    </select>
                </td>

                <td class="formErrorAsterix">${jezykError}</td>
            </tr>
            <tr>
                <td><label for="adres">Symbol: </label></td>
                <td><input type="text" name="symbol" value="${symbol}" class="inputForm" /></td>
                <td class="formErrorAsterix">${symbolError}</td>
            </tr>
            <tr>
                <td><label for="opis">Opis: </label></td>
                <td><textarea name="opis" rows="3" class="inputForm">${opis}</textarea></td>
                <td class="formErrorAsterix">${opisError}</td>
            </tr>
            <tr>
                <td><label for="sala">Sala: </label></td>
                <td><input type="text" name="sala" value="${sala}" class="inputForm" /></td>
                <td class="formErrorAsterix">${salaError}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zapisz" /></td>
            </tr>
        </table>
        <input type="hidden" name="id" value="${id}" />
        <!-- we use this one in case we are editing -->
    </form>
        
</section>