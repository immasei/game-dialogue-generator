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

// Event listeners
document.querySelector(".tit svg").addEventListener("click", toggleTitDiv);
document.querySelector(".link").addEventListener("click", showContent1);
document.querySelector(".return").addEventListener("click", returnToContent);