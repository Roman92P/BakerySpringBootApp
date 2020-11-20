let element = document.querySelector("#type");

let element4 = document.querySelector("#litr");
let element5 = document.querySelector("#quantity");
let element6 = document.querySelector("#weight");
console.log(element4);
console.log(element5);
console.log(element6);
element.addEventListener("change", function (e) {

    if (element.value == "litr") {
        console.log(1)
        element4.style.display = "block";
        element5.style.display = "none";
        element6.style.display = "none";

    } else if (element.value == "quantity") {
        console.log(2)
        element5.style.display = "block";
        element6.style.display = "none";
        element4.style.display = "none";
    } else {
        console.log(3)
        element6.style.display = "block";
        element4.style.display = "none";
        element5.style.display = "none";

    }

})