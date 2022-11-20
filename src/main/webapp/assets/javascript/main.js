var colorArray=[
    "#63b598", "#ce7d78", "#ea9e70", "#a48a9e", "#c6e1e8", "#648177" ,"#0d5ac1" ,
    "#f205e6" ,"#1c0365" ,"#14a9ad" ,"#4ca2f9" ,"#a4e43f" ,"#d298e2" ,"#6119d0",
    "#d2737d" ,"#c0a43c" ,"#f2510e" ,"#651be6" ,"#79806e" ,"#61da5e" ,"#cd2f00" ,
    "#9348af" ,"#01ac53" ,"#c5a4fb" ,"#996635","#b11573" ,"#4bb473" ,"#75d89e" ,
    "#2f3f94" ,"#2f7b99" ,"#da967d" ,"#34891f" ,"#b0d87b" ,"#ca4751" ,"#7e50a8" ,
    "#c4d647" ,"#e0eeb8" ,"#11dec1" ,"#289812" ,"#566ca0" ,"#ffdbe1" ,"#2f1179" ,
    "#935b6d" ,"#916988" ,"#513d98" ,"#aead3a", "#9e6d71", "#4b5bdc", "#0cd36d",
    "#250662", "#cb5bea", "#228916", "#ac3e1b", "#df514a", "#539397", "#880977",
    "#f697c1", "#ba96ce", "#679c9d", "#c6c42c", "#5d2c52", "#48b41b", "#e1cf3b",
    "#5be4f0", "#57c4d8", "#a4d17a",  "#be608b", "#96b00c", "#088baf",
    "#f158bf", "#e145ba", "#ee91e3", "#05d371", "#5426e0", "#4834d0", "#802234",
    "#6749e8", "#0971f0", "#8fb413", "#b2b4f0", "#c3c89d", "#c9a941", "#41d158",
    "#fb21a3", "#51aed9", "#5bb32d",  "#21538e", "#89d534", "#d36647",
    "#7fb411", "#0023b8", "#3b8c2a", "#986b53", "#f50422", "#983f7a", "#ea24a3",
    "#79352c", "#521250", "#c79ed2", "#d6dd92", "#e33e52", "#b2be57", "#fa06ec",
    "#1bb699", "#6b2e5f", "#64820f",  "#21538e", "#89d534", "#d36647",
    "#7fb411", "#0023b8", "#3b8c2a", "#986b53", "#f50422", "#983f7a", "#ea24a3",
    "#79352c", "#521250", "#c79ed2", "#d6dd92", "#e33e52", "#b2be57", "#fa06ec",
    "#1bb699", "#6b2e5f", "#64820f",  "#9cb64a", "#996c48", "#9ab9b7",
    "#06e052", "#e3a481", "#0eb621", "#fc458e", "#b2db15", "#aa226d", "#792ed8",
    "#73872a", "#520d3a", "#cefcb8", "#a5b3d9", "#7d1d85", "#c4fd57", "#f1ae16",
    "#8fe22a", "#ef6e3c", "#243eeb",  "#dd93fd", "#3f8473", "#e7dbce",
    "#421f79", "#7a3d93", "#877f9c", "#93f2d7", "#9b5c2a", "#15b9ee", "#0f5997",
    "#409188", "#911e20", "#1350ce", "#10e5b1", "#fff4d7", "#cb2582", "#ce00be",
    "#32d5d6",  "#608572", "#c79bc2", "#00f87c", "#77772a", "#6995ba",
    "#fc6b57", "#f07815", "#8fd883", "#060e27", "#96e591", "#21d52e", "#d00043",
    "#b47162", "#1ec227", "#4f0f6f", "#1d1d58", "#947002", "#bde052", "#e08c56",
    "#28fcfd",  "#36486a", "#d02e29", "#1ae6db", "#3e464c", "#a84a8f",
    "#911e7e", "#3f16d9", "#0f525f", "#ac7c0a", "#b4c086", "#c9d730", "#30cc49",
    "#3d6751", "#fb4c03", "#640fc1", "#62c03e", "#d3493a", "#88aa0b", "#406df9",
    "#615af0",  "#2a3434", "#4a543f", "#79bca0", "#a8b8d4", "#00efd4",
    "#7ad236", "#7260d8", "#1deaa7", "#06f43a", "#823c59", "#e3d94c", "#dc1c06",
    "#f53b2a", "#b46238", "#2dfff6", "#a82b89", "#1a8011", "#436a9f", "#1a806a",
    "#4cf09d", "#c188a2", "#67eb4b", "#b308d3", "#fc7e41", "#af3101",
    "#71b1f4", "#a2f8a5", "#e23dd0", "#d3486d", "#00f7f9", "#474893", "#3cec35",
    "#1c65cb", "#5d1d0c", "#2d7d2a", "#ff3420", "#5cdd87", "#a259a4", "#e4ac44",
    "#1bede6", "#8798a4", "#d7790f", "#b2c24f", "#de73c2", "#d70a9c",
    "#88e9b8", "#c2b0e2", "#86e98f", "#ae90e2", "#1a806b", "#436a9e", "#0ec0ff",
    "#f812b3", "#b17fc9", "#8d6c2f", "#d3277a", "#2ca1ae", "#9685eb", "#8a96c6",
    "#dba2e6", "#76fc1b", "#608fa4", "#20f6ba", "#07d7f6", "#dce77a", "#77ecca"]
async function getPopulationMap(side) {
var fromDate = document.getElementById('from'+side).value;
var toDate = document.getElementById('to'+side).value;
var state = document.getElementById("stateus"+side).value;

    let response = await fetch("http://localhost:8080/humanmobility_war/api/mobility/viewByDefault?" + new URLSearchParams({
        state: state,
        fromDate: fromDate,
        toDate: toDate,
}), {
        // Adding method type
        method: "GET",
        headers: {
            "Content-type": "application/json"

        }
    });
        // Converting to JSON
        response = await response.json();
        return response;
        // Displaying results to console

}

async function setDatasetForTimeSeries(side) {
    let populationMap = new Map();

    await getPopulationMap(side).then(populations => {

        for (let i = 0; i < populations.length; i++) {
            if (populationMap.has(populations[i]["locationName"]))
                populationMap.get(populations[i]["locationName"]).push(populations[i]);
            else populationMap.set(populations[i]["locationName"], [populations[i]]);
            //labels.push(populations[i]["date"]);
            //dataPoints.push(populations[i]["population"])
        }
    });


    let lineChartData = {};
    lineChartData.labels = [];
    lineChartData.datasets = [];
    let counties = 0;
    let dataset;

    for (let [key, value] of populationMap) {
        lineChartData.datasets.push({});
        dataset = lineChartData.datasets[counties];
        //dataset.color = "red";
        dataset.label=key;
        //dataset.strokeColor = colorArray[counties];
        dataset.borderColor=colorArray[counties];
        dataset.data = [];
        y = [];
        l = [];
        for (let i = 0; i < value.length; i++) {
            y.push(value[i]["population"]);
            l.push(value[i]["date"]);
        }
        dataset.data = y;
        lineChartData.labels = l;
        counties++;
    }

    const config = {
        type: 'line',
        data: lineChartData,

    };
    return lineChartData;
}
function populateDropdown() {
        var elmts = ['California','Texas','Arizona','Florida','New York','Nevada','Illinois','Washington','Pennsylvania','Michigan','Ohio'];
        var values=['60','48','40','12','36','32','17','53','42','26','39'];
        var selectLeft = document.getElementById('stateusLeft');
    var selectRight = document.getElementById('stateusRight');
    console.log(selectLeft);
    console.log(selectRight);
        for (var i = 0; i < elmts.length; i++) {
            var optn = elmts[i];
            var elLeft = document.createElement("option");
            var elRight = document.createElement("option");

            elLeft.textContent = optn;
            elLeft.value = values[i];

            elRight.textContent = optn;
            elRight.value = values[i];

            selectLeft.appendChild(elLeft);
            selectRight.appendChild(elRight);
        }
        var elLeft=document.createElement("option");
    var elRight=document.createElement("option");

        elLeft.textContent="All";
        elLeft.value='All';
        elLeft.selected=true;
        selectLeft.appendChild(elLeft);

        elRight.textContent="All";
        elRight.value='All';
        elRight.selected=true;
        selectRight.appendChild(elRight);


    }

    async function showCharts(){
    await renderChart('Left');
    await renderChart('Right');
    }

async function renderChart(side) {
    if(side=='Right') {
        document.getElementById("populationTimeSeriesRight").innerHTML = "";
        document.getElementById("sb1Right").innerHTML = "";
        document.getElementById("sb2Right").innerHTML = "";
        document.getElementById("flowRight").innerHTML = "";
        document.getElementById("heatMapRight").innerHTML = "";
    }else{
        document.getElementById("populationTimeSeriesLeft").innerHTML = "";
        document.getElementById("sb1Left").innerHTML = "";
        document.getElementById("sb2Left").innerHTML = "";
        document.getElementById("flowLeft").innerHTML = "";
        document.getElementById("heatMapLeft").innerHTML = "";
    }
    var myNewChart = new Chart(document.getElementById("populationTimeSeries"+side).getContext('2d') , setDatasetForTimeSeries());
    var canvas = document.getElementById('populationTimeSeries'+side);

    var ctx = canvas.getContext('2d');
    let ds = await setDatasetForTimeSeries(side);
    var myChart = new Chart(ctx, {
            type: 'line',
            data: ds,
        options: {
                responsive:true,
            interaction: {
                mode: 'index',
                intersect: false,
            },
            stacked: false,
                plugins:{title: {
                    display: true,
                    text: 'Population of counties over time',
                    padding: {
                        top: 20,
                        bottom: 30
                    }
                }}
            ,scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Population'
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Date'
                    }
                }]
            }
        }
        }
    );
    var sunburstDest=document.getElementById('sb1'+side);
    var  sunburstOrigin = document.getElementById('sb2'+side);

    let hierariachalData=await getHierariachalDataset(side);
    hierariachalDataDestination=[hierariachalData[0]];
    hierariachalDataOrigin=[hierariachalData[1]];
    //console.log(hierariachalData);
    var sunburstChartDest= anychart.sunburst(hierariachalDataDestination,'as-tree');
    sunburstChartDest.title('Total Incoming FLows');
    sunburstChartDest.tooltip(true);
    sunburstChartDest.calculationMode('parent-independent');
    sunburstChartDest.sort('asc');
    sunburstChartDest.container(sunburstDest);
    sunburstChartDest.draw();

    var sunburstChartOrigin= anychart.sunburst(hierariachalDataOrigin,'as-tree');
    sunburstChartOrigin.tooltip(true);
    sunburstChartOrigin.title('Total Outgoing FLows');
    sunburstChartOrigin.calculationMode('parent-independent');
    sunburstChartOrigin.sort('asc');
    sunburstChartOrigin.container(sunburstOrigin);
    sunburstChartOrigin.draw();

    let flowData= await getFlowData(side);

    var map= anychart.connector();
    var dropdown =document.getElementById("stateus"+side);

    var state= dropdown.options[dropdown.selectedIndex].text;

    if(state=='All') {
        map.geoData('anychart.maps.united_states_of_america');
    }else{
        if(state=='New York')
            stateMap='anychart.maps.new_york';
        else stateMap='anychart.maps.'+state.toLowerCase();

        map.geoData(stateMap);
    }
    map
        .unboundRegions()
        .enabled(true)
        .fill('#E1E1E1')
        .stroke('#D2D2D2');
    //map.title('Human Mobility Flow during COVID-19');
    map.overlapMode("allow-overlap");
    map
        .legend()
        .position('bottom');

    var dataSet = anychart.data.set(flowData).mapAs();

    createSeries(
        'Less than 10,000',map,
        dataSet.filter('total', filterFunction(0, 10000)),
        '#9ce0ef'
    );
    createSeries(
        '10,000 to 1,00,000',map,
        dataSet.filter('total', filterFunction(10000, 100000)),
        '#047885'
    );
    createSeries(
        '1,00,000 to 3,00,000',map,
        dataSet.filter('total', filterFunction(100000, 300000)),
        '#1b47d7'
    );
    createSeries(
        '3,00,000 to 5,00,000',map,
        dataSet.filter('total', filterFunction(300000, 500000)),
        '#e59790'
    );
    createSeries(
        'More than 5,00,000',map,
        dataSet.filter('total', filterFunction(500000, 1000000)),
        '#ec2626'
    );

     var flowMap=document.getElementById('flow'+side);
    var zoomController = anychart.ui.zoom();
    zoomController.target(map);
    zoomController.render();

    map.container(flowMap);
    map.draw();

    //matrix chart
    var matrix=document.getElementById('heatMap'+side);
    var matrixData=await getMatrixData(side);
    matrixChart = anychart.heatMap(matrixData);
   // matrixChart.title("Human Mobility");

    var customColorScale = anychart.scales.ordinalColor();
    customColorScale.ranges([
       {less:1},
        { from: 1, to: 10000},
        { from: 10000, to: 100000 },
        { from: 100000, to: 300000 },
        { from: 300000, to: 500000 },
        { greater: 500000 }
    ]);
    customColorScale.colors(["#CF7A78", "#E69645", "#69A231", "#4D7623"]);
    customColorScale.ranges([
        {less:1, name: 'No Flow',color: '#877F9CFF'},
        { from: 1, to: 10000, name: 'Less than 10,000', color: '#9ce0ef' },
        { from: 10000, to: 100000, name: '10,000 to 1,00,000', color: '#047885' },
        { from: 100000, to: 300000, name: '1,00,000 to 3,00,000', color: '#1b47d7' },
        { from: 300000, to: 500000, name: '3,00,000 to 5,00,000', color: '#e59790' },
        { greater: 500000, name: 'More than 5,00,000', color: '#ec2626' }
    ]);
    matrixChart.colorScale(customColorScale);
    var tooltip = matrixChart.tooltip();
    tooltip.fontWeight(600);
    tooltip.positionMode("point");
    tooltip.format(function (){
        return 'From: '+this.y +'\nTo: '+this.x+'\nFlow: '+this.heat;
    })
    matrixChart.xAxis().labels().rotation(-45);
    matrixChart.yAxis().labels().rotation(-45);
    matrixChart.xScroller().enabled(true);
    matrixChart.xZoom().setToPointsCount(6);
    matrixChart.yScroller().enabled(true);
    matrixChart.yZoom().setToPointsCount(6);
    matrixChart.legend(true);
    matrixChart.container(matrix);
    matrixChart.draw();
}

var createSeries = function (name,map,data,color) {

    // create and customize the connector series
    var connectorSeries = map
        .connector(data)
        .name(name).fill(color).stroke(
            {
                color: color,
                thickness: 1.5
            }
        );
    connectorSeries
        .hovered()
        .stroke('1.5 #212121')
        .fill('#212121');


    connectorSeries
        .markers()
        .position('100%')
        .fill(color)
        .stroke({
            color: color
        })
        .size(8);
    connectorSeries
        .hovered()
        .markers()
        .position('100%')
        .size(10)
        .fill('#212121')
        .stroke('2 #455a64');

    // set the labels for the source countries
    connectorSeries
        .labels()
        .enabled(true)
        .format(function () {
            return this.getData('from');
        });

    if (name === 'More than 5,00,000') {
        connectorSeries.startSize(2).endSize(1.75);
    } else if (name === '3,00,000 to 5,00,000') {
        connectorSeries.startSize(1.75).endSize(1.25);
    } else if (name === '1,00,000 to 3,00,000') {
        connectorSeries.startSize(1.25).endSize(0.75);
    } else if (name === '10,000 to 1,00,000') {
        connectorSeries.startSize(0.75).endSize(0.5);
    } else {
        connectorSeries.startSize(1).endSize(0);
    }

    connectorSeries
        .legendItem()
        .iconType('square')
        .iconFill(color)
        .iconStroke(false);

    map.legend()
        .enabled(true)
        .position('center')
        .padding([20, 0, 20, 0])
        .fontSize(10);

    map.legend()
        .title()
        .enabled(true)
        .fontSize(13)
        .padding([0, 0, 5, 0])
        .text('Population Flow');

    connectorSeries
        .tooltip()
        .useHtml(true)
        .format(function () {
            return (
                '<h4 style="font-size:14px; font-weight:400; margin: 0.25rem 0;">From:<b> ' + this.getData('from') + '</b></h4>' +
                '<h4 style="font-size:14px; font-weight:400; margin: 0.25rem 0;">Population Flow:<b>' + this.getData('total').toLocaleString() + '</b></h4>' +
                '<h4 style="font-size:14px; font-weight:400; margin: 0.25rem 0;">To:<b>' + this.getData('to').toLocaleString() + '</b></h4>'
            );
        });
};

function filterFunction(val1, val2) {
    if (val2) {
        return function (fieldVal) {
            return val1 <= fieldVal && fieldVal < val2;
        };
    }
    return function (fieldVal) {
        return val1 <= fieldVal;
    };
}

async function getHierariachalDataset(side){
    var fromDate,toDate,state;
    if(side=='Left') {
        fromDate = document.getElementById("fromLeft").value;
        toDate = document.getElementById("toLeft").value;
        state = document.getElementById("stateusLeft").value;
    }else{
        fromDate = document.getElementById("fromRight").value;
        toDate = document.getElementById("toRight").value;
        state = document.getElementById("stateusRight").value;
    }
    let response = await fetch("http://localhost:8080/humanmobility_war/api/mobility/viewHierariachalFlow?" + new URLSearchParams({
        state:state,
        fromDate: fromDate,
        toDate: toDate,
}), {
        method: "GET",
        headers: {
            "Content-type": "application/json"

        }
    });
    response = await response.json();
    return response;
}

async function getFlowData(side){
    var fromDate,toDate,state;
    if(side=='Left') {
        var fromDate = document.getElementById("fromLeft").value;
        var toDate = document.getElementById("toLeft").value;
        var state = document.getElementById("stateusLeft").value;
    }else{
        fromDate = document.getElementById("fromRight").value;
        toDate = document.getElementById("toRight").value;
        state = document.getElementById("stateusRight").value;
    }
    let response = await fetch("http://localhost:8080/humanmobility_war/api/mobility/viewFlowMap?" + new URLSearchParams({
        state:state,
        fromDate: fromDate,
        toDate: toDate,
}), {
        // Adding method type
        method: "GET",
        headers: {
            "Content-type": "application/json"

        }
    });
    // Converting to JSON
    response = await response.json();
    return response;
}

async function getMatrixData(side){
    var fromDate,toDate,state;
    if(side=='Left') {
        var fromDate = document.getElementById("fromLeft").value;
        var toDate = document.getElementById("toLeft").value;
        var state = document.getElementById("stateusLeft").value;
    }else{
        var fromDate = document.getElementById("fromRight").value;
        var toDate = document.getElementById("toRight").value;
        var state = document.getElementById("stateusRight").value;
    }
    let response = await fetch("http://localhost:8080/humanmobility_war/api/mobility/viewMatrixMap?" + new URLSearchParams({
        state:state,
        fromDate: fromDate,
        toDate: toDate,
    }), {
        method: "GET",
        headers: {
            "Content-type": "application/json"
        }
    });
    // Converting to JSON
    response = await response.json();
    return response;
}
//Change color and other visuals effects