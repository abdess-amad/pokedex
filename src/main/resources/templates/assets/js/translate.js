 document.getElementById("choix").addEventListener("click", function() {
                let selectElmt = document.getElementById("choix");
                varsel = selectElmt.options[selectElmt.selectedIndex].value;
                if(varsel=="fr"){
                window.location.href = "/categories/fr" + window["varsel"];
                }else if(varsel=="en"){
                    window.location.href = "/categories/en";
                }
                }, false);