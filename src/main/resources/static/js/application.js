function addEventListeners(){
    document.getElementById("submitApplication").addEventListener('click',submit);

}
function validateFormData(data) {
    var keys = Object.keys(data);
    for(const key of keys){
            console.log(key);
            if(!data[key]){
                alert('please give value for field '+key);
                return -1;
            }
            if(key === 'personalId'){
                if(data[key].length!==11 ){
                    alert("personal id must have length of 11");
                    return -1;
                }
            }
    }
    return 0;
}

function  submit() {
    var form = document.getElementById("loanApplicationForm");
    var formData = new FormData(form);
    console.log(formData);
    var formDataJson = Object.fromEntries(formData);
    console.log(formDataJson);
    // var status = validateFormData(formDataJson);
    // console.log(status);
    // if(status !==0) return;
    $.ajax({
        url: '/loanApplication',
        data: JSON.stringify(formDataJson),
        contentType: "application/json; charset=utf-8",
        type:'POST',
        success: function(response){
            console.log(response);
            if(response.statusCode !==0){
                alert(response.description);
            }else{
                alert('application was registered successfully');
                form.reset();

            }
        }
    });
}

function drawUsersTable(users) {
    var table = "<table class='table table-bordered'>" +
        "<thead>" +
        "<tr>" +
        "<th scope='col'>#</th>"+
        "<th scope='col'>id</th>"+
        "<th scope='col'>UserName</th>"+
        "<th scope='col'>Make operator?</th>"+
        "</tr>" +
        "</thead>";
    users.forEach(function (item, index) {
        console.log(item);
        table+="<tr>";
        table+="<td>"+(index+1)+"</td>";
        table+="<td>"+item['id']+"</td>";
        table+="<td>"+item['userName']+"</td>";
        if(!item['operator']){
            table+="<td>"+`<button onClick='makeOperator(this.value)' class='btn btn-primary' value='${item['id']}'>Make Operator</button>`+"</td>";

        }

        table+="</tr>";
    });
    table+="</tbody>";
    table+="</table>";
    $("#dynamicTable").append(table);
}
function  makeOperator(userId) {
    console.log(userId);
    $.ajax({
        url: '/user/'+userId+"/status",
        type:'PUT',
        success: function(response){
           if(response.statusCode !==0){
               alert(response.description);
           }
        }
    });
}

function displayUsers(forOperator){
    console.log("sdsd");
    $.ajax({
        url: '/user',
        type:'GET',
        success: function(users){
            console.log(users);
            drawUsersTable(users);
        }
    });
}

function drawLoansTable(data,forOperator){
    var table = "<table class='table table-bordered'>" +
        "<thead>" +
        "<tr>" +
        "<th scope='col'>#</th>"+
        "<th scope='col'>id</th>"+
        "<th scope='col'>First Name</th>"+
        "<th scope='col'>Last Name</th>"+
        "<th scope='col'>personal ID</th>"+
        "<th scope='col'>salary</th>"+
        "<th scope='col'>monthly liability</th>"+
        "<th scope='col'>requested amount</th>"+
        "<th scope='col'>requested term type</th>"+
        "<th scope='col'>requested term</th>"+
        "<th scope='col'>status</th>"+
        "</tr>" +
        "</thead>";
    data.forEach(function (item, index) {
        console.log(item);
        table+="<tr>";
        table+="<td>"+(index+1)+"</td>";
        table+="<td>"+item['id']+"</td>";
        table+="<td>"+item['firstName']+"</td>";
        table+="<td>"+item['lastName']+"</td>";
        table+="<td>"+item['personalId']+"</td>";
        table+="<td>"+item['salary']+"</td>";
        table+="<td>"+item['monthlyLiability']+"</td>";
        table+="<td>"+item['requestedAmount']+"</td>";
        table+="<td>"+item['requestedTermType']+"</td>";
        table+="<td>"+item['requestedTerm']+"</td>";
        table+="<td>"+item['status']+"</td>";
        table+="</tr>";
    });
    table+="</tbody>";
    table+="</table>";
    $("#dynamicTable").append(table);

}
function displayApplications(forOperator){
    console.log("sdsd");
    $.ajax({
        url: '/loanApplication',
        type:'GET',
        success: function(response){
            console.log(response);
            drawLoansTable(response,forOperator);
        }
    });
}


