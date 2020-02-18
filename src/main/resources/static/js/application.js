function addEventListeners(){
    document.getElementById("submitApplication").addEventListener('click',submit);

}
function validateFormData(data) {
    var keys = Object.keys(data);
    for(const key of keys){
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
    var formDataJson = Object.fromEntries(formData);
    var status = validateFormData(formDataJson);
    if(status !==0) return;
    $.ajax({
        url: '/loanApplication',
        data: JSON.stringify(formDataJson),
        contentType: "application/json; charset=utf-8",
        type:'POST',
        success: function(response){
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
    $.ajax({
        url: '/user/'+userId+"/status",
        type:'PUT',
        success: function(response){
           if(response.statusCode !==0){
               alert(response.description);
           }else{
               location.reload();
           }
        }
    });
}

function displayUsers(forOperator){
    $.ajax({
        url: '/user',
        type:'GET',
        success: function(users){
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
        if(forOperator && item['status'] === 'MANUAL'){
            table+="<td>"
                +`<div id='${item.id}'>` +
                        "<button  class='approveBtn' value='APPROVED' onClick='changeApplicationStatus(this)'>APPROVE</button>" +
                        "<button  class='rejectBtn' value='REJECTED' onClick='changeApplicationStatus(this)'>REJECT</button>" +
                "</div>"
                +"</td>";
        }
        table+="</tr>";
    });
    table+="</tbody>";
    table+="</table>";
    $("#dynamicTable").append(table);
}

function changeApplicationStatus(btn){
    var btnParents = $(btn).parents();
    var loanAppId = btnParents.eq(0).attr('id');
    $.ajax({
        url:`/loanApplication/${loanAppId}/status`,
        type:'PUT',
        data:{
          newStatus:btn.value
        },
        success:function (response) {
            if(response.statusCode !==0){
                alert(response.description);
            }else{
                btnParents.eq(2).remove();
            }
        }
    });
}

function displayApplications(forOperator){
    var data;
    if(forOperator){
        data = {"status":"MANUAL"};
    }
    $.ajax({
        url: '/loanApplication',
        type:'GET',
        data:data,
        success: function(response){
            drawLoansTable(response,forOperator);
            getSortingFields(forOperator);
        }
    });
}

function getSortingFields(forOperator) {
    $.ajax({
        url: '/loanApplication/sortingFields',
        type:'GET',
        success: function(sortingFields){
            createSortingForm(sortingFields,forOperator);
        }
    });
}

function createSortingForm(fields,forOperator) {
    var div="<div>Direction:";
     div+=" <select class='browser-default custom-select' name='direction'>"+
                        "<option value='ASC'>asc</option>"+
                        "<option value='DESC'>desc</option>"+
                    "</select>";


    div+="</div>";
    div+="<div>Field:";
    div+="<select class='browser-default custom-select'  name='sortingDisplayName'>";
    fields.forEach(function (field) {
        div+=`<option value=${field}>${field}</option>`;

    });
    div+="</select>";
    div+="</div>";
    // div+="<br/>";
    div+="<div>";
    div+="<div>&nbsp;</div>";
    div+=`<button class='btn btn-primary' onClick='searchLoanApps(${forOperator})'>Search</button>`;
    div+="</div>";
    $("#sortingFields").append(div);
}


function searchLoanApps(forOperator) {
    var direction = document.getElementsByName('direction')[0].value;
    var sortingDisplayName = document.getElementsByName('sortingDisplayName')[0].value;
    var data = {"direction":direction,"sortingDisplayName":sortingDisplayName};
    if(forOperator){
        data['status']='MANUAL';
    }
    $.ajax({
        url: '/loanApplication',
        type:'GET',
        data:data,
        success: function(loanApps){
            $("#dynamicTable").empty();
            drawLoansTable(loanApps,forOperator);
        }
    });

}
