document.addEventListener("DOMContentLoaded", function () {

    let litr = document.querySelector('#lit');
    let quantity = document.querySelector('#quan');
    let weight = document.querySelector('#weig');
    let element = document.querySelector('#toDelIfAllNull');


    let element1 = document.querySelector('#addBtn');


    element1.addEventListener('click',function (){
        let op = document.querySelector('#untOfMs');

        op.addEventListener("change", function () {
            element.remove();
            if (op.value === "l") {
                litr.style.display = 'block';
                if (litr.getAttribute('th:field') === null) {
                    litr.setAttribute('th:field', '*{litr}');
                }
                if (litr.getAttribute('th:value') !== null && litr.getAttribute('th:attr') !== null) {
                    litr.removeAttribute('th:value');
                    litr.removeAttribute('th:attr');
                }
                quantity.style.display = 'none';
                if (quantity.getAttribute('th:field') !== null) {
                    quantity.removeAttribute('th:field')
                }
                if (quantity.getAttribute('th:value') === null && quantity.getAttribute('th:attr') === null) {
                    // quantity.setAttribute('th:value','0.0');
                    quantity.value = '0';
                    quantity.setAttribute('th:attr', 'name=\'quantity\'');
                }
                weight.value.display = 'none';
                if (weight.getAttribute('th:field') !== null) {
                    weight.removeAttribute('th:field')
                }
                if (weight.getAttribute('th:value') === null && weight.getAttribute('th:attr') === null) {
                    // weight.setAttribute('th:value','0.0');
                    weight.value = '0';
                    weight.setAttribute('th:attr', 'name=\'weight\'');
                }
            }

            if (op.value === "q") {
                quantity.style.display = 'block';
                if (quantity.getAttribute('th:field') === null) {
                    quantity.setAttribute('th:field', '*{quantity}');
                }
                if (quantity.getAttribute('th:value') !== null && quantity.getAttribute('th:attr') !== null) {
                    quantity.removeAttribute('th:value');
                    quantity.removeAttribute('th:attr');
                }

                litr.style.display = 'none';
                if (litr.getAttribute('th:field') !== null) {
                    litr.removeAttribute('th:field')
                }
                if (litr.getAttribute('th:value') === null && litr.getAttribute('th:attr') === null) {
                    // litr.setAttribute('th:value', '0.0');
                    litr.value = '0';
                    litr.setAttribute('th:attr', 'name=\'litr\'');
                }
                weight.style.display = 'none'
                weight.value = '0';
                if (weight.getAttribute('th:field') !== null) {
                    weight.removeAttribute('th:field')
                }
                if (weight.getAttribute('th:value') === null && weight.getAttribute('th:attr') === null) {
                    weight.value = '0';
                    weight.setAttribute('th:attr', 'name=\'weight\'');
                }
            }

                if (op.value === "w") {
                    weight.style.display = 'block';
                    if (weight.getAttribute('th:field') === null) {
                        weight.setAttribute('th:field', '*{weight}');
                    }
                    if (weight.getAttribute('th:value') !== null && weight.getAttribute('th:attr') !== null) {
                        weight.removeAttribute('th:value');
                        weight.removeAttribute('th:attr');
                    }
                    litr.style.display = 'none'
                    if (litr.getAttribute('th:field') !== null) {
                        litr.removeAttribute('th:field')
                    }
                    if (litr.getAttribute('th:value') === null && litr.getAttribute('th:attr') === null) {
                        litr.value = '0';
                        litr.setAttribute('th:attr', 'name=\'litr\'');
                    }
                    quantity.style.display = 'none'
                    if (quantity.getAttribute('th:field') !== null) {
                        quantity.removeAttribute('th:field')
                    }
                    if (quantity.getAttribute('th:value') === null && quantity.getAttribute('th:attr') === null) {
                        quantity.value = '0';
                        quantity.setAttribute('th:attr', 'name=\'quantity\'');
                    }

                }
        })


    })



})