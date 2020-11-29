document.addEventListener("DOMContentLoaded", function () {
    console.log("DOM fully loaded and parsed!");


    let litrInput = document.querySelector("#l");
    let quantityInput = document.querySelector("#q");
    let weightInput = document.querySelector("#w");

    let element = document.querySelector('#forInput');

    let element1 = document.querySelector('#litr');
    let element2 = document.querySelector('#weight');
    let element3 = document.querySelector('#quantity');

    let title = document.querySelector("#title");

    let lin = document.querySelector("#lin");
    let win = document.querySelector("#win");
    let qin = document.querySelector("#qin");



    let htmlElement = document.getElementById("type");
    htmlElement.addEventListener("change", function (event){
        if(htmlElement.value==='litr'){

            element1.style.display='block';
            element2.remove();
            element3.remove();
            htmlElement.childNodes.style.display='none'

        } else if(htmlElement.value==='quantity'){

            element3.style.display='block';
            element2.remove();
            element1.remove();
            htmlElement.childNodes.style.display='none'



        } else if(htmlElement.value==='wieght'){

            element2.style.display='block';
            element3.remove();
            element1.remove();
            htmlElement.childNodes.style.display='none'


        } else{

        }
    })
});



