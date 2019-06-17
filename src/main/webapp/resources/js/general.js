/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function modificareParolaUtilizator(numeUser) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var parolaInitiala = $('#parola_user_initiala').val();
    var parolaNoua = $('#parola_user_noua').val();
    var parolaConfirmata = $('#parola_user_confirmata').val();

    $('#alerta_modificare_parola').removeClass('alert-success');
    $('#alerta_modificare_parola').removeClass('alert-danger');
    $('#alerta_modificare_parola').empty();

    if (parolaInitiala == '') {
        $('#alerta_modificare_parola').css('display', '');
        $('#alerta_modificare_parola').addClass('alert-danger');
        $('#alerta_modificare_parola').html('Parola veche nu poate fi goala!');
        return;
    }

    if (parolaNoua == '') {
        $('#alerta_modificare_parola').css('display', '');
        $('#alerta_modificare_parola').addClass('alert-danger');
        $('#alerta_modificare_parola').html('Parola noua nu poate fi goala!');
        return;
    }

    if (parolaConfirmata == '') {
        $('#alerta_modificare_parola').css('display', '');
        $('#alerta_modificare_parola').addClass('alert-danger');
        $('#alerta_modificare_parola').html('Confirmarea noii parole trebuie completata!');
        return;
    }

    if (parolaNoua != parolaConfirmata) {
        $('#alerta_modificare_parola').css('display', '');
        $('#alerta_modificare_parola').addClass('alert-danger');
        $('#alerta_modificare_parola').html('Parola noua nu este egala cu parola confirmata!');
        return;
    }

    var datePost = '&parolaInitiala=' + parolaInitiala + '&parolaNoua=' + parolaNoua
            + '&numeUser=' + numeUser;

    $.ajax({
        type: 'POST',
        url: root + "/modificare_parola_user",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                $('#alerta_modificare_parola').css('display', '');
                $('#alerta_modificare_parola').addClass('alert-success');
                $('#alerta_modificare_parola').html('S-a modificat parola!');
            } else {
                console.log("Eroare la modificarea parolei: " + data.mesajConsola);

                $('#alerta_modificare_parola').css('display', '');
                $('#alerta_modificare_parola').addClass('alert-danger');
                if (data.mesajUtilizator != undefined && data.mesajUtilizator != ''
                        && data.mesajUtilizator != null) {
                    $('#alerta_modificare_parola').html('Nu s-a modificat parola! ' + data.mesajUtilizator);
                } else {
                    $('#alerta_modificare_parola').html('Nu s-a modificat parola! EROARE SERVER');
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la modificarea parolei");

            $('#alerta_modificare_parola').css('display', '');
            $('#alerta_modificare_parola').addClass('alert-danger');
            $('#alerta_modificare_parola').html('Nu s-a modificat parola! EROARE SERVER');
        }
    });
}