document.addEventListener("DOMContentLoaded", function () {
    console.log("DOM fully loaded and parsed!");

    let btn = document.querySelector("#submit");
    btn.innerHTML = "Edit";

    let litrInput = document.querySelector("#litrInput");
    let quantityInput = document.querySelector("#quantityInput");
    let weightInput = document.querySelector("#weightInput");

    let htmlElement = document.getElementById("type");
        htmlElement.addEventListener("change", function (event){
        if(htmlElement.value==='litr'){
            litrInput.style.display='block';
            quantityInput.style.display='none';
            weightInput.style.display='none';
        } else if(htmlElement.value==='quantity'){
            quantityInput.style.display='block';
            weightInput.style.display='none';
            litrInput.style.display='none';
        } else if(htmlElement.value==='wieght'){
            weightInput.style.display='block';
            quantityInput.style.display='none';
            litrInput.style.display='none';
        } else{
            weightInput.style.display='none';
            quantityInput.style.display='none';
            litrInput.style.display='none';
        }
    })
});


