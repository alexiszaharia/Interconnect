/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function votareReferendum(idReferendum, numeUser) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var intrebari = '';

    $('.class_intrebare_referendum').each(function () {
        var intrebare = $(this).find('input[name=id_intrebare]').val();
        var valoareOptiune = $(this).find('input[name=optiune_referendum]:checked').val();

        if (valoareOptiune == undefined || valoareOptiune == '') {
            $('#alerta_votare_referendum').css('display', '');
            $('#alerta_votare_referendum').addClass('alert-danger');
            $('#alerta_votare_referendum').html('Nu s-a furnizat raspuns!');
            return;
        }
        
        if (intrebari == '') {
            intrebari += intrebare + ',' + valoareOptiune;
        } else {
            intrebari += ';' + intrebare + ',' + valoareOptiune;
        }
    });

//    var valoareOptiune = $('input[name=optiune_referendum]:checked').val();



    var datePost = '&id_referendum=' + idReferendum + '&intrebari='
            + intrebari + '&nume_user=' + numeUser;

    $.ajax({
        type: 'POST',
        url: root + "/votare_referendum",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                $('#div_referendum').empty();
                $('#div_referendum').html(
                        '<a href="<%=request.getContextPath()%>/istoric_referendumuri/detalii_referendum/' + idReferendum + '">'
                        + 'Vizualizare detalii referendum'
                        + '</a>');
            } else {
                console.log("Eroare la votarea referendumului: " + data.mesajConsola);

                $('#alerta_votare_referendum').css('display', '');
                $('#alerta_votare_referendum').addClass('alert-danger');
                $('#alerta_votare_referendum').html('Nu s-a inregistrat optiunea! EROARE SERVER');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la votarea referendumului");

            $('#alerta_votare_referendum').css('display', '');
            $('#alerta_votare_referendum').addClass('alert-danger');
            $('#alerta_votare_referendum').html('Nu s-a inregistrat optiunea! EROARE SERVER');
        }
    });
}
