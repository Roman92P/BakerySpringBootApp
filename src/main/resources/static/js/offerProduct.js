document.addEventListener("DOMContentLoaded", function () {

    let element = document.querySelectorAll('#imgHolder');

    let element1 = document.querySelector('#container');

    element.forEach(function (image, index) {
        let productId = image.getAttribute('value');
        new Promise((resolve, reject) => {
            fetch('/products/allImages/' + productId).then(response => {
                return response.json();
            }).then(data => {
                console.log("Result form response: ", data)
                let htmlImageElement = document.createElement('img');
                htmlImageElement.setAttribute('height', '150');
                htmlImageElement.setAttribute('width', '150');
                htmlImageElement.setAttribute('alt', 'product');
                htmlImageElement.setAttribute('src', "data:image/png;base64, "+data);
                image.append(htmlImageElement);
            })
        }).catch(error => {
            console.log("Error during fetch: ",index);
        })
    })

})