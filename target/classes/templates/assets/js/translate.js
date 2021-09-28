 document.getElementById("choix").addEventListener("change", function() {
     let selectElmt = document.getElementById("choix");
     varsel = selectElmt.options[selectElmt.selectedIndex].value;
     if (varsel == "fr") {
         window.location.href = "/categories/" + window["varsel"];
         localStorage.setItem('selectedtem', varsel);
     } else if (varsel == "en") {
         window.location.href = "/categories/" + window["varsel"];
         localStorage.setItem('selectedtem', varsel);
     }
 }, false);
 if (localStorage.getItem('selectedtem')) {
     document.getElementById('select_' + localStorage.getItem('selectedtem')).selected = true;
 }