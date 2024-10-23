$("#theme-switch").on("click", function() {
    if ($(this).prop('checked')) {
        $("#light-theme").prop("checked", false);
        $("#dark-theme").prop("checked", true);
        $("body").css("background", "#2F2E2E");
        $("footer").attr("class", "dark");
        // store until close browser
        sessionStorage.setItem("elec5619-theme", "dark");
    } else {
        $("#dark-theme").prop("checked", false);
        $("#light-theme").prop("checked", true);
        $("body").css("background", "#F7F7F7");
        $("footer").attr("class", "");
        // store until close browser
        sessionStorage.setItem("elec5619-theme", "light");
    }
});

var is_light_theme= sessionStorage.getItem("elec5619-theme");
if (is_light_theme == null || is_light_theme == "light") is_light_theme = true;
else is_light_theme = false;
if (!is_light_theme && !$("#theme-switch").prop('checked')) {
    $("#theme-switch").click();
} else if (is_light_theme && $("#theme-switch").prop('checked')) {
    $("#theme-switch").click();
}

function logout() {
    axios.post('/logout')
        .then(function (response) {
            window.open('/', "_self");

        })
        .catch(function (error) {
            console.log(error.response.data);
        });
    // return back to unauthenticated conn
}

function archive() {
    window.open('/archive', "_self");
    // document.querySelector('.content').style.display = 'block';
    // document.querySelector('.content1').style.display = 'none';
}


function generator() {
    window.open('/home', "_self");
}

// function toggleTitDiv() {
//     const titDiv = document.querySelector('.titdiv');
//     if (titDiv) {
//         titDiv.style.display = titDiv.style.display === 'none' ? 'block' : 'none';
//     }
// }

// function showContent1() {
//     document.querySelector('.content').style.display = 'none';
//     document.querySelector('.content1').style.display = 'block';
// }
//
// function returnToContent() {
//     document.querySelector('.content').style.display = 'block';
//     document.querySelector('.content1').style.display = 'none';
// }
// PDF download
// document.getElementById('download-pdf').addEventListener('click', function() {
//     const { jsPDF } = window.jspdf;
//     const doc = new jsPDF();
//
//     // Gets the content to export, assuming the entire page content is in .treeContent
//     let content = document.querySelector('.dialogue-list').innerText;
//
//     doc.text(content, 10, 10);
//
//     doc.save('page-content.pdf');
// });

// JSON download
// document.getElementById('download-json').addEventListener('click', function() {
//     let content = document.querySelector('.dialogue-list').innerText;
//     let jsonContent = JSON.stringify({ content: content });
//
//     const a = document.createElement('a');
//     a.href = 'data:text/json;charset=utf-8,' + encodeURIComponent(jsonContent);
//     a.download = 'page-content.json';
//     a.click();
// });
//
// // TXT download
// document.getElementById('download-txt').addEventListener('click', function() {
//     let content = document.querySelector('.dialogue-list').innerText;
//
//     // creat the txt
//     const a = document.createElement('a');
//     a.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(content);
//     a.download = 'page-content.txt';
//     a.click();
// });

// Event listeners
// document.querySelector(".tit svg").addEventListener("click", toggleTitDiv);
// document.querySelector(".link").addEventListener("click", showContent1);
// document.querySelector(".return").addEventListener("click", returnToContent);

// $('textarea').keyup(function(){
//     var lim = parseInt($("#word-lim-per-textarea").text());
//     var raw = $(this).val().trim();
//     var str;
//     if (raw==='') str=[];
//     else str=raw.split(/[\s]+/);
//     var count = str.length;
//
//     var status= 'primary';
//     if (count >= lim) {
//         var str_new = str.splice(0, lim).join(' ');
//         // overwrite content
//         $(this).val(str_new);
//         count=lim;
//         status='danger';
//     } else {
//         if (count >= lim/2) status = 'purple';
//     }
//     $(this).parent().find('span').removeClass()
//                                  .addClass('word-count '+status)
//                                  .text(count + ' / ' + lim);
//     countTotal($(this).parents());
// });

function wordCountTotal(obj) {
    var tot = 0;
    var max = 0;

    obj.find('textarea').each(function () {
        // console.log($(this));
        var str;
        const raw = $(this).val();
        if (countStr(raw)===0) str=[]
        else str = raw.trim().split(/\s+/);
        tot+=str.length;
        max += parseInt($("#word-lim-per-textarea").text());
    });
    var status = 'success';
    if (tot >= max/2) status = 'orange';
    if (tot >= max) status = 'danger';

    obj.parents().find('.counter').removeClass()
                                  .addClass('counter total-count '+status)
                                  .text(tot);

    obj.parents().find('.counter-limit').text(max);
}

function countStr(str) {
    // Split the string by spaces and filter out empty strings
    const arr = str.trim().split(/\s+/).filter(word => word.length > 0);
    return arr.length;
}
function wordCount(obj) {
    var lim = parseInt($("#word-lim-per-textarea").text());
    var raw = $(obj).val();
    var str;
    if (countStr(raw)===0) str=[];
    else str=raw.trim().split(/\s+/);
    var count = str.length;
    console.log(raw);
    var status= 'primary';
    if (count >= lim) {
        var str_new = str.splice(0, lim).join(' ');
        // overwrite content
        $(obj).val(str_new);
        count=lim;
        status='danger';
    } else {
        if (count >= lim/2) status = 'purple';
    }
    $(obj).parent().find('span').removeClass()
        .addClass('word-count '+status)
        .text(count + ' / ' + lim);

    wordCountTotal($(obj).parents());
}