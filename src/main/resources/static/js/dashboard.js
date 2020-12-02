document.addEventListener("DOMContentLoaded", function () {

    //month counter
    let up = document.getElementById('upButton');
    let down = document.getElementById('downButton');
    let yearMonth = document.getElementById('secondStartDate');
    let buttonsMonthList = document.querySelectorAll('.myButton');

    buttonsMonthList.forEach(function (buton){
        buton.addEventListener('click', function (event){
            if(event.target===up){
                let monthVal = yearMonth.value;
                // console.log(monthVal)
                let split = monthVal.split('-');
                // console.log(split[1]);
                let splitElement = split[1];
                if(splitElement==='12'){
                    let year=++split[0];
                    let month=11-split[1];
                    let result='';
                    yearMonth.value = result.concat(year, month);
                }else {
                    let month = ++split[1];
                    let result = '';
                    yearMonth.value = result.concat(split[0], '-', month)
                }
            }
             if(event.target===down){
                let monthVal = yearMonth.value;
                // console.log(monthVal)
                let split = monthVal.split('-');
                // console.log(split[1]);
                let splitElement = split[1];
                if(splitElement==='1'){
                    let year=--split[0];
                    let month='12';
                    let result='';
                    yearMonth.value = result.concat(year,'-', month);
                }else {
                    let month = --split[1];
                    let result = '';
                    yearMonth.value = result.concat(split[0], '-', month)
                }
            }
        })
    })
    //end of month counter

    // third row chart
    let secondChartStart = document.getElementById('secondStartDate');
    let secondCanvasRow = document.getElementById('secondCanvasRow');
    let secondChart = document.getElementById('mostPopularProducts');
    let popProductSubmit = document.getElementById('popProductSubmit');

    let elementsArrayTwo = document.querySelectorAll('input[type="date"].mySecondClass');



            popProductSubmit.addEventListener("click",function (){
               document.getElementById('mostPopularProducts').remove();
                let htmlCanvasElement = document.createElement('canvas');
                htmlCanvasElement.setAttribute('id', 'mostPopularProducts');
                secondCanvasRow.append(htmlCanvasElement);

                let yM = yearMonth.value.split('-');

                new Promise((resolve, reject) => {
                    fetch('/dashboard/mostPopularProducts/'+yM[0]+'/'+yM[1])
                        .then(response=>{
                            return response.json();
                        }).then(test =>{
                        var reduced = test.reduce(function (allDates, date) {
                            if (allDates.some(function (e) {
                                return e.date === date.soldProductName;
                            })) {
                                allDates.filter(function (e) {
                                    return e.date === date.soldProductName
                                })[0].sum += +date.soldProductQuantity
                            } else {
                                allDates.push({
                                    date: date.soldProductName,
                                    sum: +date.soldProductQuantity
                                })
                            }
                            return allDates
                        }, []);
                        console.log(reduced);
                        var labels = reduced.map(function (e) {
                            return e.date;
                        });
                        console.log(labels);
                        var data = reduced.map(function (e) {
                            return e.sum;
                        });
                        console.log(data);
                    //    Last Chart with common products
                        Chart.defaults.global.defaultFontFamily = 'Lato';
                        Chart.defaults.global.defaultFontSize = 18;
                        Chart.defaults.global.defaultFontColor = '#777';
                        let commonProductChart = document.getElementById('mostPopularProducts').getContext('2d');


                        let productChart = new Chart(commonProductChart, {
                            type: 'bar',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                            data: {
                                labels: labels,
                                datasets: [{
                                    label: 'SOLD PRODUCT PER MONTH',
                                    data: data,
                                    backgroundColor: "green"
                                }]
                            },
                            options: {
                            }
                        });


                    })
                }).catch(error=>{
                    console.log("Error during fetch: ", index);
                })
            })

    //end of third row chart


    //Second row chart
    let soldSelect = document.getElementById('soldPeriod');

    soldSelect.addEventListener('change', function () {

        if (soldSelect.value === "cm") {

            document.getElementById("usersSoldHistoryLm").style.display = 'none';
            document.getElementById("usersSoldHistory").style.display = 'block';
            document.getElementById('beginDate').style.display = 'none';
            document.getElementById('endDate').style.display = 'none';
            document.getElementById('customBtn').style.display = 'none';
            document.getElementById('usersSoldHistoryCm').style.display = 'none'

            new Promise((resolve, reject) => {
                fetch('/dashboard/soldCurrentMonth').then(response => {
                    return response.json();
                }).then(datas=> {
                    // console.log("Result form response: ", data)

                    var reduced = datas.reduce(function (allDates, date) {
                        if (allDates.some(function (e) {
                            return e.date === date.date
                        })) {
                            allDates.filter(function (e) {
                                return e.date === date.date
                            })[0].sum += +date.sum
                        } else {
                            allDates.push({
                                date: date.date,
                                sum: +date.sum
                            })
                        }
                        return allDates
                    }, []);

                    // console.log(reduced)


                    var labels = reduced.map(function (e) {
                        return e.date;
                    });
                    var data = reduced.map(function (e) {
                        return e.sum;
                    });
                    Chart.defaults.global.defaultFontFamily = 'Lato';
                    Chart.defaults.global.defaultFontSize = 18;
                    Chart.defaults.global.defaultFontColor = '#777';
                    let userSoldHistoryChart = document.getElementById('usersSoldHistory').getContext('2d');


                    let barChart = new Chart(userSoldHistoryChart, {
                        type: 'bar',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Orders Value per Day',
                                data: data,
                                backgroundColor: 'dark'
                            }]
                        },
                        options: {}
                    });

                })
            }).catch(error => {
                console.log("Error during fetch: ", index);
            })
        }

        if (soldSelect.value === "pm") {

            document.getElementById("usersSoldHistoryLm").style.display = 'block';
            document.getElementById("usersSoldHistory").style.display = 'none';
            document.getElementById('beginDate').style.display = 'none';
            document.getElementById('endDate').style.display = 'none';
            document.getElementById('customBtn').style.display = 'none';
            document.getElementById('usersSoldHistoryCm').style.display = 'none'

            new Promise((resolve, reject) => {
                fetch('/dashboard/soldPreviousMonth').then(response => {
                    return response.json();
                }).then(resp => {
                    // console.log("Result form response: ", resp)
                    var reduced = resp.reduce(function (allDates, date) {
                        if (allDates.some(function (e) {
                            return e.date === date.date
                        })) {
                            allDates.filter(function (e) {
                                return e.date === date.date
                            })[0].sum += +date.sum
                        } else {
                            allDates.push({
                                date: date.date,
                                sum: +date.sum
                            })
                        }
                        return allDates
                    }, []);

                    // console.log(reduced)


                    var labels = reduced.map(function (e) {
                        return e.date;
                    });
                    var data = reduced.map(function (e) {
                        return e.sum;
                    });
                    Chart.defaults.global.defaultFontFamily = 'Lato';
                    Chart.defaults.global.defaultFontSize = 18;
                    Chart.defaults.global.defaultFontColor = '#777';
                    let userSoldHistoryChartLm = document.getElementById('usersSoldHistoryLm').getContext('2d');


                    let barChart = new Chart(userSoldHistoryChartLm, {
                        type: 'bar',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Orders Value per Day last month',
                                data: data,
                                backgroundColor: 'blue'
                            }]
                        },
                        options: {}
                    });

                })
            }).catch(error => {
                console.log("Error during fetch: ", index);
            })
        }
        if (soldSelect.value === "cd") {
            document.getElementById("usersSoldHistoryLm").style.display = 'none';
            document.getElementById("usersSoldHistory").style.display = 'none';
            document.getElementById('beginDate').style.display = 'block';
            document.getElementById('endDate').style.display = 'block';
            document.getElementById('customBtn').style.display = 'block';

            let elementById = document.getElementById('beginDate');

            let elementById1 = document.getElementById('endDate');

            let elementById2 = document.getElementById('customBtn');

            let elementsArray = document.querySelectorAll('input[type="date"].myClass');

            elementsArray.forEach(function (elem) {
                elem.addEventListener("input", function () {

                    elementById2.addEventListener('click', function () {

                        document.getElementById('usersSoldHistoryCm').remove();
                        let htmlCanvasElement = document.createElement('canvas');
                        htmlCanvasElement.setAttribute('id', 'usersSoldHistoryCm');
                        let elementById3 = document.getElementById('canvasRow');
                        elementById3.append(htmlCanvasElement);

                        new Promise((resolve, reject) => {
                            fetch('/dashboard/customSoldDate/' + elementById.value + '/' + elementById1.value).then(response => {
                                return response.json();
                            }).then(answ => {
                                // console.log("Result form response: ", data)

                                var reduced = answ.reduce(function (allDates, date) {
                                    if (allDates.some(function (e) {
                                        return e.date === date.date
                                    })) {
                                        allDates.filter(function (e) {
                                            return e.date === date.date
                                        })[0].sum += +date.sum
                                    } else {
                                        allDates.push({
                                            date: date.date,
                                            sum: +date.sum
                                        })
                                    }
                                    return allDates
                                }, []);


                                var labels = reduced.map(function (e) {
                                    return e.date;
                                });
                                var data = reduced.map(function (e) {
                                    return e.sum;
                                });


                                Chart.defaults.global.defaultFontFamily = 'Lato';
                                Chart.defaults.global.defaultFontSize = 18;
                                Chart.defaults.global.defaultFontColor = '#777';
                                let userSoldHistoryChartCm = document.getElementById('usersSoldHistoryCm').getContext('2d');


                                let barChart = new Chart(userSoldHistoryChartCm, {
                                    type: 'bar',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                                    data: {
                                        labels: labels,
                                        datasets: [{
                                            label: 'Orders Value per Day',
                                            data: data,
                                            backgroundColor: 'dark',

                                        }]
                                    },
                                    options: {
                                        responsive: true,
                                        legend: {
                                            position: 'bottom',
                                            display: true,

                                        },
                                        "hover": {
                                            "animationDuration": 0
                                        }, "animation": {
                                            "duration": 1,
                                            "onComplete": function () {
                                                var chartInstance = this.chart,
                                                    ctx = chartInstance.ctx;

                                                ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
                                                ctx.textAlign = 'center';
                                                ctx.textBaseline = 'bottom';

                                                this.data.datasets.forEach(function (dataset, i) {
                                                    var meta = chartInstance.controller.getDatasetMeta(i);
                                                    meta.data.forEach(function (bar, index) {
                                                        var data = dataset.data[index];
                                                        ctx.fillText(data, bar._model.x, bar._model.y - 5);
                                                    });
                                                });
                                            }
                                        },
                                    }
                                });

                            })
                        }).catch(error => {
                            console.log("Error during fetch: ", index);
                        })
                    })
                });
            });

        }
    })
//    end of Second row chart
})