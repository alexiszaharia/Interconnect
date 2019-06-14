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

function activeazaDezactiveazaUser(enabled, id) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    
    var datePost = '&enabled=' + enabled + '&id=' + id;

    $.ajax({
        type: 'POST',
        url: root + "/activare_dezactivare_user",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                if (enabled == 0) {
                    $('td[specific=' + id + ']').empty();
                    $('td[specific=' + id + ']').html('<i class="fas fa-window-close" ' 
                                + 'style="color: red; font-size: 18px; cursor: pointer;" ' 
                                + 'onclick="activeazaDezactiveazaUser(1, ' + id + ')" ' 
                                + 'title="Dezactivat"></i>');
                } else if (enabled == 1) {
                    $('td[specific=' + id + ']').empty();
                    $('td[specific=' + id + ']').html('<i class="fas fa-check-square" ' 
                                + 'style="color: green; font-size: 18px; cursor: pointer;" ' 
                                + 'onclick="activeazaDezactiveazaUser(0, ' + id + ')" ' 
                                + 'title="Activat"></i>');
                }
            } else {
                console.log("Eroare la modificarea activarii: " + data.mesajConsola);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la modificarea activarii");
        }
    });
}

function modificareUtilizator(idUser) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var numeUtilizator = $('#nume_user').val();
    var parola = $('#parola_user').val();
    var tipUser = $('#tip_utilizator option:selected').val();

    $('#alerta_modificare_utilizator').removeClass('alert-success');
    $('#alerta_modificare_utilizator').removeClass('alert-danger');
    $('#alerta_modificare_utilizator').empty();

    if (numeUtilizator == '') {
        $('#alerta_modificare_utilizator').css('display', '');
        $('#alerta_modificare_utilizator').addClass('alert-danger');
        $('#alerta_modificare_utilizator').html('Numele de utilizator nu poate fi gol!');
        return;
    }

    if (tipUser == undefined || tipUser == '') {
        $('#alerta_modificare_utilizator').css('display', '');
        $('#alerta_modificare_utilizator').addClass('alert-danger');
        $('#alerta_modificare_utilizator').html('Trebuie selectat un tip de utilizator!');
        return;
    }
    
    if (numeUtilizator == userInitial 
            && parola == parolaInitiala 
            && tipUser == rolInitial) {
        $('#alerta_modificare_utilizator').css('display', '');
        $('#alerta_modificare_utilizator').addClass('alert-danger');
        $('#alerta_modificare_utilizator').html('Nu s-au adus modificari!');
        return;
    }

    var datePost = '&numeUtilizator=' + numeUtilizator + '&parola=' + parola 
            + '&tipUser=' + tipUser + '&idUser=' + idUser;

    $.ajax({
        type: 'POST',
        url: root + "/modificare_user",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                $('#alerta_modificare_utilizator').css('display', '');
                $('#alerta_modificare_utilizator').addClass('alert-success');
                $('#alerta_modificare_utilizator').html('S-a modificat utilizatorul!');
            } else {
                console.log("Eroare la modificarea utilizatorului: " + data.mesajConsola);

                $('#alerta_modificare_utilizator').css('display', '');
                $('#alerta_modificare_utilizator').addClass('alert-danger');
                $('#alerta_modificare_utilizator').html('Nu s-a modificat utilizatorul! EROARE SERVER');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la modificarea utilizatorului");

            $('#alerta_modificare_utilizator').css('display', '');
            $('#alerta_modificare_utilizator').addClass('alert-danger');
            $('#alerta_modificare_utilizator').html('Nu s-a modificat utilizatorul! EROARE SERVER');
        }
    });
}