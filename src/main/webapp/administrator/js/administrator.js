/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function adaugareUtilizator() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var numeUtilizator = $('#nume_user').val();
    var parola = $('#parola_user').val();
    var enabled = $('#enabled option:selected').val();
    var tipUser = $('#tip_utilizator option:selected').val();

    $('#alerta_adaugare_utilizator').removeClass('alert-success');
    $('#alerta_adaugare_utilizator').removeClass('alert-danger');
    $('#alerta_adaugare_utilizator').empty();

    if (numeUtilizator == '') {
        $('#alerta_adaugare_utilizator').css('display', '');
        $('#alerta_adaugare_utilizator').addClass('alert-danger');
        $('#alerta_adaugare_utilizator').html('Numele de utilizator nu poate fi gol!');
        return;
    }

    if (parola == '') {
        $('#alerta_adaugare_utilizator').css('display', '');
        $('#alerta_adaugare_utilizator').addClass('alert-danger');
        $('#alerta_adaugare_utilizator').html('Parola nu poate fi goala!');
        return;
    }

    if (enabled == undefined || enabled == '') {
        $('#alerta_adaugare_utilizator').css('display', '');
        $('#alerta_adaugare_utilizator').addClass('alert-danger');
        $('#alerta_adaugare_utilizator').html('Trebuie selectat un tip de activare cont!');
        return;
    }

    if (tipUser == undefined || tipUser == '') {
        $('#alerta_adaugare_utilizator').css('display', '');
        $('#alerta_adaugare_utilizator').addClass('alert-danger');
        $('#alerta_adaugare_utilizator').html('Trebuie selectat un tip de utilizator!');
        return;
    }

    var datePost = '&numeUtilizator=' + numeUtilizator + '&parola=' + parola
            + '&enabled=' + enabled + '&tipUser=' + tipUser;

    $.ajax({
        type: 'POST',
        url: root + "/adaugare_user",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                $('#nume_user').val('');
                $('#parola_user').val('');

                $('#alerta_adaugare_utilizator').css('display', '');
                $('#alerta_adaugare_utilizator').addClass('alert-success');
                $('#alerta_adaugare_utilizator').html('S-a adaugat utilizatorul!');
            } else {
                console.log("Eroare la adaugarea utilizatorului: " + data.mesajConsola);

                $('#alerta_adaugare_utilizator').css('display', '');
                $('#alerta_adaugare_utilizator').addClass('alert-danger');
                $('#alerta_adaugare_utilizator').html('Nu s-a adaugat utilizatorul! EROARE SERVER');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la adaugarea utilizatorului");

            $('#alerta_adaugare_utilizator').css('display', '');
            $('#alerta_adaugare_utilizator').addClass('alert-danger');
            $('#alerta_adaugare_utilizator').html('Nu s-a adaugat utilizatorul! EROARE SERVER');
        }
    });
}