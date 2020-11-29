document.addEventListener("DOMContentLoaded", function () {

    let firstCheck;
    let secondCheck;

    new Promise((resolve, reject) => {
        fetch('bakery/ingredients/minimumQuantity').then(response => {
            return response.json();
        }).then(data => {
            console.log("Result form response: ", data)
            if(data>0){
                firstCheck=data;
                $(window).on('load', function () {
                    $('#myModal').modal('show');
                });
            }
        })
    }).catch(error => {
        console.log("Error during fetch: ",index);
    })


    new Promise((resolve, reject) => {
        fetch('bakery/ingredients/zeroQuantity').then(response => {
            return response.json();
        }).then(data => {
            console.log("Result form response: ", data)
            if(data>0){
                secondCheck=data;
                $(window).on('load', function () {
                    $('#myModal').modal('show');
                });
            }
        })
    }).catch(error => {
        console.log("Error during fetch: ",index);
    })

    if(secondCheck>0 || firstCheck>0) {

    }

})