/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function adaugareReferendum(numeUser) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var form = $('#form_adaugare_referendum')[0];
    var formData = new FormData(form);

//    var intrebare = $('#intrebare_referendum').val();
    var data = $('#data_referendum').val();
    var prezentare = $('#prezentare_referendum').val();
//    var optiuni = getOptiuni();

    $('#alerta_adaugare_referendum').removeClass('alert-success');
    $('#alerta_adaugare_referendum').removeClass('alert-danger');
    $('#alerta_adaugare_referendum').empty();

//    if (intrebare == '') {
//        $('#alerta_adaugare_referendum').css('display', '');
//        $('#alerta_adaugare_referendum').addClass('alert-danger');
//        $('#alerta_adaugare_referendum').html('Intrebarea nu poate fi goala!');
//        return;
//    }

    if (prezentare == '') {
        $('#alerta_adaugare_referendum').css('display', '');
        $('#alerta_adaugare_referendum').addClass('alert-danger');
        $('#alerta_adaugare_referendum').html('Trebuie completata o prezentare!');
        return;
    }

    if (data == '') {
        $('#alerta_adaugare_referendum').css('display', '');
        $('#alerta_adaugare_referendum').addClass('alert-danger');
        $('#alerta_adaugare_referendum').html('Trebuie completata data!');
        return;
    }

    if (nrIntrebariSalvate == 0) {
        $('#alerta_adaugare_referendum').css('display', '');
        $('#alerta_adaugare_referendum').addClass('alert-danger');
        $('#alerta_adaugare_referendum').html('Nu s-a salvat nici o intrebare!');
        return;
    }

//    if (optiuni == false) {
//        $('#alerta_adaugare_referendum').css('display', '');
//        $('#alerta_adaugare_referendum').addClass('alert-danger');
//        $('#alerta_adaugare_referendum').html('Trebuie completate cel putin doua optiuni de raspuns!');
//        return;
//    }

//    var datePost = '&intrebare=' + intrebare + '&data=' + data
//            + '&optiuni=' + optiuni + '&nume_user=' + numeUser;

    formData.append('intrebari', intrebariSalvate);
    formData.append('nume_user', numeUser);

    $.ajax({
        type: 'POST',
        url: root + "/adaugare_referendum",
        contentType: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: formData,
        processData: false,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                $('#intrebare_referendum').val('');
                $('#data_referendum').val('');
                $('input[name=optiune]').val('');

                $('#alerta_adaugare_referendum').css('display', '');
                $('#alerta_adaugare_referendum').addClass('alert-success');
                $('#alerta_adaugare_referendum').html('S-a adaugat referendumul!');
            } else {
                console.log("Eroare la adaugarea referendumului: " + data.mesajConsola);

                $('#alerta_adaugare_referendum').css('display', '');
                $('#alerta_adaugare_referendum').addClass('alert-danger');
                if (data.mesajUtilizator == '' || data.mesajUtilizator == undefined) {
                    $('#alerta_adaugare_referendum').html('Nu s-a adaugat referendumul! EROARE SERVER');
                } else {
                    $('#alerta_adaugare_referendum').html(data.mesajUtilizator);
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la adaugarea referendumului");

            $('#alerta_adaugare_referendum').css('display', '');
            $('#alerta_adaugare_referendum').addClass('alert-danger');
            $('#alerta_adaugare_referendum').html('Nu s-a adaugat referendumul! EROARE SERVER');
        }
    });
}

function getOptiuni() {
    var optiuni = '';
    var nrOptiuniCuContinut = 0;
    $('input[name=optiune]').each(function (i, elem) {
        if ($(this).val() != '') {
            nrOptiuniCuContinut++;
            if (optiuni == '') {
                optiuni += $(this).val();
            } else {
                optiuni += ';' + $(this).val();
            }
        }
    });

    if (nrOptiuniCuContinut < 2) {
        return false;
    } else {
        return optiuni;
    }
}

function adaugareStire(data) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var titluStire = $('#titlu_stire').val();
    var previewStire = $('#preview_stire').val();
    var tipStire = $('#tip_stire option:selected').val();
    var continutStire = $('#continut_stire').val();
    var anuntStire;

    $('#alerta_adaugare_stire').removeClass('alert-success');
    $('#alerta_adaugare_stire').removeClass('alert-danger');
    $('#alerta_adaugare_stire').empty();

    if (titluStire == '') {
        $('#alerta_adaugare_stire').css('display', '');
        $('#alerta_adaugare_stire').addClass('alert-danger');
        $('#alerta_adaugare_stire').html('Titlul nu poate fi gol!');
        return;
    }

    if (previewStire == '') {
        $('#alerta_adaugare_stire').css('display', '');
        $('#alerta_adaugare_stire').addClass('alert-danger');
        $('#alerta_adaugare_stire').html('Preview-ul nu poate fi gol!');
        return;
    }

    if (tipStire == undefined || tipStire == '') {
        $('#alerta_adaugare_stire').css('display', '');
        $('#alerta_adaugare_stire').addClass('alert-danger');
        $('#alerta_adaugare_stire').html('Trebuie selectat un tip de stire!');
        return;
    }

    if (continutStire == '') {
        $('#alerta_adaugare_stire').css('display', '');
        $('#alerta_adaugare_stire').addClass('alert-danger');
        $('#alerta_adaugare_stire').html('Continutul stirii nu poate fi gol!');
        return;
    }

    if ($('#checkbox_anunt').is(':checked')) {
        anuntStire = 1;
    } else {
        anuntStire = 0;
    }

//    var datePost = '&titluStire=' + titluStire + '&previewStire=' + previewStire
//            + '&tipStire=' + tipStire + '&continutStire=' + continutStire
//            + '&numeUser=' + numeUser + '&anuntStire=' + anuntStire;

    $.ajax({
        type: 'POST',
        url: root + "/adaugare_stire",
        contentType: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: data,
        processData: false,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                $('#titlu_stire').val('');
                $('#preview_stire').val('');
                $('#continut_stire').val('');

                $('#alerta_adaugare_stire').css('display', '');
                $('#alerta_adaugare_stire').addClass('alert-success');
                $('#alerta_adaugare_stire').html('S-a adaugat stirea!');
            } else {
                console.log("Eroare la adaugarea stirii: " + data.mesajConsola);

                $('#alerta_adaugare_stire').css('display', '');
                $('#alerta_adaugare_stire').addClass('alert-danger');
                $('#alerta_adaugare_stire').html('Nu s-a adaugat stirea! EROARE SERVER');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la adaugarea stirii");

            $('#alerta_adaugare_stire').css('display', '');
            $('#alerta_adaugare_stire').addClass('alert-danger');
            $('#alerta_adaugare_stire').html('Nu s-a adaugat stirea! EROARE SERVER');
        }
    });
}