document.addEventListener("DOMContentLoaded", function () {
    console.log("DOM fully loaded and parsed!");

    //<label id="litr">-->
    // <!--                                            <div class="text-xs font-weight-bold  text-uppercase mb-1">Litre-->
    // <!--                                                unit of measurement-->
    // <!--                                            </div>-->
    // <!--                                            <div class="col-6">-->
    // <!--                                                <input th:field="*{litr}" type="number" step="0.01"/>-->
    // <!--                                                <input hidden th:value="0" th:attr="name='weight'">-->
    // <!--                                                <input hidden th:value="0" th:attr="name='quantity'">-->
    // <!--                                            </div>-->
    // <!--                                        </label>-->


    //<label id="litr" style="display: none">
    //                                                 <div class="text-xs font-weight-bold  text-uppercase mb-1">Litre
    //                                                     unit of measurement
    //                                                 </div>
    //                                                 <div class="col-6">
    //                                                     <input th:field="*{litr}" type="number" step="0.01"/>
    //                                                     <input hidden th:value="0" th:attr="name='weight'">
    //                                                     <input hidden th:value="0" th:attr="name='quantity'">
    //                                                 </div>
    //                                             </label>

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
//
//
//     let htmlElement = document.getElementById("type");
//
//     htmlElement.addEventListener("change", function (event){
//         if(htmlElement.value==='litr'){
//
//             element1.style.display='block';
//
//
//
//         } else if(htmlElement.value==='quantity'){
//
//             element1.style.display='block';
//             title.innerText='Psc unit of measurement';
//             lin.removeAttribute('th:field');
//             lin.removeAttribute('type');
//             lin.removeAttribute("step");
//             qin.removeAttribute('hidden');
//             qin.removeAttribute('th:value');
//             qin.removeAttribute('th:attr');
//             lin.setAttribute('type','hidden');
//             lin.setAttribute('th:value','0');
//             lin.setAttribute('th:attr','name="litr"');
//             qin.setAttribute('th:field','*{quantity}');
//             qin.setAttribute('type','number');
//             qin.setAttribute('step','0.01');
//
//
//         } else if(htmlElement.value==='wieght'){
//
//             element1.style.display='block';
//             title.innerText='Psc unit of measurement'
//
//         } else{
//
//         }
//     })
// });


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
//     htmlElement.addEventListener("change", function (event){
//         if(htmlElement.value==='litr'){
//             if(document.querySelector("#inputLabel")!=null){
//                 document.querySelector('#inputLabel').remove()
//             }
//             let htmlLabelElement = document.createElement('label');
//             let htmlDivElementTitle = document.createElement('div');
//             let htmlDivElementInput = document.createElement('div');
//             let htmlLitrInput = document.createElement('input');
//             let htmlWeightInput = document.createElement('input');
//             let htmlQuantityInput = document.createElement('input');
//             htmlLabelElement.setAttribute('id', 'inputLabel')
//             htmlDivElementTitle.classList.add('text-xs');
//             htmlDivElementTitle.classList.add('font-weight-bold');
//             htmlDivElementTitle.classList.add('text-uppercase');
//             htmlDivElementTitle.classList.add('mb-1');
//             htmlDivElementTitle.innerText='Litre unit of measurement';
//             htmlDivElementInput.classList.add('col-6');
//
//             htmlLitrInput.setAttribute('th:field', '${ingredient.litr}');
//             htmlLitrInput.setAttribute("type", 'number');
//             htmlLitrInput.setAttribute("step", '0.01');
//             htmlQuantityInput.setAttribute('type','hidden');
//             htmlQuantityInput.setAttribute('th:value','0');
//             htmlQuantityInput.setAttribute('th:attr','name="quantity"');
//             htmlWeightInput.setAttribute('type','hidden');
//             htmlWeightInput.setAttribute('th:value','0');
//             htmlWeightInput.setAttribute('th:attr','name="weight"');
//
//             htmlDivElementInput.appendChild(htmlLitrInput);
//             htmlDivElementInput.appendChild(htmlQuantityInput);
//             htmlDivElementInput.appendChild(htmlWeightInput);
//
//             htmlLabelElement.appendChild(htmlDivElementTitle);
//             htmlLabelElement.appendChild(htmlDivElementInput);
//
//             element.appendChild(htmlLabelElement);
//
//         } else if(htmlElement.value==='quantity'){
//             if(document.querySelector("#inputLabel")!=null){
//                 document.querySelector('#inputLabel').remove()
//             }
//             let htmlLabelElement = document.createElement('label');
//             let htmlDivElementTitle = document.createElement('div');
//             let htmlDivElementInput = document.createElement('div');
//             let htmlLitrInput = document.createElement('input');
//             let htmlWeightInput = document.createElement('input');
//             let htmlQuantityInput = document.createElement('input');
//             htmlLabelElement.setAttribute('id', 'inputLabel')
//             htmlDivElementTitle.classList.add('text-xs');
//             htmlDivElementTitle.classList.add('font-weight-bold');
//             htmlDivElementTitle.classList.add('text-uppercase');
//             htmlDivElementTitle.classList.add('mb-1');
//             htmlDivElementTitle.innerText='Psc unit of measurement';
//             htmlDivElementInput.classList.add('col-6');
//
//             htmlQuantityInput.setAttribute('th:field', '${ingredient.quantity}');
//             htmlQuantityInput.setAttribute("type", 'number');
//             htmlQuantityInput.setAttribute("step", '0.01');
//             htmlLitrInput.setAttribute('type','hidden');
//             htmlLitrInput.setAttribute('th:value','0');
//             htmlLitrInput.setAttribute('th:attr','name="litr"');
//             htmlWeightInput.setAttribute('type','hidden');
//             htmlWeightInput.setAttribute('th:value','0');
//             htmlWeightInput.setAttribute('th:attr','name="weight"');
//
//             htmlDivElementInput.appendChild(htmlLitrInput);
//             htmlDivElementInput.appendChild(htmlQuantityInput);
//             htmlDivElementInput.appendChild(htmlWeightInput);
//
//             htmlLabelElement.appendChild(htmlDivElementTitle);
//             htmlLabelElement.appendChild(htmlDivElementInput);
//
//             element.appendChild(htmlLabelElement);
//         } else if(htmlElement.value==='wieght'){
//             if(document.querySelector("#inputLabel")!=null){
//                 document.querySelector('#inputLabel').remove()
//             }
//             let htmlLabelElement = document.createElement('label');
//             let htmlDivElementTitle = document.createElement('div');
//             let htmlDivElementInput = document.createElement('div');
//             let htmlLitrInput = document.createElement('input');
//             let htmlWeightInput = document.createElement('input');
//             let htmlQuantityInput = document.createElement('input');
//             htmlLabelElement.setAttribute('id', 'inputLabel')
//             htmlDivElementTitle.classList.add('text-xs');
//             htmlDivElementTitle.classList.add('font-weight-bold');
//             htmlDivElementTitle.classList.add('text-uppercase');
//             htmlDivElementTitle.classList.add('mb-1');
//             htmlDivElementTitle.innerText='KG unit of measurement';
//             htmlDivElementInput.classList.add('col-6');
//
//             htmlWeightInput.setAttribute('th:field', '${ingredient.weight}');
//             htmlWeightInput.setAttribute("type", 'number');
//             htmlWeightInput.setAttribute("step", '0.01');
//             htmlQuantityInput.setAttribute('type','hidden');
//             htmlQuantityInput.setAttribute('th:value','0');
//             htmlQuantityInput.setAttribute('th:attr','name="quantity"');
//             htmlLitrInput.setAttribute('type','hidden');
//             htmlLitrInput.setAttribute('th:value','0');
//             htmlLitrInput.setAttribute('th:attr','name="litr"');
//
//             htmlDivElementInput.appendChild(htmlLitrInput);
//             htmlDivElementInput.appendChild(htmlQuantityInput);
//             htmlDivElementInput.appendChild(htmlWeightInput);
//
//             htmlLabelElement.appendChild(htmlDivElementTitle);
//             htmlLabelElement.appendChild(htmlDivElementInput);
//
//             element.appendChild(htmlLabelElement);
//         } else{
//             if(document.querySelector("#inputLabel")!=null){
//                 document.querySelector('#inputLabel').remove()
//             }
//         }
//     })
// });


