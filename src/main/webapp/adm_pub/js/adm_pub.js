/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function adaugareReferendum(numeUser) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var intrebare = $('#intrebare_referendum').val();
    var data = $('#data_referendum').val();
    var optiuni = getOptiuni();
    
    $('#alerta_adaugare_referendum').removeClass('alert-success');
    $('#alerta_adaugare_referendum').removeClass('alert-danger');
    $('#alerta_adaugare_referendum').empty();

    if (intrebare == '') {
        $('#alerta_adaugare_referendum').css('display', '');
        $('#alerta_adaugare_referendum').addClass('alert-danger');
        $('#alerta_adaugare_referendum').html('Intrebarea nu poate fi goala!');
        return;
    }

    if (data == '') {
        $('#alerta_adaugare_referendum').css('display', '');
        $('#alerta_adaugare_referendum').addClass('alert-danger');
        $('#alerta_adaugare_referendum').html('Trebuie completata data!');
        return;
    }
    
    if (optiuni == false) {
        $('#alerta_adaugare_referendum').css('display', '');
        $('#alerta_adaugare_referendum').addClass('alert-danger');
        $('#alerta_adaugare_referendum').html('Trebuie completate cel putin doua optiuni de raspuns!');
        return;
    }

    var datePost = '&intrebare=' + intrebare + '&data=' + data
            + '&optiuni=' + optiuni + '&nume_user=' + numeUser;

    $.ajax({
        type: 'POST',
        url: root + "/adaugare_referendum",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
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
                $('#alerta_adaugare_referendum').html('Nu s-a adaugat referendumul! EROARE SERVER');
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
        if (optiuni == '') {
            optiuni += $(this).val();
        } else {
            optiuni += ';' + $(this).val();
        }
        if ($(this).val() != '') {
            nrOptiuniCuContinut++;
        }
    });

    if (nrOptiuniCuContinut < 2) {
        return false;
    } else {
        return optiuni;
    }
}