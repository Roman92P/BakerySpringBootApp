document.addEventListener("DOMContentLoaded", function () {
    console.log("DOM fully loaded and parsed!");
    let element = document.querySelector('#litrProduct');
    let element1 = document.querySelector('#quantityProduct');
    let element2 = document.querySelector('#weightProduct');


    let htmlElement = document.getElementById("type");
    htmlElement.addEventListener("change", function (event) {
        if (htmlElement.value === 'litr') {

            element.style.display = 'block';
            element1.style.display = 'none';
            element2.style.display = 'none';


        } else if (htmlElement.value === 'quantity') {
            element.style.display = 'none';
            element1.style.display = 'block';
            element2.style.display = 'none';


        } else if (htmlElement.value === 'wieght') {

            element.style.display = 'none';
            element1.style.display = 'none';
            element2.style.display = 'block';


        } else {
            element.style.display = 'block';

        }
    })
})