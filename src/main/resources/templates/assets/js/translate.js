 document.getElementById("choix").addEventListener("click", function() {
                let selectElmt = document.getElementById("choix");
                varsel = selectElmt.options[selectElmt.selectedIndex].value;
                if(varsel=="fr"){
                window.location.href = "/categories/" + window["varsel"];
                }else if(varsel=="en"){
                    window.location.href = "/category";
                }
                }, false);