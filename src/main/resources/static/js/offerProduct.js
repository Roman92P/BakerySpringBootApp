document.addEventListener("DOMContentLoaded", function () {

    let element = document.querySelectorAll('#imgHolder');

    let element1 = document.querySelector('#container');

    element.forEach(function (image, index) {
        // console.log(image)
        // console.log(image.getAttribute('value'))
        let productId = image.getAttribute('value');
        new Promise((resolve, reject) => {
            fetch('/products/allImages/' + productId).then(response => {
                return response.json();
            }).then(data => {
                console.log("Result form response: ", data)
                //<img height="150" width="150" alt="product"
                //                                          th:src="*{'data:image/png;base64,'+photo}"/>
                // <!--<img class="info" th:attr="src=@{${image}}" />-->
                //<div>
                //     <p>Taken from wikpedia</p>
                //     <img src="data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUA
                // AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
                //     9TXL0Y4OHwAAAABJRU5ErkJggg==" alt="Red dot" />
                // </div>
                let htmlImageElement = document.createElement('img');
                // let htmlImageElement1 = document.createElement('img');
                htmlImageElement.setAttribute('height', '150');
                htmlImageElement.setAttribute('width', '150');
                htmlImageElement.setAttribute('alt', 'product');
                htmlImageElement.setAttribute('src', "data:image/png;base64, "+data);

                // htmlImageElement1.setAttribute('class', 'info');
                // htmlImageElement1.setAttribute('attr', 'src=@{${'+data+'}}');
                image.append(htmlImageElement);
                // element1.append(htmlImageElement1);
            })
        }).catch(error => {
            console.log("Error during fetch: ",index);
        })
    })

})