document.addEventListener("DOMContentLoaded", function () {


    let elementById = document.getElementById('beginDate');

    let elementById1 = document.getElementById('endDate');

    let elementById2 = document.getElementById('customBtn');

    let elementsArray = document.querySelectorAll('input[type="date"].myClassLp');

    elementsArray.forEach(function (elem) {
        elem.addEventListener("input", function () {

            elementById2.addEventListener('click', function () {

                document.getElementById('lostProductsChart').remove();
                let htmlCanvasElement = document.createElement('canvas');
                htmlCanvasElement.setAttribute('id', 'lostProductsChart');
                let elementById3 = document.getElementById('lostCanvasRow');
                elementById3.append(htmlCanvasElement);

                new Promise((resolve, reject) => {
                    fetch('/lostProducts/lostMoney/' + elementById.value + '/' + elementById1.value).then(response => {
                        return response.json();
                    }).then(data => {
                        // console.log("Result form response: ", data)

                        var reduced = data.reduce(function (allDates, date) {
                            if (allDates.some(function (e) {
                                return e.date === date.createdOn;
                            })) {
                                allDates.filter(function (e) {
                                    return e.date === date.createdOn
                                })[0].sum += +date.value
                            } else {
                                allDates.push({
                                    date: date.createdOn,
                                    sum: +date.value
                                })
                            }
                            return allDates
                        }, []);


                        var labels = reduced.map(function (e) {
                            return e.date;
                        });
                        var datas = reduced.map(function (e) {
                            return e.sum;
                        });


                        Chart.defaults.global.defaultFontFamily = 'Lato';
                        Chart.defaults.global.defaultFontSize = 18;
                        Chart.defaults.global.defaultFontColor = '#777';
                        let lostProductsChart = document.getElementById('lostProductsChart').getContext('2d');


                        let lineChart = new Chart(lostProductsChart, {
                            type: 'line',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                            data: {
                                labels: labels,
                                datasets: [{
                                    label: 'Lost products',
                                    data: datas,
                                    backgroundColor: 'red',

                                }]
                            },
                            options: {
                                // responsive: true,
                                // legend: {
                                //     position: 'bottom',
                                //     display: true,
                                //
                                // },
                                // "hover": {
                                //     "animationDuration": 0
                                // }, "animation": {
                                //     "duration": 1,
                                //     "onComplete": function () {
                                //         var chartInstance = this.chart,
                                //             ctx = chartInstance.ctx;
                                //
                                //         ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
                                //         ctx.textAlign = 'center';
                                //         ctx.textBaseline = 'bottom';
                                //
                                //         this.data.datasets.forEach(function (dataset, i) {
                                //             var meta = chartInstance.controller.getDatasetMeta(i);
                                //             meta.data.forEach(function (bar, index) {
                                //                 var data = dataset.data[index];
                                //                 ctx.fillText(data, bar._model.x, bar._model.y - 5);
                                //             });
                                //         });
                                //     }
                                // },
                            }
                        });

                    })
                }).catch(error => {
                    console.log("Error during fetch: ", index);
                })
            })
        });
    });


})