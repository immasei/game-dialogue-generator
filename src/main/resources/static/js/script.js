$("#theme-switch").on("click", function() {
    if ($(this).prop('checked')) {
        $("#light-theme").prop("checked", false);
        $("#dark-theme").prop("checked", true);
        $("body").css("background", "#2F2E2E");
        $("footer").attr("class", "dark");
    } else {
        $("#dark-theme").prop("checked", false);
        $("#light-theme").prop("checked", true);
        $("body").css("background", "#F7F7F7");
        $("footer").attr("class", "");
    }
});

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
}

function generator() {
    window.open('/home', "_self");
}

function toggleTitDiv() {
    const titDiv = document.querySelector('.titdiv');
    if (titDiv) {
        titDiv.style.display = titDiv.style.display === 'none' ? 'block' : 'none';
    }
}

function showContent1() {
    document.querySelector('.content').style.display = 'none';
    document.querySelector('.content1').style.display = 'block';
}

function returnToContent() {
    document.querySelector('.content').style.display = 'block';
    document.querySelector('.content1').style.display = 'none';
}
// PDF download
document.getElementById('download-pdf').addEventListener('click', function() {
    const { jsPDF } = window.jspdf;

    const doc = new jsPDF();

    // Gets the content to export, assuming the entire page content is in .treeContent
    let content = document.querySelector('.treeContent').innerText;

    doc.text(content, 10, 10);

    doc.save('page-content.pdf');
});

// JSON download
document.getElementById('download-json').addEventListener('click', function() {
    let content = document.querySelector('.treeContent').innerText;
    let jsonContent = JSON.stringify({ content: content });

    const a = document.createElement('a');
    a.href = 'data:text/json;charset=utf-8,' + encodeURIComponent(jsonContent);
    a.download = 'page-content.json';
    a.click();
});

// TXT download
document.getElementById('download-txt').addEventListener('click', function() {
    let content = document.querySelector('.treeContent').innerText;

    // creat the txt
    const a = document.createElement('a');
    a.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(content);
    a.download = 'page-content.txt';
    a.click();
});

// Event listeners
document.querySelector(".tit svg").addEventListener("click", toggleTitDiv);
document.querySelector(".link").addEventListener("click", showContent1);
document.querySelector(".return").addEventListener("click", returnToContent);