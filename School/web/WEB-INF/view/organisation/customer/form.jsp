<%-- 
    Document   : CUSTOMER add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajFirmePotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th>wprowadź dane firmy (klienta):</td>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="nazwa">Nazwa: </label></td>
                <td><input type="text" name="nazwa" value="${nazwa}" /></td>
                <td class="formErrorAsterix">${nazwaError}</td>
            </tr>
            <tr>
                <td><label for="symbol">Symbol: </label></td>
                <td><input type="text" name="symbol" value="${symbol}" /></td>
                <td class="formErrorAsterix">${symbolError}</td>
            </tr>
            <tr>
                <td><label for="miasto">Miasto: </label></td>
                <td><input type="text" name="miasto" value="${miasto}" /></td>
                <td class="formErrorAsterix">${miastoError}</td>
            </tr>
            <tr>
                <td><label for="adres">Adres: </label></td>
                <td><textarea name="adres" rows="3">${adres}</textarea></td>
                <td class="formErrorAsterix">${adresError}</td>
            </tr>
            <tr>
                <td><label for="osoba">Osoba do kontaktu: </label></td>
                <td><input type="text" name="osoba" value="${osoba}" /></td>
                <td class="formErrorAsterix">${osobaError}</td>
            </tr>
            <tr>
                <td><label for="telefon">Telefon: </label></td>
                <td><input type="text" name="telefon" value="${telefon}" /></td>
                <td class="formErrorAsterix">${telefonError}</td>
            </tr>
            <tr>
                <td><label for="komorka">Tel. komórkowy: </label></td>
                <td><input type="text" name="komorka" value="${komorka}" /></td>
                <td class="formErrorAsterix">${komorkaError}</td>
            </tr>
            <tr>
                <td><label for="email">Email: </label></td>
                <td><input type="email" name="email" value="${email}" /></td>
                <td class="formErrorAsterix">${emailError}</td>
            </tr>
            <tr>
                <td><label for="nip">NIP </label></td>
                <td><input type="text" name="nip" value="${nip}" /></td>
                <td class="formErrorAsterix">${nipError}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zapisz" /></td>
            </tr>
        </table>
        <input type="hidden" name="id" value="${id}" />
        <!-- we use this one in case we are editing -->
    </form>

</section>