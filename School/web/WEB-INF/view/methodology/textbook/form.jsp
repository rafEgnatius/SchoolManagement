<%-- 
    Document   : TEXTBOOK add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajPodrecznikPotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th colspan="2">wprowadź informacje dotyczące podręcznika:</th>
                <td colspan="1" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="nazwa">Tytuł: </label></td>
                <td><input type="text" name="nazwa" value="${nazwa}" /></td>
                <td class="formErrorAsterix">${nazwaError}</td>
            </tr>
            <tr>
                <td><label for="poziom">Poziom: </label></td>
                <td>
                    <select name="poziom">
                        <c:forEach var="languageLevel" items="${applicationScope['languageLevels']}">
                            <option value="${languageLevel}" ${languageLevel eq poziom ? "selected" : ""}>${languageLevel}</option>
                        </c:forEach>
                    </select>
                </td>
                <td class="formErrorAsterix">${poziomError}</td>
            </tr>
            <tr>
                <td><label for="jezykId">Język: </label></td>
                <td>
                    <select name="jezykId">
                        <c:forEach var="jezyk" items="${jezykList}">
                            <option value="${jezyk.id}" ${jezyk eq podrecznik.jezyk ? "selected" : ""}>${jezyk.nazwa}</option>
                        </c:forEach>
                    </select>
                </td>
                
                <td class="formErrorAsterix">${jezykError}</td>
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