<%-- 
    Document   : LECTOR add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajLektoraPotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th>wprowadź dane lektora:</th>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="nazwa">Imię i nazwisko: </label></td>
                <td><input type="text" name="nazwa" value="${nazwa}" class="inputForm" /></td>
                <td class="formErrorAsterix">${nazwaError}</td>
            </tr>
            <tr>
                <td><label for="miasto">Miasto: </label></td>
                <td><input type="text" name="miasto" value="${miasto}" class="inputForm" /></td>
                <td class="formErrorAsterix">${miastoError}</td>
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
                <td><label for="umowa">Typ umowy: </label></td>
                <td><input type="text" name="umowa" value="${umowa}" class="inputForm" /></td>
                <td class="formErrorAsterix">${umowaError}</td>
            </tr>
            <tr>
                <td><label for="nip">NIP/PESEL: </label></td>
                <td><input type="text" name="nip" value="${nip}" class="inputForm" /></td>
                <td class="formErrorAsterix">${nipError}</td>
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