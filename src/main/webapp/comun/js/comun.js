/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function adaugareComentariuInitiativa(numeUser, idInitiativa) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var comentariu = $('#comment_opinie').val();

    if (comentariu == '') {
        return;
    }

    var datePost = '&nume_user=' + numeUser + '&id_initiativa=' + idInitiativa
            + '&comentariu=' + comentariu;

    $.ajax({
        type: 'POST',
        url: root + "/adaugare_opinie_initiativa",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                var date = new Date();
                var dataFormatata = date.getDate() + '.' + (date.getMonth() + 1)
                        + '.' + date.getFullYear() + ' ' + date.getHours()
                        + ':' + date.getMinutes() + ':' + date.getSeconds();
                var html = '<div class="opinie">'
                        + '<p class="autor_opinie">' + numeUser + '</p>'
                        + '<p class="data">' + dataFormatata + '</p>'
                        + '<p>' + comentariu + '</p>'
                        + '</div>';
                $('#div_lista_opinii').append(html);
                $('#comment_opinie').val('');
            } else {
                console.log("Eroare la adaugarea comentariului: " + data.mesajConsola);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la adaugarea comentariului");
        }
    });
}

function selectareLikeDislike(numeUser, idInitiativa, like) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    if (like != 0 && like != 1) {
        return;
    }

    var datePost = '&nume_user=' + numeUser + '&id_initiativa=' + idInitiativa
            + '&like=' + like;

    $.ajax({
        type: 'POST',
        url: root + "/modificare_like_initiativa",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                switch (likeUserCurent) {
                    case - 1: //fara like sau dislike
                        if (like == 0) {
                            $('#dislike').addClass('button_like_active');
                            var nrDislike = parseInt($('#nr_dislike').text());
                            nrDislike++;
                            $('#nr_dislike').text(nrDislike);
                        } else if (like == 1) {
                            $('#like').addClass('button_like_active');
                            var nrLike = parseInt($('#nr_like').text());
                            nrLike++;
                            $('#nr_like').text(nrLike);
                        }
                        break;

                    case 0: //cu dislike deja
                        if (like == 0) {
                            $('#dislike').removeClass('button_like_active');
                            var nrDislike = parseInt($('#nr_dislike').text());
                            nrDislike--;
                            $('#nr_dislike').text(nrDislike);
                        } else if (like == 1) {
                            $('#dislike').removeClass('button_like_active');
                            var nrDislike = parseInt($('#nr_dislike').text());
                            nrDislike--;
                            $('#nr_dislike').text(nrDislike);

                            $('#like').addClass('button_like_active');
                            var nrLike = parseInt($('#nr_like').text());
                            nrLike++;
                            $('#nr_like').text(nrLike);
                        }
                        break;

                    case 1: //cu like deja
                        if (like == 0) {
                            $('#like').removeClass('button_like_active');
                            var nrLike = parseInt($('#nr_like').text());
                            nrLike--;
                            $('#nr_like').text(nrLike);

                            $('#dislike').addClass('button_like_active');
                            var nrDislike = parseInt($('#nr_dislike').text());
                            nrDislike++;
                            $('#nr_dislike').text(nrDislike);
                        } else if (like == 1) {
                            $('#like').removeClass('button_like_active');
                            var nrLike = parseInt($('#nr_like').text());
                            nrLike--;
                            $('#nr_like').text(nrLike);
                        }
                        break;
                }

                likeUserCurent = like;
            } else {
                console.log("Eroare la modificarea like-ului: " + data.mesajConsola);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la modificarea like-ului");
        }
    });
}

function adaugareInitiativa(numeUser) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var titlu = $('#titlu_initiativa').val();
    var continut = $('#continut_initiativa').val();

    $('#alerta_adaugare_initiativa').removeClass('alert-success');
    $('#alerta_adaugare_initiativa').removeClass('alert-danger');
    $('#alerta_adaugare_initiativa').empty();

    if (titlu == '') {
        $('#alerta_adaugare_initiativa').css('display', '');
        $('#alerta_adaugare_initiativa').addClass('alert-danger');
        $('#alerta_adaugare_initiativa').html('Titlul nu poate fi gol!');
        return;
    }

    if (continut == '') {
        $('#alerta_adaugare_initiativa').css('display', '');
        $('#alerta_adaugare_initiativa').addClass('alert-danger');
        $('#alerta_adaugare_initiativa').html('Textul initiativei nu poate fi gol!');
        return;
    }

    var datePost = '&nume_user=' + numeUser + '&titlu=' + titlu
            + '&continut=' + continut;

    $.ajax({
        type: 'POST',
        url: root + "/adaugare_initiativa",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                $('#titlu_initiativa').val('');
                $('#continut_initiativa').val('');

                $('#alerta_adaugare_initiativa').css('display', '');
                $('#alerta_adaugare_initiativa').addClass('alert-success');
                $('#alerta_adaugare_initiativa').html('S-a adaugat initiativa!');
            } else {
                console.log("Eroare la adaugarea initiativei: " + data.mesajConsola);

                $('#alerta_adaugare_initiativa').css('display', '');
                $('#alerta_adaugare_initiativa').addClass('alert-danger');
                $('#alerta_adaugare_initiativa').html('Nu s-a adaugat initiativa! EROARE SERVER');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la adaugarea initiativei");

            $('#alerta_adaugare_initiativa').css('display', '');
            $('#alerta_adaugare_initiativa').addClass('alert-danger');
            $('#alerta_adaugare_initiativa').html('Nu s-a adaugat initiativa! EROARE SERVER');
        }
    });
}

function getDetaliiReferendum(idReferendum) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");

    var datePost = '&id_referendum=' + idReferendum;

    $.ajax({
        type: 'POST',
        url: root + "/statistica_referendum",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        data: datePost,
        success: function (data, textStatus, jqXHR) {
            if (data.codRetur == 0) {
                var referendum = data.objectResponse;
                $('#intrebare_referendum').text(referendum.intrebare);
                $('#creator_referendum').append(referendum.userCreare.userName);
                $('#data_referendum').append(referendum.dataReferendumFormatata);

                var htmlStatistici = '';
                htmlStatistici += '<p style="font-size: 16px; font-weight: bold;">Participare la referendum</p>';
                htmlStatistici += '<div class="progress">'
                        + '<div class="progress-bar ' + getTypeProgressBar(referendum.procentParticipare) + '" role="progressbar" '
                        + 'aria-valuenow="' + referendum.procentParticipare + '" '
                        + 'aria-valuemin="0" aria-valuemax="100" style="width:' + referendum.procentParticipare + '%">'
                        + referendum.procentParticipare + '%'
                        + '</div>'
                        + '</div>';
                $.each(referendum.listaOptiuni, function (i, elem) {
                    htmlStatistici += '<p style="font-size: 16px; font-weight: bold;">Procent la raspuns <b>' 
                            + elem.textOptiune + '</b></p>';
                    htmlStatistici += '<div class="progress">'
                            + '<div class="progress-bar ' + getTypeProgressBar(elem.procentVot) + '" role="progressbar" '
                            + 'aria-valuenow="' + elem.procentVot + '" '
                            + 'aria-valuemin="0" aria-valuemax="100" style="width:' + elem.procentVot + '%">'
                            + elem.procentVot + '%'
                            + '</div>'
                            + '</div>';
                });
                
                $('#statistici_referendum').html(htmlStatistici);
            } else {
                console.log("Eroare la obtinerea informatiilor despre referendum: "
                        + data.mesajConsola);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Eroare la obtinerea informatiilor despre referendum");
        }
    });
}

function getTypeProgressBar(procent) {
    var progressBar = '';
    if (procent < 30) {
        progressBar = 'progress-bar-danger';
    } else if (procent >= 30 && procent < 50) {
        progressBar = 'progress-bar-warning';
    } else if (procent >= 50 && procent < 70) {
        progressBar = 'progress-bar-info';
    } else if (procent > 70) {
        progressBar = 'progress-bar-success';
    }

    return progressBar;
}