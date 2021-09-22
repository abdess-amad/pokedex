 let count =document.getElementById("count").value;
       let pre =document.getElementById("pre");
       if(count <=1)
       {
            pre.style.display = "none";
       }
       else{
           pre.style.display= "block";
       }
       let verifNext =document.getElementById("verifNext").value;
       let nex =document.getElementById("nex");
        if(verifNext >=0)
       {
            nex.style.display = "none";
       }
       else{
           nex.style.display= "block";
       }