<%-- 
    Document   : PROGRAMME add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajProgramPotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th colspan="2">wprowadź informacje dotyczące programu nauczania:</th>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="metoda">metoda: </label></td>
                <td>
                    <select name="metodaId">
                            <option value="1" ${metodaId eq "1" ? "selected" : ""}>audiolingwalna</option>
                            <option value="2" ${metodaId eq "2" ? "selected" : ""}>komunikatywna</option>
                            <option value="3" ${metodaId eq "3" ? "selected" : ""}>gramatyczno-tłumaczeniowa</option>
                            <option value="0" ${metodaId eq "0" ? "selected" : ""}>inna</option>
                    </select>
                </td>
                
                <td class="formErrorAsterix">${jezykError}</td>
            </tr>
            <tr>
                <td><label for="referencja">Referencja: </label></td>
                <td><input type="text" name="referencja" value="${referencja}" /></td>
                <td class="formErrorAsterix">${referencjaError}</td>
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