 document.getElementById("choix").addEventListener("click", function() {
                let selectElmt = document.getElementById("choix");
                varsel = selectElmt.options[selectElmt.selectedIndex].value;
                if(varsel=="fr"){
                window.location.href = "/categories/" + window["varsel"];
                window.onload= function(){
                    document.documentElement.setAttribute("lang","fr");
                };
                }else if(varsel=="en"){
                    window.location.href = "/categories/"+ window["varsel"];
                    document.documentElement.setAttribute("lang","en");
                }
                }, false);