let workItem = document.querySelector('#workOrderItem');
let endOrderButton = document.querySelector('#endOrder');

checkIfItemExists();

function checkIfItemExists(){
    if (workItem=== null){
        endOrderButton.style.display='none';
    }
}
